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

    /**
     * Retrieves {@link Category} from db by email
     * @param id id (unique ID)
     * @return {@link Category} object as JSON
     */
    public static Result retrieve(String id) {
        Logger.debug("Getting Category with id: " + id);
        Category category = Category.find.byId(id);
        if (category == null) {
            return notFound(id);
        }
        return ok(Json.toJson(category)).as("application/json");  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static Result delete(String id) {
        Logger.debug("Deleting Category with id: " + id);
        Category category = Category.find.byId(id);
        if (category == null) {
            return notFound(id);
        }
        category.delete();
        return ok(id).as("application/text");
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
        JsonNode request = request().body().asJson();
        Logger.info("Updating Category from JSON: " + request.asText());

        Category category = null;
        ObjectMapper mapper = new ObjectMapper();
        //Attempt to parse JSON
        try {
            category = mapper.readValue(request, Category.class);
            category.save();
        } catch (Exception e) {
            Logger.error(e.getMessage(), e.getCause());
            return badRequest(e.getCause().getMessage());
        }
        return ok(Json.toJson(category)).as("application/json");
    }
}
