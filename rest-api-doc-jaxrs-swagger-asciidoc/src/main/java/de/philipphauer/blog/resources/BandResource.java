package de.philipphauer.blog.resources;

import com.google.common.collect.ImmutableList;
import de.philipphauer.blog.resources.dto.BandCreationDTO;
import de.philipphauer.blog.resources.dto.BandRetrievalDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Api
public class BandResource {

    private static final BandRetrievalDTO A_BAND = new BandRetrievalDTO()
            .setId(UUID.randomUUID())
            .setName("Flogging Molly")
            .setFoundation(1997);

    @GET
    @Path("/bands")
    @ApiOperation(value = "Retrieve all Bands", notes = "See [usage](#_retrieve_all_bands).")
    public List<BandRetrievalDTO> getBands(@QueryParam("offset") @DefaultValue("0") int offset,
                                           @QueryParam("limit") @DefaultValue("100") int limit) {
        return ImmutableList.of(A_BAND);
    }

    @GET
    @Path("/bands/{bandId}")
    @ApiOperation(value = "Retrieve a single Band", notes = "Description")
    public BandRetrievalDTO getBand(@PathParam("bandId") String bandId) {
        return A_BAND;
    }

    @POST
    @Path("/bands/")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create a new Band", notes = "Description")
    @ApiResponses(value = @ApiResponse(code = 201, message = "Successfully created band",
            responseHeaders = @ResponseHeader(name = "Location", description = "URL of the created band. e.g. `/band/<uuid>`")
    ))
    public Response createBand(@ApiParam(required = true) BandCreationDTO newBand) {
        System.out.println(newBand);
        return null;
    }
}