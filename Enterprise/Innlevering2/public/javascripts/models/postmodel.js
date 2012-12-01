//The Category model
window.Post = Backbone.Model.extend({
    //idAttribute: 'title',
    urlRoot: '/post',
    defaults:{
        "id":null,
        "title":null,
        "datePosted":null,
        "author":"",
        "content":"",
        "category":"",
        "tags":[
                    {"name":"tedsadsa","dateCreated":"1354394942970"},
                    {"name":"TEfsdssST2","dateCreated":"1354394942970"},
                    {"name":"TEdffdsST2","dateCreated":"1354394942970"},
                    {"name":"TEsdssdffST2","dateCreated":"1354394942970"},
                    {"name":"TEaaasaaST2","dateCreated":"1354394942970"},
                    {"name":"TEddddST3","dateCreated":"1354394942970"},
                    {"name":"TEffST4","dateCreated":"1354394942970"}
               ]
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
    },    /*
    methodToURL: {
        'read': '/post',
        'create': '/post',
        'update': '/post',
    },

    sync: function(method, model, options) {
        options = options || {};
        options.url = model.methodToURL[method.toLowerCase()];

        Backbone.sync(method, model, options);
    },
    */
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