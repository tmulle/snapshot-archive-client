package org.acme.commands;

import org.acme.client.SnapshotArchiveClient;
import picocli.CommandLine.Command;

import javax.ws.rs.core.Response;

@Command(description = "Get the total record count", name = "count")
public class CountCommand extends ReusableOptions implements Runnable {

    @Override
    public void run() {
        SnapshotArchiveClient client = getClient(host, port);
        Response response = client.getCount();
        System.out.println(response.readEntity(String.class));
    }

}
