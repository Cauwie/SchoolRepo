//The Category model
window.Post = Backbone.Model.extend({
    urlRoot: '/post',
    defaults:{
        "title":" ",
        "datePosted":"",
        "author":"",
        "content":"",
        "category":"",
        "tags":""
    },
    //Empty Constructor
    initialize: function() { }

});

//The Category collection
window.PostCollection = Backbone.Collection.extend({
    model: Category,
    url: '/posts',

    parse: function (obj) {
        return obj;
    }
});