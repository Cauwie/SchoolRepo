//The Category view element
window.CategoryView = Backbone.View.extend({
    //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
    tagName: 'div',
    className: 'well sidebar-nav',

    //Cache the template defined in the main html, using Underscore.JS
    template: _.template($('#sidebar-template').html()),

    events: {
        'click button#addCategory' : 'onCategoryAdd'
    },

    initialize: function () {
        this.model.bind('reset', this.render, this);
        this.model.bind('add', this.render, this);
    },

    //Our render-function bootstraps the model JSON data into the template
    render: function() {
        this.$el.html(this.template({
            categories: this.model.toJSON()
        }));

        return this; //To allow for daisy-chaining calls
    },

    //Called every time the Add-button is clicked
    onCategoryAdd: function(event) {
        //Add the new category to the collection
        app.categories.create({name: app.input.val()});

        //Clear the input field
        app.input.val('');
    }
});