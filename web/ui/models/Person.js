define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/request/xhr",
    "dojo/dom-attr",
    "dojo/Stateful",
    "dojo/store/Observable",
    "dijit/form/FilteringSelect",
    "dojox/mvc/at",
    "myApp/ecm/ui/utils/Utils",
    "myApp/ecm/ui/mixins/_FormAwareMixin"
], function (declare,
             lang,
             xhr,
             domAttr,
             Stateful,
             Observable,
             FilteringSelect,
             at,
             Utils,
             _FormAwareMixin) {
    var Person = declare("Person", [Stateful, _FormAwareMixin], {
        birthday: null,
        _birthdaySetter: function (value) {
            this.birthday = Utils.addTimezoneToDate(value);
        },
        initForm: function (form) {
            var isNew = form.isNew;
            form.position.store = form.postStore;
            form.position.value = this.positionId;
            if (!isNew){
                form.postStore.get(this.positionId).then(function (data) {
                    form.position.set('item', data);
                }.bind(this));
            }

            var up = form.uploader;
            up.set('label', 'Select photo');
            up.set('disabled', isNew);
            up.set('name', "file");
            up.set('onComplete', lang.hitch(form, function(file) {
                domAttr.set(form.avatar, "src", "data:image/png;base64, " + file.image);
            }));
            up.set('url', form.urlConfig.employeeURL + "/"+ form.model.id+'/photo');

            var  list = form.filelist;
            list.set('uploader', up);
            list.set('uploaderId', up.id);


            var btn = form.button;
            btn.set('label', 'Upload');
            btn.set('disabled', isNew);
            btn.set('onClick', function() {
                up.upload();
            });

            var avatar = form.avatar;
            if (!form.isNew){
                loadPhoto(form.model.id);
            }

            function loadPhoto(id) {
                xhr(form.urlConfig.employeeURL + "/"+id + "/photo", {
                    handleAs: "json",
                    method: "get"
                }).then(function (data) {
                    if (data!= null && data.image!= undefined) {
                        domAttr.set(avatar, "src", "data:image/png;base64, " + data.image);
                    }
                });
            }
        },
        updateFormAfterSaveNew: function (form) {
            form.uploader.set('url', form.urlConfig.employeeURL + "/"+ form.model.id+'/photo');
            form.uploader.set('disabled', false);
            form.button.set('disabled', false);
        }
    });
    //static field
    Person.columns = [
        {id: 'id', field: 'id', name: 'id', width: '5%'},
        {id: 'firstname', field: 'firstname', name: 'Firstname', width: '19%'},
        {id: 'surname', field: 'surname', name: 'Surname', width: '19%'},
        {id: 'patronymic', field: 'patronymic', name: 'Patronymic', width: '19%'},
        {id: 'position.post', field: 'positionName', name: 'Position', width: '19%'},
        {id: 'birthday', field: 'birthday', name: 'Birthday', width: '19%'}
    ];
    Person.tableName = "Persons";

    return Person;
})