//The Category view element
window.CategoryView = Backbone.View.extend({
    tagName: 'div',
    className: 'sidebar-nav',

    template: _.template($('#sidebar-template').html()),

    events: {
        'click button#addCategory'          :   'onCategoryAdd',
        'click button#hideCategoryErrors'   :   'hideErrors'
    },

    initialize: function () {
        this.model.bind('reset', this.render, this);
        this.model.bind('add', this.render, this);
    },

    render: function() {
        this.$el.html(this.template({
            categories: this.model.toJSON()
        }));

        return this; //To allow for daisy-chaining calls
    },

    validateCategory:function () {
        var errors = "";
        var result = true;

        if (this.model.where({'name':$('input#categoryName').val()}).length > 0) {
            errors = "You can not define a <b>name</b> that already exists. <br>";
            result = false;
        }

        //sjekk name
        if(!$('input#categoryName').val()) {
            errors = "You need to define a <b>name</b> for the category. <br>";
            result = false;
        }

        if(!result) {
            $("#categoryErrorMessages").html(errors);
            $('#category-errors').show();
        }
        return result;
    },

    onCategoryAdd: function() {
        var validCategory = this.validateCategory();
        if (validCategory) {
            app.categories.create({name:$('input#categoryName').val()});
            $('input#categoryName').val('');
        }
    },

    hideErrors:function() {
        $('#category-errors').hide();
    }
});