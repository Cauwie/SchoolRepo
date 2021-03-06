package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.sun.corba.se.impl.logging.POASystemException;
import helper.YamlJodaTimeConstructor;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Yaml;
import runners.PlayJUnitRunner;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests model classes, EJB Entity operations, such as CRUD etc. 
 * 
 * @author aksel@agresvig.com
 *
 */
@RunWith(PlayJUnitRunner.class)
public class ModelTest {

    //IDs
	private static String EMAIL = "aksel@agresvig.com";
    private static int POST_ONE_ID = 0;
	private static String POST_ONE = "Post One";
	private static String CATEGORY_ONE = "Category One";
    private static final String POST_CONTENT = "This is the first post!";

	private static String TAG_ONE = "Tag One";
    public static DdlGenerator ddl;
    public static EbeanServer server;

    /**
     * Get some info we need to reset the DB between tests.
     *
     */
    @BeforeClass
    public static void setup() {
        server = Ebean.getServer("default");

        ServerConfig config = new ServerConfig();
        config.setDebugSql(true);

        ddl = new DdlGenerator((SpiEbeanServer) server, new H2Platform(), config);
    }

    /**
     * Runs before every test and resets the database
     * @throws IOException
     */
    @Before
    public void resetDb() throws IOException {

        // drop
        String dropScript = ddl.generateDropDdl();
        ddl.runScript(false, dropScript);

        // create
        String createScript = ddl.generateCreateDdl();
        ddl.runScript(false, createScript);

        // insert data
        loadAndSaveTestdata();
    }

    private void loadAndSaveTestdata() {
    	System.out.println("Test:" + Thread.currentThread().getContextClassLoader());
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("testdata.yaml");
        //Using Yaml instance with custom constructor here in order to support JodaTime dates with Yaml testing..
        Yaml yaml = new Yaml(new YamlJodaTimeConstructor());
        Map data = (Map) yaml.load(input);
        Ebean.save((Collection) (data.get("users")));
        Ebean.save((Collection) (data.get("categories")));
        Ebean.save((Collection) (data.get("tags")));
    }

    @Test
    public void testCreatePost() {
        Post createdPost = Post.create("title", "content", EMAIL, CATEGORY_ONE);

        Post post = Post.find.byId(String.valueOf(createdPost.id));
        assertThat(post).isNotNull();
        assertThat(post.title).isEqualToIgnoringCase("title");
        assertThat(post.author.email).isEqualToIgnoringCase(EMAIL);
        assertThat(post.category.name).isEqualToIgnoringCase(CATEGORY_ONE);
        assertThat(post.tags).isEmpty();
    }

    @Test
    public void testDeletePost() {
        Post createdPost = Post.create("title", "content", EMAIL, CATEGORY_ONE);

        Post post = Post.find.byId(String.valueOf(createdPost.id));
        assertThat(post).isNotNull();
        post.delete();

        post = Post.find.byId(String.valueOf(createdPost.id));
        assertThat(post).isNull();
    }

    @Test
    public void testRetrieveUser() {
        List<User> userList = User.find.all();
        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(1);

        User aksel = User.find.byId(EMAIL);

        assertThat(aksel).isNotNull();
        assertThat(EMAIL).isEqualTo(aksel.email);
        assertThat(aksel.firstName).isEqualTo("Aksel");
        assertThat(aksel.lastName).isEqualTo("Gresvig");
        assertThat(aksel.password).isEqualTo("secret");
        assertThat(aksel.isAdmin).isTrue();

        DateTime createDate = new DateTime(1950, 06, 22, 0, 0);
        compareDate(aksel.dateOfBirth, createDate);
    }

    @Test
    public void testCreateUser() {
        User.create("Bob", "Bobson", "bob@bobson.com", "topsecret");

        User bobAgain = User.find.byId("bob@bobson.com");
        assertThat(bobAgain.dateOfBirth).isNotNull();
        assertThat(bobAgain.email).isEqualToIgnoringCase("bob@bobson.com");
        assertThat(bobAgain.firstName).isNotEmpty();
        assertThat(bobAgain.lastName).isNotEmpty();
        assertThat(bobAgain.isAdmin).isFalse();
    }

    @Test
    public void testDeleteUser() {
        User aksel = User.find.byId(EMAIL);
        assertThat(aksel).isNotNull();
        aksel.delete();

        User noOne = User.find.byId(EMAIL);
        assertThat(noOne).isNull();
    }

    @Test
    public void testRetrieveCategory() {
        List<Category> catList = Category.find.all();

        assertThat(catList).isNotEmpty();

        Category category = Category.find.byId(CATEGORY_ONE);

        assertThat(category.name).isEqualTo(CATEGORY_ONE);
        DateTime createDate = new DateTime(2012, 10, 20, 0, 0);
        compareDate(createDate, category.dateCreated);
    }

    @Test
    public void testCreateCategory() {
        String categoryName = "Category two";
        Category categoryTwo = Category.create(categoryName);

        Category retrievedCategory = Category.find.byId(categoryName);
        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.name).isNotEmpty();
        assertThat(retrievedCategory.dateCreated).isNotNull();

    }

    @Test
    public void testDeleteCategory() {
        Category categoryTwo = Category.find.byId(CATEGORY_ONE);
        assertThat(categoryTwo).isNotNull();
        categoryTwo.delete();

        Category noCategory = Category.find.byId(CATEGORY_ONE);
        assertThat(noCategory).isNull();
    }

    @Test
    public void testRetrieveTag() {
        createTag();

        List<Tag> tagList = Tag.find.all();
        assertThat(tagList).isNotEmpty();

        DateTime createDate = new DateTime(2012, 10, 10, 0, 0);
        Tag tagTwo = new Tag("Tag two", createDate);
        tagTwo.save();

        Tag tag = Tag.find.byId("Tag two");
        assertThat(tag).isNotNull();
        assertThat(tag.name).isEqualTo("Tag two");
        compareDate(createDate, tag.dateCreated);
    }

    @Test
    public void testCreateTag() {
        Tag.create("Tag two");

        Tag retrievedTag = Tag.find.byId("Tag two");
        assertThat(retrievedTag).isNotNull();
        assertThat(retrievedTag.name).isNotEmpty();
        assertThat(retrievedTag.dateCreated).isNotNull();
    }

    @Test
    public void testDeleteTag() {
        createTag();
        Tag tag = Tag.find.byId(TAG_ONE);
        assertThat(tag).isNotNull();
        assertThat(tag.name).isNotEmpty();
        tag.delete();

        Tag noTag = Tag.find.byId(TAG_ONE);
        assertThat(noTag).isNull();
    }

    @Test
    public void testFindTagByName() {
        createTag();
        List<Tag> tags = Tag.findByName(TAG_ONE);
        assertThat(tags).isNotNull();
        assertThat(tags.size()).isGreaterThan(0);

        Tag tag = tags.get(0);
        assertThat(tag.name).isEqualToIgnoringCase(TAG_ONE);
    }

    @Test
    public void testSearchForTagByName() {
        createTag();
        List<Tag> tags = Tag.searchByName(TAG_ONE);
        assertThat(tags).isNotNull();
        assertThat(tags.size()).isGreaterThan(0);

        for (Tag t: tags) {
            assertThat(t).isNotNull();
            assertThat(t.name).contains(TAG_ONE);
        }
    }

    @Test
    public void testCreateAndRetrieveNewPost() {
        createTag();
        createPost();

        List<Post> postList = Post.find.all();
        assertThat(postList).isNotEmpty();
        assertThat(postList.get(0).title).isEqualTo(POST_ONE);

        Post postOne = Post.find.all().get(0);

        assertThat(postOne).isNotNull();
        compareDate(postOne.datePosted, new DateTime());
        assertThat(postOne.content).contains(POST_CONTENT);
        assertThat(postOne.title).isEqualTo(POST_ONE);
        assertThat(postOne.category.name).isEqualTo(CATEGORY_ONE);
        assertThat(postOne.tags).isNotEmpty();
        assertThat(postOne.tags.get(0).name).isEqualTo(TAG_ONE);
        assertThat(postOne.author.email).isEqualTo(EMAIL);
    }

    @Test
    public void findPostsByUser() {
        createPost();

        List<Post> posts = Post.findByUser(EMAIL);
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).title).isEqualTo(POST_ONE);
    }

    @Test
    public void findPostsByCategory() {
        createPost();

        List<Post> posts = Post.findByCategory(CATEGORY_ONE);
        assertThat(posts).isNotEmpty();
        assertThat(posts.get(0).title).isEqualTo(POST_ONE);
    }

    @Test
    public void testAddRemoveTags() {
        createTag();
        createPost();
        Post post = Post.find.byId(String.valueOf(POST_ONE_ID));
        assertThat(post).isNotNull();
        assertThat(post.tags).hasSize(1);

        String tagTwo = "Tag Two";
        Tag.create(tagTwo);

        //Test add
        Post.addTag(POST_ONE_ID, tagTwo);
        post = Post.find.byId(String.valueOf(POST_ONE_ID));
        assertThat(post.tags).hasSize(2);

        //Check that we cant add same tag twice
        Post.addTag(POST_ONE_ID, tagTwo);
        post = Post.find.byId(String.valueOf(POST_ONE_ID));
        assertThat(post.tags).hasSize(2);

        //Test remove
        Post.removeTag(POST_ONE_ID, TAG_ONE);
        post = Post.find.byId(String.valueOf(POST_ONE_ID));
        assertThat(post.tags).hasSize(1);
        assertThat(post.tags.get(0).name).isEqualTo(tagTwo);
    }

    @Test(expected = PersistenceException.class)
    public void testPrimaryKeyViolationForPost() {
        createPost();
        createPost();
    }

    @Test(expected = PersistenceException.class)
     public void testPrimaryKeyViolationForTag() {
        createTag();
        createTag();
    }

    /**
     * Helpmethod that just inserts basic test-post
     * @return
     */
    private Post createPost() {
        Post p = Post.create(POST_ONE, POST_CONTENT, EMAIL, CATEGORY_ONE);
        POST_ONE_ID = p.id;
        Post.addTag(POST_ONE_ID, TAG_ONE);
        return p;
    }

    private Tag createTag() {
        Tag tag = Tag.create(TAG_ONE);
        return tag;
    }

    private void compareDate(DateTime date1, DateTime date2) {
        assertThat(date1.getYear()).isEqualTo(date2.getYear());
        assertThat(date1.getDayOfMonth()).isEqualTo(date2.getDayOfMonth());
        assertThat(date1.getMonthOfYear()).isEqualTo(date2.getMonthOfYear());
    }

    private void compareDateAndTime(DateTime date1, DateTime date2) {
        compareDate(date1, date2);
        assertThat(date1.getHourOfDay()).isEqualTo(date2.getHourOfDay());
        assertThat(date1.getMinuteOfHour()).isEqualTo(date2.getMinuteOfHour());
        assertThat(date1.getSecondOfMinute()).isEqualTo(date2.getSecondOfMinute());
    }
}
