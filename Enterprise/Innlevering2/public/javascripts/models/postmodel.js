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
    model: Post,

    url: '/posts',

    parse: function (obj) {
        return obj;
    }
});

//The Category collection
window.SortedPostCollection = Backbone.Collection.extend({
    model: Post,

    url: "/posts/" + this.name,
    parse: function (obj) {
        return obj;
    }
});

 //The Post model
    var Post = Backbone.Model.extend({
        url: '/post',

        //Empty Constructor
        initialize: function() { }

    });

    //The Post collection
    var PostList = Backbone.Collection.extend({
        model: Post,
        parse: function (obj) {
            return obj;
        }
    });