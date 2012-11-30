package controllers;

import models.Category;
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
public class Categories extends Controller {

    /**
     * Gets list of all categories in the database
     *  @return
     */
    public static Result retrieveAll() {
        Logger.debug("Getting list of Categories");

        List<Category> categories = Category.find.all();
        return ok(Json.toJson(categories)).as("application/json");
    }

    /**
     * Retrieves {@link Category} from db by email
     * @param name name (unique ID)
     * @return {@link Category} object as JSON
     */
    public static Result retrieve(String name) {
        Logger.debug("Getting Category with name: " + name);
        Category category = Category.find.byId(name);
        if (category == null) {
            return notFound(name);
        }
        return ok(Json.toJson(category)).as("application/json");
    }

    public static Result delete(String name) {
        Logger.debug("Deleting Category with name: " + name);
        Category category = Category.find.byId(name);
        if(category == null) {
            return notFound(name);
        }
        category.delete();
        return ok(name).as("application/text");
    }

    /**
     * Creates new {@link models.Category} from request body JSON data
     * and persists to database
     * @return The resulting {@link models.Category} object as JSON
     */
    @BodyParser.Of(BodyParser.Json.class)
    public static Result persist() {
        JsonNode request = request().body().asJson();
        Logger.info("Saving Category from JSON: " + request.asText());

        JsonNode nameNode = request.get("name");
        Logger.info("Name: " + nameNode.asText());
        Category category = null;

        try {
            if (nameNode.asText().isEmpty()) {
                Logger.error("Missing 'content' node");
                return badRequest("Missing 'content' node");
            }
            category = Category.create(nameNode.asText());
        } catch (PersistenceException e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return created(Json.toJson(category));
    }
}
