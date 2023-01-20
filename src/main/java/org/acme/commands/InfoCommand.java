package org.acme.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.client.SnapshotArchiveClient;
import org.acme.model.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.IOException;

@Command(description = "Fetch info for a file", name = "info")
public class InfoCommand extends ReusableOptions implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(InfoCommand.class);

    @Inject
    ObjectMapper mapper;

    @CommandLine.Option(names = { "-i", "--id" }, paramLabel = "ID", description = "File ID to get info", required = true)
    String id;

    @Override
    public void run() {
        SnapshotArchiveClient client = getClient(host, port);
        FileInfo info = client.getInfo(id);
        try {
            mapper.writeValue(System.out, info);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
