package controllers;

import models.Category;
import models.Post;
import models.Tag;
import models.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONArray;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
     * Gets list of all posts in the database
     *  @return
     */
    public static Result retrieveAll() {
        Logger.debug("Getting list of posts");

        List<Post> posts = Post.find.all();
        return ok(Json.toJson(posts)).as("application/json");
    }

    public static Result retrieveByCategory(String name) {
        Logger.debug("Getting list of posts by category");

        List<Post> posts = Post.findByCategory(name);
        return ok(Json.toJson(posts)).as("application/json");
    }

    public static Result retrieve(String id) {
        Logger.debug("Getting Category with name: " + id);
        Post post = Post.find.byId(id);
        if (post == null) {
            return notFound(id);
        }

        return ok(Json.toJson(post)).as("application/json");
    }

    public static Result delete(String id) {
        Logger.debug("Deleting Post with id: " + id);
        Post post = Post.find.byId(id);
        if (post == null) {
            return notFound(id);
        }

        post.delete();
        return ok(id).as("application/text");
    }

    /**
     * Creates new {@link Post} from request body JSON data
     * and persists to database
     * @return The resulting {@link Post} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result persist() {
        JsonNode request = request().body().asJson();

        if (request.isNull()) {
            Logger.error("The Request was empty.");
            return badRequest("The Request was empty.");
        }
        Logger.info("Updating Post from JSON: " + request);

        JsonNode title = request.get("title");
        JsonNode content = request.get("content");
        JsonNode email = request.get("author").get("email");
        JsonNode category = request.get("category").get("name");

        Post post = null;
        try {

            post = Post.create(title.asText(), content.asText(), email.asText(), category.asText());

            Iterator<JsonNode> tagsIterator = request.get("tags").getElements();
            ArrayList<Tag> tags = new ArrayList<Tag>();

            while (tagsIterator.hasNext()) {
                Tag tempTag = Tag.find.byId(tagsIterator.next().get("name").asText());
                tags.add(tempTag);
            }
            post.tags = tags;
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
    public static Result update(String id) {

        JsonNode request = request().body().asJson();
        if (request.isNull()) {
            Logger.error("The Request was empty.");
            return badRequest("The Request was empty.");
        }

        Logger.info("Updating Post from JSON: " + request);

        ObjectMapper mapper = new ObjectMapper();
        Post post = null;
        //Attempt to parse JSON
        try {
            post = mapper.readValue(request, Post.class);
            post.update();
        } catch (Exception e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return ok(Json.toJson(post)).as("application/json");
    }
}