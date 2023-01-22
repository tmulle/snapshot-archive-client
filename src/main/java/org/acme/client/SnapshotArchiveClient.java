package org.acme.client;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import org.acme.model.FileInfo;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Client to talk to the snapshot archive server
 *
 * @author tmulle
 */
@Path("/archive")
@RegisterRestClient(configKey = "snapshotClient")
public interface SnapshotArchiveClient {

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteFile(@PathParam("id") String id);

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadFile(@RestForm File file, @QueryParam("ticketNumber") String ticketNumber);

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCount();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<FileInfo> getAll(@QueryParam("ticketNumber") String ticketNumber,
                          @QueryParam("startDate") String startDate,
                          @QueryParam("endDate") String endDate,
                          @QueryParam("limit") String recordLimit,
                          @QueryParam("skip") String skipRecord,
                          @QueryParam("sortyBy") String sortFields,
                          @QueryParam("sortDir") String sortDir);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    FileInfo getFileInfo(@PathParam("id") String id);

    @GET
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Response downloadFile(@PathParam("id") String fileId);


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    FileInfo getInfo(@PathParam("id") String fileId);

    @ClientExceptionMapper(priority = 100)
    static RuntimeException toException(Response response) {
        if (response.getStatus() == 400) {
            System.out.println(response.readEntity(String.class));
        }
        return null;
    }
}