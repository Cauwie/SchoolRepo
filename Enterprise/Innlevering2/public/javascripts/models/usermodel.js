//The User model
window.User = Backbone.Model.extend({
    url: '/user',

    //Empty Constructor
    initialize: function() { }

});

//The User collection
window.UserCollection = Backbone.Collection.extend({
    model: Category,
    url: '/users',

    parse: function (obj) {
        return obj;
    }
});