window.PostView = Backbone.View.extend({
    //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
    tagName: 'div',
    className: 'well posts-nav',

    //Cache the template defined in the main html, using Underscore.JS
    template: _.template($('#post-template').html()),

    initialize: function () {
        this.model.bind('change', this.render, this);

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

        return this; //To allow for daisy-chaining calls
    },

    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
        // You could change your model on the spot, like this:
         var change = {};
         change[target.name] = target.value;
         this.model.set(change);
    },

    events:{
        "change input"          :   "change",
        "click button#save"     :   "savePost",
        "click button#delete"   :   "deletePost",
        "click button#cancel"   :   "cancel",
        "click button#addTag"   :   "addTag"
    },

    savePost:function () {
        this.model.set({
            title:$('#title').val(),
            author:$('#author').val(),
            category:$('#category').val(),
            tags:$('#tags').val(),
            content:$('#content').val()
        });
        if (this.model.isNew()) {
            var self = this;
            app.postList.create(this.model, {
                success:function () {
                    app.navigate('post/' + self.model.id, false);
                }
            });
        } else {
            this.model.save();
        }

        return false;
    },

    deletePost:function () {
        this.model.destroy({
            success:function () {
                alert('Post destroyed successfully');
            }
        });
        app.postList.remove(this.model, {
            success:function () {
                alert('Post removed from list successfully');
            }
        });
        return false;
    },

    addTag:function() {
        //Add the new category to the collection

        alert(this.model.tags.pop());
        this.model.tags.add({name: this.searchTag.val()});
        //Clear the input field
        this.searchTag.val('');
    },

    cancel:function() {
        app.showView("main-content", new PostListView({model:app.postList}));
    }
});