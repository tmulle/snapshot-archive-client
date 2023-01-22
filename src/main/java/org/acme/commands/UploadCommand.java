package org.acme.commands;

import org.acme.client.SnapshotArchiveClient;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.ws.rs.core.Response;
import java.io.File;

/**
 * Upload a file to the system
 *
 * @author tmulle
 */
@Command(description = "Upload a file", name = "upload")
public class UploadCommand extends ReusableOptions implements Runnable {

    @CommandLine.Option(names = { "-t", "--ticketNumber" }, paramLabel = "TICKETNUMBER", description = "Help Desk Ticket Number")
    String ticketNumber;

    @CommandLine.Option(names = { "-f", "--file" }, paramLabel = "FILE", description = "Path to the file to upload", required = true)
    File file;

    @Override
    public void run() {

        // Build the client
        SnapshotArchiveClient client = getClient(host, port);

        // Upload the file
        Response all = client.uploadFile(file, ticketNumber);

        // Read the return object ID of the new file
        System.out.println(all.readEntity(String.class));
    }

}
