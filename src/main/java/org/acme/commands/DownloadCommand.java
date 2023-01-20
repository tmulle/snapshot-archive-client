package org.acme.commands;

import org.acme.client.SnapshotArchiveClient;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Command(description = "Download a file", name = "download")
public class DownloadCommand extends ReusableOptions implements Runnable {

    @CommandLine.Option(names = {"-i", "--id"}, paramLabel = "ID", description = "File Id to download", required = true)
    String id;

    @CommandLine.Option(names = {"-d", "--outdir"}, paramLabel = "DIR", description = "Absolute path to directory to store file")
    Path outdir;

    @CommandLine.Option(names = {"-f", "--filename"}, paramLabel = "DIR", description = "Name to store file. If not specified will use the original filename or the file Id if it can't read it")
    String fileName;

    @Override
    public void run() {
        System.out.println("Downloading data for file id: " + id);
        SnapshotArchiveClient client = getClient(host, port);
        Response response = client.downloadFile(id);

        // Get the inputstream to read the data
        InputStream in = response.readEntity(InputStream.class);

        // If not specified use current dir
        if (outdir == null) {
            outdir = Paths.get("");
        }

        // If no filename specified
        // then try to read the original
        // otherwise use the file ID
        if (fileName == null || fileName.isEmpty()) {
            String headerString = response.getHeaderString("Content-Disposition");
            if (headerString != null) {
                String origFilename = headerString.substring(headerString.indexOf("=") + 1);
                outdir = outdir.resolve(origFilename);
            } else {
                outdir = outdir.resolve(id);
            }
        } else {
            outdir = outdir.resolve(fileName);
        }

        System.out.println("Saving file to: " + outdir);

        try {
            Files.copy(in, outdir, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
