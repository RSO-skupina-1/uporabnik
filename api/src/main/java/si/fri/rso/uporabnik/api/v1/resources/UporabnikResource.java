package si.fri.rso.uporabnik.api.v1.resources;


import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.uporabnik.lib.Uporabnik;
import si.fri.rso.uporabnik.services.beans.UporabnikBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/uporabnik")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(name="katalogDestinacij", allowOrigin = "*", supportedMethods = "GET, POST, HEAD, PUT, DELETE, OPTIONS")
public class UporabnikResource {

    private Logger log = Logger.getLogger(UporabnikResource.class.getName());

    @Inject
    private UporabnikBean uporabnikBean;


    @Context
    protected UriInfo uriInfo;

    @Counted(name = "get_all_uporabnik_count")
    @Operation(description = "Get all users.", summary = "Returns all users present in the database.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Array of comments",
                    content = @Content(schema = @Schema(implementation = Uporabnik.class, type = SchemaType.ARRAY), example = "[{\"admin\":true,\"id\":1,\"password\":\"\",\"salt\":\"a\",\"username\":\"uc\",\"visitedLocations\":[1,2]}]")
            )})
    @GET
    @CrossOrigin(allowOrigin = "*")
    public Response getUporabnik() {
        log.info("Get all users.") ;
        List<Uporabnik> uporabnik = uporabnikBean.getUporabnik();

        // go through all users and set password to empty string
        for (Uporabnik u : uporabnik) {
            u.setPassword("");
        }

        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }


    @Operation(description = "Get user by ID.", summary = "Returns user with corresponding ID.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen user.",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class),example = "{\"admin\":true,\"id\":1,\"password\":\"\",\"salt\":\"a\",\"username\":\"uc\",\"visitedLocations\":[1,2]}")
            ),
            @APIResponse(responseCode = "404",
                    description = "User with given ID doesn't exist.")
    })
    @GET
    @Path("/{uporabnikId}")
    @CrossOrigin(allowOrigin = "*")
    public Response getUporabnik(@Parameter(description = "User ID.", required = true)
                                     @PathParam("uporabnikId") Integer uporabnikId) {

        log.info("Get user with id: " + uporabnikId);

        Uporabnik uporabnik = uporabnikBean.getUporabnik(uporabnikId);

        if (uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        uporabnik.setPassword("");
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @Operation(description = "login.", summary = "Checks if the login details are correct.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen user.",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class), example = "{\"admin\":true,\"id\":1,\"password\":\"\",\"salt\":\"a\",\"username\":\"uc\",\"visitedLocations\":[1,2]}")
            ),
            @APIResponse(responseCode = "404",
                    description = "User with given ID doesn't exist."),
            @APIResponse(responseCode = "401",
                    description = "Wrong password.")
    })
    @POST
    @Path("/login")
    @CrossOrigin(allowOrigin = "*")
    public Response login(Uporabnik uporabnik) {
        log.info("Login user: " + uporabnik.getUsername());
        String username = uporabnik.getUsername();
        String password = uporabnik.getPassword();
        List<Uporabnik> uporabnik1 = uporabnikBean.getUporabnikByUsername(username);
        if (uporabnik1 == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (uporabnik1.get(0).getPassword().equals(password)) {
            uporabnik1.get(0).setPassword("");
            return Response.status(Response.Status.OK).entity(uporabnik1).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Operation(description = "register.", summary = "Registers a new user.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Successfully returns chosen user.",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class), example = "{\"admin\":true,\"id\":1,\"password\":\"\",\"salt\":\"a\",\"username\":\"uc\",\"visitedLocations\":[1,2]}")
            ),
            @APIResponse(responseCode = "409",
                    description = "User with given username already exists.")
    })
    @POST
    @Path("/register")
    @CrossOrigin(allowOrigin = "*")
    public Response register(@RequestBody(
                description = "DTO object with uporabnik metadata.",
                required = true, content = @Content(
                schema = @Schema(implementation = Uporabnik.class))) Uporabnik uporabnik) {

        String username = uporabnik.getUsername();
        String password = uporabnik.getPassword();
        log.info("Register user: " + username);
        List<Uporabnik> uporabnik1 = uporabnikBean.getUporabnikByUsername(username);
        if (uporabnik1 != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        uporabnik = uporabnikBean.createUporabnik(uporabnik);
        uporabnik.setPassword("");
        return Response.status(Response.Status.OK).entity(uporabnik).build();
    }

    @Operation(description = "Delete user with given id.", summary = "Delete user with corresponding uporabnik ID.")
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "User successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "User with given comment ID was not found."
            )
    })
    @DELETE
    @Counted(name = "number_of_deleted_comments")
    @Path("/{uporabnikId}")
    @CrossOrigin(allowOrigin = "*")
    public Response deleteUporabnik(@Parameter(description = "User ID.", required = true)
                                        @PathParam("uporabnikId") Integer uporabnikId){

        log.info("Delete comment with id: " + uporabnikId);

        boolean deleted = uporabnikBean.deleteUporabnik(uporabnikId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Update visited locations for user with given id.", summary = "Update visited locations for user with corresponding uporabnik ID.")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "User successfully updated.",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class), example = "{\"admin\":true,\"id\":1,\"password\":\"\",\"salt\":\"a\",\"username\":\"uc\",\"visitedLocations\":[1,2]}")
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "User with given user ID was not found."
            )
    })
    @POST
    @Path("/{uporabnikId}/obiskane/{destinacijaId}")
    @CrossOrigin(allowOrigin = "*")
    public Response posodobiObiskane(@Parameter(description = "id uporabnika", required = true)
                                         @PathParam("uporabnikId") Integer uporabnikId,
                                     @Parameter(description = "id destinacije", required = true)
                                     @PathParam("destinacijaId") Integer destinacijaId) {

        log.info("Update visited locations for user with id: " + uporabnikId);

        Uporabnik uporabnik = uporabnikBean.getUporabnik(uporabnikId);
        if (uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        int[] newVisited = new int[uporabnik.getVisitedLocations().length + 1];
        for (int i = 0; i < uporabnik.getVisitedLocations().length; i++) {
            newVisited[i] = uporabnik.getVisitedLocations()[i];
        }
        newVisited[uporabnik.getVisitedLocations().length] = destinacijaId;
        uporabnik.setVisitedLocations(newVisited);

        Uporabnik uporabnik1 = uporabnikBean.putUporabnik(uporabnikId, uporabnik);
        uporabnik1.setPassword("");
        return Response.status(Response.Status.OK).entity(uporabnik1).build();
    }

    @POST
    @Path("/{uporabnikId}/obiskane/{destinacijaId}/delete")
    @CrossOrigin(allowOrigin = "*")
    public Response deleteObiskana(@Parameter(description = "id uporabnika", required = true)
                                         @PathParam("uporabnikId") Integer uporabnikId,
                                     @Parameter(description = "id destinacije", required = true)
                                     @PathParam("destinacijaId") Integer destinacijaId) {

        log.info("Update visited locations for user with id: " + uporabnikId);

        Uporabnik uporabnik = uporabnikBean.getUporabnik(uporabnikId);
        if (uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        int[] newVisited = new int[uporabnik.getVisitedLocations().length - 1];
        int j = 0;
        for (int i = 0; i < uporabnik.getVisitedLocations().length; i++) {
            if (uporabnik.getVisitedLocations()[i] != destinacijaId) {
                newVisited[j] = uporabnik.getVisitedLocations()[i];
                j++;
            }
        }
        uporabnik.setVisitedLocations(newVisited);

        Uporabnik uporabnik1 = uporabnikBean.putUporabnik(uporabnikId, uporabnik);
        uporabnik1.setPassword("");
        return Response.status(Response.Status.OK).entity(uporabnik1).build();
    }
}
