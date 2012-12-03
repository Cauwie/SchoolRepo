/**
 * This is the main client-side JavaScript application file.
 * The Backbone MVC structure lives here.
 *
 * @author aksel@agresvig.com
 */

//Load the application once the DOM is ready, using jQuery.ready:

Backbone.View.prototype.close = function () {
    if (this.beforeClose) {
        this.beforeClose();
    }
    this.remove();
    this.unbind();
};

var AppRouter = Backbone.Router.extend({
    routes:{
        "":"list",
        "post/new":"newPost",
        "post/:title":"postDetails",
        "category/:category":"byCategory"
    },

    initialize: function() {
        $('body').click(function () {
            $('.dropdown').removeClass("open");
        });
    },

    //Displays all existing posts
    list:function () {
        this.currentCategory = null;
        this.before(function() {
            app.showView('#main-content', new PostListView({model:app.postList}));
        });
    },

    //Displays a post based on title
    postDetails:function (title) {
        this.before(function () {
            var post = app.postList.where({'title':title})[0];
            var categories = app.categories;
            var users = app.users;
            app.showView('#main-content', new PostView({model:post, model2:categories, model3:users}));
        });
    },

    //Displays a form for new posts
    newPost:function () {
        this.before(function () {
            var categories = app.categories;
            var users = app.users;
            app.showView('#main-content', new PostView({model:new Post(), model2:categories, model3:users}));
        });
    },

    showView:function (selector, view) {
        if (this.currentView)
            this.currentView.close();
        $(selector).html(view.render().el);
        this.currentView = view;
        return view;
    },

    //Displays all posts belonging to a given category
    byCategory:function (category) {
        this.currentCategory = category;
        app.postList = null;
        this.before(function () {
            app.showView('#main-content', new PostListView({model:app.postList}));
        });
    },

    //Helper function which ensures that the data is always present / up-to-date
    before:function (callback) {
        if (this.postList && this.currentCategory != null) {
            if (callback) callback();
        } else {
            if(this.currentCategory != null) {
                this.postList = new PostCollection();
                this.postList.url = "posts/" + this.currentCategory;
                this.postList.fetch({success:function () {
                    app.showView('#main-content', new PostListView({model:app.postList}));
                    if (callback) callback();
                }});
            } else {
                this.postList = new PostCollection();
                this.postList.url = "/posts";

                this.postList.fetch({success:function () {
                 app.showView('#main-content', new PostListView({model:app.postList}));
                 if (callback) callback();
                }});
            }
        }

        if (this.categories) {
            if (callback) callback();
        } else {
            this.categories = new CategoryCollection();
            this.categories.fetch({success:function() {
                $('#sidebar-nav').html(new CategoryView({model:app.categories}).render().el);
                if (callback) callback();
            }});
        }

        if (this.users) {
            if (callback) callback();
        } else {
            this.users = new UserCollection();
            this.users.fetch({success:function() {
                if (callback) callback();
            }});
        }

        if (this.tags) {
            if (callback) callback();
        } else {
            this.tags = new TagCollection();
            this.tags.fetch({success:function() {
                if (callback) callback();
            }});
        }
    }
});

$(function() {
    app = new AppRouter();
    Backbone.history.start();
});