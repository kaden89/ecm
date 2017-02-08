

var form = domConstruct.create('form', {
    method: 'post',
    enctype: 'multipart/form-data',
    class: 'Uploader'
}, this.uploader);

var up = new Uploader({
    label: 'Select photo',
    multiple: true,
    url: '/ecm/rest/employees/'+ this.model.id+'/photo',
    name: "file",
    onComplete:  lang.hitch(this, function(file) {
        domAttr.set(this.avatar, "src", "data:image/png;base64, " + file.image);
    })
}).placeAt(form);

var list = new FileList({
    uploader: up
}).placeAt(form);

var btn = new Button({
    label: 'Upload',
    onClick: function() {
        up.upload();
    }
}).placeAt(form);


btn.startup();
up.startup();
list.startup();


var avatar = this.avatar;
if (this.model.id != undefined){
    loadPhoto(this.model.id, avatar);
}

function loadPhoto(id, avatarNode) {
    xhr("/ecm/rest/employees/" + id + "/photo", {
        handleAs: "json",
        method: "get"
    }).then(function (data) {
        if (data.image!= undefined) {
            domAttr.set(avatar, "src", "data:image/png;base64, " + data.image);
        }
    }, function (err) {
        // Handle the error condition
    }, function (evt) {
        // Handle a progress event from the request if the
        // browser supports XHR2
    });
}
