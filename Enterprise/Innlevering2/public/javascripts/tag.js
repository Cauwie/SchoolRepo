/**
 * This is the main client-side JavaScript application file.
 * The Backbone MVC structure lives here.
 *
 * @author aksel@agresvig.com
 */

//Load the application once the DOM is ready, using jQuery.ready:
$(function() {

    //The Category model
    var Category = Backbone.Model.extend({
        url: '/category',

        //Empty Constructor
        initialize: function() { }

    });

    //The Category collection
    var CategoryList = Backbone.Collection.extend({
        model: Category,
        url: '/categories',

        parse: function (obj) {
            return obj;
        }
    });

    //Create global category list
    var Categories = new CategoryList();

    //The Category view element
    var CategoryView = Backbone.View.extend({
        //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
        tagName: 'div',
        className: 'well sidebar-nav',

        //Cache the template defined in the main html, using Underscore.JS
        template: _.template($('#sidebar-template').html()),

        initialize: function () {
            this.collection.bind('reset', this.render, this);
            this.collection.bind('add', this.render, this);
        },

        //Our render-function bootstraps the model JSON data into the template
        render: function() {
            this.$el.html(this.template({
                categories: this.collection.toJSON()
            }));

            return this; //To allow for daisy-chaining calls
        }
    });

    //This view resembles the the main area of our app
    var YabeApp = Backbone.View.extend({

        //We won't generate a new element here, but rather bind to an existing DOM element
        el : $('#page-container'),

        //Declare UI events
        events: {
            'keydown input#categoryName'    : 'onCategoryNameChange',
            'click button#addCategory'      : 'onCategoryAdd'
        },

        //Fetch the categories and show the CategoryView
        initialize: function() {
            //Store a reference to "this" as this view-object in the specified methods
            //This is really just because javascript gives us a rather useless "this" in for example event-handlers
            _.bindAll(this, 'onCategoryNameChange', 'onCategoryAdd');

            //Load the categories from the server
            Categories.fetch();

            //Instantiate the category view with the Categories as the "collection" property
            var catView = new CategoryView({collection: Categories}).render();
            //And add it to the DOM
            this.$('#sidebar').append(catView.render().el);

            //Store a reference to the UI components for convenience
            this.addButton = $('button#addCategory');
            this.input = $('input#categoryName');
        },

        //Called every time the text in the category-name input changes
        onCategoryNameChange: function(event) {
            //Show the add-button if its hidden and text is entered
            if(this.addButton.is(':hidden') && this.input.val())
                this.addButton.show('fast');
        },

        //Called every time the Add-button is clicked
        onCategoryAdd: function(event) {

            //Add the new category to the collection
            Categories.create({name: this.input.val()});

            //Clear the input field
            this.input.val('');
        }
    });

    var App = new YabeApp;
});