window.TagListView = Backbone.View.extend({
    tagName:'ul',

    className:'dropdown-menu',

    template: _.template($('#tag-item').html()),
    initialize:function () {
        var self = this;
        this.model.bind("reset", this.render, this);
        this.model.bind("add", function (tag) {
            $(self.el).append(new TagListItemView({model:tag}).render().el);
        });
    },

    render:function () {
        $(this.el).empty();
        _.each(this.model.models, function (tag) {
            $(this.el).append(new TagListItemView({model:tag}).render().el);
        }, this);
        return this;
    }
});

window.TagListItemView = Backbone.View.extend({
    tagName:"li",
    template: _.template($('#tag-item').html()),

    initialize:function () {
        this.model.bind("change", this.render, this);
        this.model.bind("destroy", this.close, this);
    },

    render:function () {
        $(this.el).html(this.template(this.model.toJSON()));
        return this;
    }

});