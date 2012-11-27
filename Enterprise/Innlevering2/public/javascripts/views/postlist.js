/*

window.PostListView = Backbone.View.extend({
    //The DOM element is a div with class="well posts-view" (because of Twitter Bootstrap)
    tagName: 'div',
    className: 'posts-view',

    //Cache the template defined in the main html, using Underscore.JS
    template: _.template($('#all-posts-template').html()),

    initialize: function () {
        this.model.bind('reset', this.render, this);
        this.model.bind('add', this.render, this);
    },

    //Our render-function bootstraps the model JSON data into the template
    render: function() {
        this.$el.html(this.template({
            posts: this.model.toJSON()
        }));

        return this; //To allow for daisy-chaining calls
    }
});


*/
window.PostListView = Backbone.View.extend({

    tagName:'ul',

    initialize:function () {
        this.model.bind("reset", this.render, this);
        var self = this;
        this.model.bind("add", function (post) {
            $(self.el).append(new PostListItemView({model:post}).render().el);
        });
    },

    render:function (eventName) {
        _.each(this.model.models, function (post) {
            $(this.el).append(new PostListItemView({model:post}).render().el);
        }, this);
        return this;
    }
});

window.PostListItemView = Backbone.View.extend({

    tagName:"li",

    initialize:function () {
        this.template = _.template($('#post-item-template').html());
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render:function (eventName) {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }
});