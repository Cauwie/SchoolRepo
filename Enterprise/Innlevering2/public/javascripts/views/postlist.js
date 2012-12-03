window.PostListView = Backbone.View.extend({

    tagName:'ul',
    className:'nav nav-pills nav-stacked',

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
    className:'well',

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