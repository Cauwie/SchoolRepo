window.Tag = Backbone.Model.extend({

    urlRoot:"/tags",

    initialize:function () {
        this.reports = new TagCollection();
        this.reports.url = '/tags/' + this.id + '/reports';
    }
});

window.TagCollection = Backbone.Collection.extend({

    model: Tag,

    url:"/tags",

    findByName:function (name) {
        var url = (name == '') ? '/tags' : "/tags/search/" + name;
        console.log('findByName: ' + name);
        var self = this;
        $.ajax({
            url:url,
            dataType:"json",
            success:function (data) {
                console.log("search success: " + data.length);
                self.reset(data);
            }
        });
    }
});