package controllers;

import models.Category;
import models.Post;
import models.User;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.PersistenceException;
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

    public static Result retrieve(String title) {
        Logger.debug("Getting Category with name: " + title);
        Post post = Post.find.byId(title);
        if (post == null) {
            return notFound(title);
        }
        return ok(Json.toJson(post)).as("application/json");
    }

    public static Result delete(String title) {
        Logger.debug("Deleting Post with title: " + title);
        Post post = Post.find.byId(title);
        if (post == null) {
            return notFound(title);
        }
        post.delete();
        return ok(Json.toJson(post)).as("application/text");
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

        JsonNode title = request.get("title");
        JsonNode content = request.get("content");
        JsonNode author = request.get("author");
        JsonNode category = request.get("category");

        Logger.info("Title: " + title.asText());
        Logger.info("Content: " + content.asText());
        Logger.info("Author: " + author.asText());
        Logger.info("Category: " + category.asText());

        Post post = null;
        //Attempt to parse JSON

        try {
            post = Post.create(title.asText(), content.asText(),
                    author.asText(), category.asText());
        } catch (PersistenceException e) {
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
        Logger.info("Saving Post from JSON: " + request.asText());

        JsonNode id = request.get("id");
        JsonNode title = request.get("title");
        JsonNode content = request.get("content");
        JsonNode author = request.get("author");
        JsonNode category = request.get("category");
        JsonNode tags = request.get("tags");

        Iterator<JsonNode> i = request.getElements();
        while (i.hasNext()){
            Logger.info(i.next().asText());
        }

        Post post = null;
        try {
            post = Post.find.byId(id.asText());
            post.title = title.asText();
            post.author = User.find.byId(author.asText());
            post.category = Category.find.byId(category.asText());
            post.content = content.asText();
            post.save();
        } catch (PersistenceException e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return ok(Json.toJson(post)).as("application/json");
    }
}