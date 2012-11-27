//The Category model
window.Category = Backbone.Model.extend({
    url: '/category',

    //Empty Constructor
    initialize: function() { }

});

//The Category collection
window.CategoryCollection = Backbone.Collection.extend({
    model: Category,
    url: '/categories',

    parse: function (obj) {
        return obj;
    }
});