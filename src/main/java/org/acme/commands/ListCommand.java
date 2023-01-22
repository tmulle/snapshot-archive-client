package org.acme.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.client.SnapshotArchiveClient;
import org.acme.model.FileInfo;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.List;

/**
 * Lists records by optional filters
 *
 * @author tmulle
 */
@Command(description = "Return a list of records", name = "list")
public class ListCommand extends ReusableOptions implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(ListCommand.class);

    @Inject
    ObjectMapper mapper;

//    @RestClient
//    SnapshotArchiveClient client;

    @CommandLine.Option(names = { "-sd", "--startDate" }, paramLabel = "STARTDATE", description = "Sets the startDate filter (MM-DD-YYYY)")
    String startDate;

    @CommandLine.Option(names = { "-ed", "--endDate" }, paramLabel = "ENDDATE", description = "Sets the endDate filter (MM-DD-YYYY)")
    String endDate;

    @CommandLine.Option(names = { "-t", "--ticketNumber" }, paramLabel = "TICKETNUMBER", description = "Help Desk Ticket Number filter")
    String ticketNumber;

    @CommandLine.Option(names = { "-l", "--limit" }, paramLabel = "LIMIT", description = "How many records to return")
    String recordLimit;

    @CommandLine.Option(names = { "-s", "--skip" }, paramLabel = "SKIP", description = "How many records to skip")
    String skipRecord;

    @CommandLine.Option(names = { "-sf", "--sortFields" }, paramLabel = "SORTFIELDS", description = "Comma seperated list of fields to sort by")
    String sortFields;

    @CommandLine.Option(names = { "-sdr", "--sortDir" }, paramLabel = "SORTDIR", description = "ASC or DESC sorting")
    String sortDir;

    @CommandLine.Option(names = { "-fn", "--filename" }, paramLabel = "FILENAME", description = "Name of file to search for")
    String filename;


    @Override
    public void run() {
        SnapshotArchiveClient client = getClient(host, port);
        List<FileInfo> all = client.getAll(ticketNumber, startDate, endDate, recordLimit, skipRecord, sortFields, sortDir, filename);
        try {
            mapper.writeValue(System.out, all);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
