package org.acme.commands;

import org.acme.client.SnapshotArchiveClient;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

import java.net.URI;

@CommandLine.Command(synopsisHeading      = "%nUsage:%n%n",
        descriptionHeading   = "%nDescription:%n%n",
        parameterListHeading = "%nParameters:%n%n",
        optionListHeading    = "%nOptions:%n%n",
        commandListHeading   = "%nCommands:%n%n")
public class ReusableOptions {

    @CommandLine.Option(names = {"-?", "-h", "--help"},
            usageHelp = true,
            description = "display this help message")
    boolean usageHelpRequested;

    @CommandLine.Option(defaultValue = "localhost", names = { "--host" }, paramLabel = "HOST", description = "Server host")
    String host;

    @CommandLine.Option(defaultValue = "8080", names = { "--port" }, paramLabel = "PORT", description = "Server port")
    int port;

    @CommandLine.Option(defaultValue = "false", names = { "--secure" }, description = "Turns on HTTPS")
    boolean isSsl;

    public SnapshotArchiveClient getClient(String host, int nport) {
        String url = String.format("%s://%s:%d", isSsl ? "https" : "http", host, nport);
        URI anyDynamicUrl = URI.create(url);
        SnapshotArchiveClient client = RestClientBuilder.newBuilder().baseUri(anyDynamicUrl)
                .build(SnapshotArchiveClient.class);

        return client;
    }
}