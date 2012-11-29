//The Category model
window.Post = Backbone.Model.extend({
    //idAttribute: 'title',
    url: '/post',
    defaults:{
        "title":null,
        "datePosted":null,
        "author":"",
        "content":"",
        "category":""
    },

    addTag:function (name) {
        var url = (name == '') ? '/post' : "/post/" + this.model.get("id") + "/addTag/" + name;
        console.log('Adding tag: ' + name);
        var self = this;
        $.ajax({
            url:url,
            dataType:"json",
            success:function (data) {
                console.log("search success: " + data.length);
                self.reset(data);
            }
        });
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