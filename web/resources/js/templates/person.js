var disabled = false;
if (this.model.id == undefined) {
    disabled = true
}
var up = this.uploader;
up.set('label', 'Select photo');
up.set('disabled', disabled);
up.set('name', "file");
up.set('onComplete', lang.hitch(this, function(file) {
    domAttr.set(this.avatar, "src", "data:image/png;base64, " + file.image);
}));
up.set('url', '/ecm/rest/employees/'+ this.model.id+'/photo');

var list = this.filelist;
list.set('uploader', up);

var btn = this.button;
btn.set('label', 'Upload');
btn.set('disabled', disabled);
btn.set('onClick', function() {
    up.upload();
});

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