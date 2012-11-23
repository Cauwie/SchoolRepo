package controllers;

import models.Category;
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

    public static Result retrieve(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Result delete(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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

        Category category = null;
        if(nameNode == null) {
            Logger.error("Missing 'name' node");
            return badRequest("Missing 'name' node");
        }
        try {
            category = Category.create(nameNode.asText());
        } catch (PersistenceException e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return created(Json.toJson(category));
    }

    public static Result update() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
