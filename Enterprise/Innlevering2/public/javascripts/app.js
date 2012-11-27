/**
 * This is the main client-side JavaScript application file.
 * The Backbone MVC structure lives here.
 *
 * @author aksel@agresvig.com
 */

//Load the application once the DOM is ready, using jQuery.ready:

Backbone.View.prototype.close = function () {
    console.log('Closing view ' + this);
    if (this.beforeClose) {
        this.beforeClose();
    }
    this.remove();
    this.unbind();
};

//This view resembles the the main area of our app
var AppRouter = Backbone.Router.extend({
    routes:{
        "":"list",
        "post/new":"newPost",
        "post/:title":"postDetails",
        "category/:category":"byCategory"
    },

    list:function () {
        this.before();
    },

    postDetails:function (title) {
        this.before(function () {
            var post = app.postList.where({'title':title}).pop();
            var categories = app.categories;
            var users = app.users;
            app.showView('#main-content', new PostView({model:post, model2:categories, model3:users}));
        });
    },

    newPost:function () {
        this.before(function () {
            app.showView('#main-content', new PostView({model:new Post()}));
        });
    },

    showView:function (selector, view) {
        if (this.currentView)
            this.currentView.close();
        $(selector).html(view.render().el);
        this.currentView = view;
        return view;
    },

    byCategory:function (category) {
        this.before(function() {
            app.showView('#main-content', new PostListView({model:app.postList}));
        });

    },

    before:function (callback) {
        if (this.postList) {
            if (callback) callback();
        } else {
            this.postList = new PostCollection();
            this.postList.fetch({success:function () {
                $('#posts').html(new PostListView({model:app.postList}).render().el);
                if (callback) callback();
            }});
            this.categories = new CategoryCollection();
            this.categories.fetch({success:function() {
                $('#sidebar').html(new CategoryView({model:app.categories}).render().el);
            }});
            this.users = new UserCollection();
            this.users.fetch();
        }
    }
});

$(function() {
    app = new AppRouter();
    Backbone.history.start();
});