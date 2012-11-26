package controllers;

import models.Post;
import models.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.PersistenceException;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.created;
import static play.mvc.Results.ok;

/**
 * Controller class for the {@link models.Category} object.
 * Exposes RESTful CRUD services.
 */
public class Posts extends Controller {

    /**
     * Gets list of all categories in the database
     *  @return
     */
    public static Result retrieveAll() {
        Logger.debug("Getting list of posts");

        List<Post> posts = Post.find.all();
        return ok(Json.toJson(posts)).as("application/json");
    }

    public static Result retrieve(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Result delete(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates new {@link Post} from request body JSON data
     * and persists to database
     * @return The resulting {@link Post} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result persist() {
        JsonNode request = request().body().asJson();
        Logger.info("Saving Post from JSON: " + request.asText());

        Post post = null;
        ObjectMapper mapper = new ObjectMapper();
        //Attempt to parse JSON
        try {
            post = mapper.readValue(request, Post.class);
            post.save();
        } catch (Exception e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return created(Json.toJson(post));
    }

    /**
     * Updates {@link User} from request body JSON data
     * and saves to database
     * @return The resulting {@link User} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result update() {
        JsonNode request = request().body().asJson();
        Logger.info("Updating Post from JSON: " + request.asText());

        Post post = null;
        ObjectMapper mapper = new ObjectMapper();
        //Attempt to parse JSON
        try {
            post = mapper.readValue(request, Post.class);
            post.save();
            //user.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return badRequest(e.getCause().getMessage());
        }
        return ok(Json.toJson(post)).as("application/json");
    }
}
