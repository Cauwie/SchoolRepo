package controllers;

import models.Tag;
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
public class Tags extends Controller {

    /**
     * Gets list of all posts in the database
     *  @return
     */
    public static Result retrieveAll() {
        Logger.debug("Getting list of tags");

        List<Tag> tags = Tag.find.all();
        return ok(Json.toJson(tags)).as("application/json");
    }

    public static Result retrieveByName(String name) {
        Logger.debug("Getting list of tags by name");

        List<Tag> tags = Tag.findByName(name);
        return ok(Json.toJson(tags)).as("application/json");
    }

    public static Result retrieve(String name) {
        Logger.debug("Getting Tag with name: " + name);
        Tag tag = Tag.find.byId(name);
        if (tag == null) {
            return notFound(name);
        }
        return ok(Json.toJson(tag)).as("application/json");
    }

    public static Result delete(String name) {
        Logger.debug("Deleting Tag with name: " + name);
        Tag tag = Tag.find.byId(name);
        if (tag == null) {
            return notFound(name);
        }
        tag.delete();
        return ok(name).as("application/text");
    }

    /**
     * Creates new {@link Tag} from request body JSON data
     * and persists to database
     * @return The resulting {@link Tag} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result persist() {
        JsonNode request = request().body().asJson();
        Logger.info("Saving Tag from JSON: " + request.asText());

        ObjectMapper mapper = new ObjectMapper();
        Tag tag = null;
        //Attempt to parse JSON
        try {
            tag = mapper.readValue(request, Tag.class);

            if (tag.name.isEmpty()) {
                Logger.error("Missing 'content' node");
                return badRequest("Missing 'content' node");
            }
            tag.save();
        } catch (Exception e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return created(Json.toJson(tag));
    }
}
