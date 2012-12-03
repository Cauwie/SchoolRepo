//The Post model
window.Post = Backbone.Model.extend({
    urlRoot: '/post',
    defaults:{
        "id":null,
        "title":"",
        "datePosted":null,
        "author":"",
        "content":"",
        "category":"",
        "tags":[]
    },

    initialize: function() { }
});

//The Post collection
window.PostCollection = Backbone.Collection.extend({
    model: Post,

    url: '/posts',

    parse: function (obj) {
        return obj;
    }
});