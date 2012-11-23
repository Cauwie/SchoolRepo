package controllers;

import models.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import play.Logger;
import play.api.libs.json.JerksonJson;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for the {@link models.User} object.
 * Exposes RESTful CRUD services.
 */
public class Users extends Controller {

    /**
     * Gets list of all users in the database
     * @return List of users as JSON
     */
    public static Result retrieveAll() {
        Logger.debug("Getting list of users");
        List<User> users = User.find.all();
        return ok(Json.toJson(users)).as("application/json");
    }

    /**
     * Retrieves {@link User} from db by email
     * @param email email (unique ID)
     * @return {@link User} object as JSON
     */
    public static Result retrieve(String email) {
        Logger.debug("Getting User with email: " + email);
        User user = User.find.byId(email);
        if(user == null) {
            return notFound(email);
        }
        return ok(Json.toJson(user)).as("application/json");
    }

    public static Result delete(String email) {
        Logger.debug("Deleting User with email: " + email);
        User user = User.find.byId(email);
        if(user == null) {
            return notFound(email);
        }
        user.delete();
        return ok(email).as("application/text");
    }

    /**
     * Creates new {@link User} from request body JSON data
     * and persists to database
     * @return The resulting {@link User} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result persist() {
        JsonNode request = request().body().asJson();
        Logger.info("Saving User from JSON: " + request.asText());

        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        //Attempt to parse JSON
        try {
            user = mapper.readValue(request, User.class);
            user.save();
        } catch (Exception e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return created(Json.toJson(user));
    }

    /**
     * Updates {@link User} from request body JSON data
     * and saves to database
     * @return The resulting {@link User} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update() {
        JsonNode request = request().body().asJson();
        Logger.info("Updating User from JSON: " + request.asText());

        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        //Attempt to parse JSON
        try {
            user = mapper.readValue(request, User.class);
            user.save();
            //user.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return badRequest(e.getCause().getMessage());
        }
        return ok(Json.toJson(user)).as("application/json");
    }
}
