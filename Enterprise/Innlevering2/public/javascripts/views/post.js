window.PostView = Backbone.View.extend({
    //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
    tagName: 'fieldset',
    className: 'well',

    //Cache the template defined in the main html, using Underscore.JS
    template: _.template($('#post-template').html()),

    initialize: function () {
        //this.model.bind('change', this.render, this);

        this.searchResults = new TagCollection();
        this.searchTagResultsView = new TagListView({model: this.searchResults, className: 'dropdown-menu'});

        this.searchTag = $('button#searchTag');
        this.tagName = $('input#tagName');
    },

    //Our render-function bootstraps the model JSON data into the template
    render: function() {
        $(this.el).html(this.template({
            post: this.model.toJSON(),
            categories: this.options.model2.toJSON(),
            users: this.options.model3.toJSON()
        }));
        $('.navbar-search', this.el).append(this.searchTagResultsView.render().el);

        return this; //To allow for daisy-chaining calls
    },

    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
        // You could change your model on the spot, like this:
         //var change = {};
         //change[target.name] = target.value;
         //this.model.set(change);
    },

    events:{
        "change input"          :   "change",
        "click button#save"     :   "savePost",
        "click button#delete"   :   "deletePost",
        "click button#cancel"   :   "cancel",
        "click button#addTag"   :   "addTag",
        "keyup .search-query"   :   "search",
        "keypress .search-query":   "onkeypress",
        "click .tagItem"        :   "addTag"
    },

    savePost:function () {
        var isNew = this.model.isNew();
        this.model.set({
            title:$('#title').val(),
            author:$('#author').find(":selected").text(),
            category:$('#category').find(":selected").text(),
            tags:$('#tags').val(),
            content:$('#content').val()
        });
        alert($('#author').find(":selected").text());
        alert(isNew);
        if (isNew) {
            alert("Save model");
            var self = this;
            app.postList.create(this.model, {
                success:function () {
                    alert("Post successfully saved!");
                    window.history.back();
                }
            });
        } else {
            alert("Update model");
            this.model.save();
            window.history.back();
        }

        return false;
    },

    deletePost:function () {
        if(confirm("Are you sure you want to delete this post?")){
            this.model.destroy({
                success:function () {
                    alert('Post deleted successfully');
                    window.history.back();
                },
                id:this.model.get('id')
            });
        }
        return false;
    },

    addTag:function(event) {
        //Add the new category to the collection
        var tagName = $(event.target).text();
        //alert(this.model.tags.pop());
        alert(this.model.get('tags'));
        this.model.get('tags').set({name:app.tags.get({name:tagName})});
        alert("Added tag:" + tagName);
        //Clear the input field
        this.searchTag.val('');
    },

    search: function () {
       var name = $('#searchTag').val();
       console.log('search ' + name);
       this.searchResults.findByName(name);
       setTimeout(function () {
           $('.dropdown').addClass('open');
       });
    },

    onkeypress: function (event) {
       if (event.keyCode == 13) {
           event.preventDefault();
       }
    },

    select: function(menuItem) {
       $('.nav li').removeClass('active');
       $('.' + menuItem).addClass('active');
    },

    cancel:function() {
        window.history.back();
    }
});