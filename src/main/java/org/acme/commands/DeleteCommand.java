package org.acme.commands;

import org.acme.client.SnapshotArchiveClient;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(description = "Delete a file", name = "delete")
public class DeleteCommand extends ReusableOptions implements Runnable {

    @CommandLine.Option(names = {"-i", "--id"}, paramLabel = "ID", description = "File ID to get info", required = true)
    String id;

    @Override
    public void run() {
        SnapshotArchiveClient client = getClient(host, port);
        client.deleteFile(id);
    }

}
