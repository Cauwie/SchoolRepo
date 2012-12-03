window.PostView = Backbone.View.extend({
    //The DOM element is a div with class="well sidebar-nav" (because of Twitter Bootstrap)
    tagName: 'fieldset',
    className: 'well',

    //Cache the template defined in the main html, using Underscore.JS
    template: _.template($('#post-template').html()),

    initialize: function () {
        //this.model.bind('change', this.render, this);
        //_.bindAll(this, "render");
        _.bindAll(this.model);
        this.searchResults = new TagCollection();
        this.searchTagResultsView = new TagListView({model: this.searchResults, className: 'dropdown-menu'});

        this.currentTags = new TagCollection();
        this.tagsView = new PostTagsView({model:this.currentTags});
        this.searchTag = $('button#searchTag');

    },

    //Our render-function bootstraps the model JSON data into the template
    render: function() {
        if (this.model != null){
            $(this.el).html(this.template({
                post: this.model.toJSON(),
                categories: this.options.model2.toJSON(),
                users: this.options.model3.toJSON()

            }));
            $('.navbar-search', this.el).append(this.searchTagResultsView.render().el);

        }
        return this; //To allow for daisy-chaining calls
    },

    change:function (event) {
        var target = event.target;
        console.log('changing ' + target.id + ' from: ' + target.defaultValue + ' to: ' + target.value);
    },

    events:{
        "change input"                  :   "change",
        "click button#save"             :   "savePost",
        "click button#delete"           :   "deletePost",
        "click button#cancel"           :   "cancel",
        "click button#addTag"           :   "addTag",
        "click button#hidePostErrors"   :   "hideErrors",
        "keyup input#searchTag"         :   "search",
        "keypress input#searchTag"      :   "onkeypress",
        "click .tagItem"                :   "addTag",
        "click .tag"                    :   "removeTag"
    },

    savePost:function () {
        var isNew = this.model.isNew();
        var validPost = this.validatePost();
        this.model.set({
            title:$('#title').val(),
            author:app.users.where({'email':$('#author').find(":selected").val()}).pop(),
            category:app.categories.where({'name':$('#category').find(":selected").val()}).pop(),
            content:$('#content').val()
        });

        if(validPost){
            if (isNew) {
                app.postList.create(this.model, {
                    success:function () {
                        alert("Post successfully saved!");
                        window.history.back();
                    }
                });
            } else {
                this.model.save();
                window.history.back();
            }
        }
        return false;
    },

    validatePost:function () {
        var errors = "";
        var result = true;
        $("#postErrorMessages").html("");

        if(!$('#title').val()) {
            errors = "You need to define a <b>title</b>. <br>";
            result = false;
        }

        if($('#author').find(":selected").text() == "--select a author--") {
            errors = errors + "You need to select an <b>author</b> <br>";
            result = false;
        }

        if($('#category').find(":selected").text() == "--select a category--") {
            errors = errors + "You need to select a <b>category</b> <br>";
            result = false;
        }

        if($('#content').val().length > 256) {
            errors = errors + "The maximum length of <b>content</b> is <b>256</b> <br>";
            result = false;
        }

        if($('#title').val() != this.model.get('title')) {
            if (app.postList.where({'title':$('#title').val()}).length > 0) {
                errors = errors + "A post with that <b>title</b> already exists. <br>";
                result = false;
            }
        }

        if(!result) {
            $("#postErrorMessages").html(errors);
            $('#post-errors').show();
        }

        return result;
    },

    deletePost:function () {
        if(confirm("Are you sure you want to delete this post?")){
            this.model.destroy({
                success:function () {
                    app.postList.remove(this.model);
                    alert('Post deleted successfully');
                    window.history.back();
                },
                id:this.model.get('id')
            });
        }
        return false;
    },

    addTag:function(event) {
        var tagName = $(event.target).text();
        currentTag = app.tags.where({name:tagName}).pop();
        this.model.get('tags').push(currentTag.toJSON());

        this.searchTag.val('');
        this.tagsView.model = this.model;
        this.tagsView.render();
    },

    removeTag:function(event) {
        var tagName = $(event.target).text();
        this.findAndRemove(this.model.get('tags'), 'name', tagName);
        this.render();
    },

    findAndRemove: function(array, property, value) {
       $.each(array, function(index, result) {
          if(result[property] == value) {
              array.splice(index, 1);
          }
       });
    },

    search: function () {
        var name = $('#searchTag').val();
        console.log('search ' + name);
        if(!name.empty) {
            this.searchResults.findByName(name);
        }
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
    },

    hideErrors:function() {
        $('#post-errors').hide();
    }
});