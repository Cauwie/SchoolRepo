//The Error view element
window.ErrorView = Backbone.View.extend({
    //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
    tagName: 'div',
    className: 'alert alert-block',

    //Cache the template defined in the main html, using Underscore.JS
    template: _.template($('#error-template').html()),
    /*
    events: {
        'click button#addCategory' : 'onCategoryAdd'
    },
    */
    initialize: function () {

    },
    //Our render-function bootstraps the model JSON data into the template
    render: function() {
        this.$el.html(this.template({
            errorMessage: this.errorMessage;
        }));

        return this; //To allow for daisy-chaining calls
    }
});