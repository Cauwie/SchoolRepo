window.Employee = Backbone.Model.extend({

    urlRoot:"../api/employees",

    initialize:function () {
        this.reports = new EmployeeCollection();
        this.reports.url = '../api/users/' + this.id + '/reports';
    }

});

window.UserCollection = Backbone.Collection.extend({

    model: User,

    url:"../api/users",

    findByName:function (key) {
        var url = (key == '') ? '../api/users' : "../api/users/search/" + key;
        console.log('findByName: ' + key);
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