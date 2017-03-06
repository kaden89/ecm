/**
 * Виджет общей формы для отображения документов и Person
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/topic",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/_WidgetBase",
    "dijit/Editor",
    "dijit/form/Select",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dijit/form/ValidationTextBox",
    "dijit/form/DateTextBox",
    "dijit/form/FilteringSelect",
    "dijit/form/CheckBox",
    "dojox/layout/TableContainer",
    "dojox/mvc/at",
    "dojox/mvc/Output",
    "dojox/form/Uploader",
    "dojox/form/uploader/FileList",
    "dojox/form/uploader/plugins/IFrame",
    "dijit/form/Form",
    "dojo/_base/lang",
    "dojo/request/xhr",
    "dojo/dom-attr",
    "myApp/ecm/ui/models/Incoming",
    "myApp/ecm/ui/models/Outgoing",
    "myApp/ecm/ui/models/Person",
    "myApp/ecm/ui/models/Task",
    "dojo/text!/ecm/ui/templates/Person.html",
    "dojo/text!/ecm/ui/templates/Incoming.html",
    "dojo/text!/ecm/ui/templates/Outgoing.html",
    "dojo/text!/ecm/ui/templates/Task.html"

], function (declare, topic, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Editor, Select, Toolbar, Button, ValidationTextBox,
             DateTextBox, FilteringSelect, CheckBox, TableContainer, at, Output, Uploader, FileList, IFrame, Form, lang, xhr, domAttr,
             Incoming, Outgoing, Person, Task, personTemplate, incomingTemplate, outgoingTemplate, taskTemplate) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        templateString: null,
        isNew: null,
        personStore: null,
        postStore: null,
        urlConfig: null,
        constructor: function (params) {
            lang.mixin(this, params);
            this.initTemplateByModel(params.model);
        }
        ,
        startup: function () {
            this.inherited(arguments);

            // this.model.initForm(this);
            this.initFormByModelType(this.model);
            var toolbar = this.toolbar;
            var createButton = new Button({
                label: "Save",
                iconClass: "dijitEditorIcon dijitEditorIconSave",
                onClick: lang.hitch(this, function () {
                    if (!this.form.validate()) return;
                    topic.publish("commonForm/Save", this);
                })
            });
            this.deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick: lang.hitch(this, function () {
                    topic.publish("commonForm/Delete", this.model);
                })
            });
            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick: lang.hitch(this, function () {
                    topic.publish("commonEvent/Close", this.isNew ? this.model.declaredClass : this.model.id);
                })
            });

            if (this.isNew) {
                this.deleteButton.set('disabled', true);
            } else {
                this.deleteButton.set('disabled', false);
            }
            toolbar.addChild(createButton);
            toolbar.addChild(this.deleteButton);
            toolbar.addChild(closeButton);
        },
        updateAfterSaveNew: function (data) {
            this.isNew = false;
            //Чтобы mvc/at отрисовал новый ID на форме
            this.model.set('id', data.id);
            lang.mixin(this.model, data);
            this.deleteButton.set('disabled', false);

            if (this.model instanceof Person) {
                this.uploader.set('url', this.urlConfig.employeeURL + "/" + this.model.id + '/photo');
                this.uploader.set('disabled', false);
                this.button.set('disabled', false);
            }
        },
        initFormByModelType: function (model) {
            if (model instanceof Person) {
                this.initPerson();
            } else if (model instanceof Incoming) {
                this.initIncoming();
            } else if (model instanceof Outgoing) {
                this.initOutgoing();
            } else if (model instanceof Task) {
                this.initTask();
            }
        },
        initTemplateByModel: function (model) {
            if (model instanceof Person) {
                this.templateString = personTemplate;
            } else if (model instanceof Incoming) {
                this.templateString = incomingTemplate;
            } else if (model instanceof Outgoing) {
                this.templateString = outgoingTemplate;
            } else if (model instanceof Task) {
                this.templateString = taskTemplate;
            }
        },
        initIncoming: function () {
            this.author.store = this.personStore;
            this.sender.store = this.personStore;
            this.recipient.store = this.personStore;

            this.author.value = this.model.authorId;
            this.sender.value = this.model.senderId;
            this.recipient.value = this.model.recipientId;

            if (!this.isNew) {
                this.personStore.get(this.authorId).then(function (data) {
                    this.author.set('item', data);
                }.bind(this));
                this.personStore.get(this.senderId).then(function (data) {
                    this.sender.set('item', data);
                }.bind(this));
                this.personStore.get(this.recipientId).then(function (data) {
                    this.recipient.set('item', data);
                }.bind(this));
            }
        },
        initOutgoing: function () {
            this.author.store = this.personStore;
            this.recipient.store = this.personStore;

            this.author.value = this.model.positionId;
            this.recipient.value = this.model.recipientId;

            if (!this.isNew) {
                this.personStore.get(this.authorId).then(function (data) {
                    this.author.set('item', data);
                }.bind(this));
                this.personStore.get(this.recipientId).then(function (data) {
                    this.recipient.set('item', data);
                }.bind(this));
            }
        },
        initTask: function () {
            this.author.store = this.personStore;
            this.executor.store = this.personStore;
            this.controller.store = this.personStore;

            this.author.value = this.model.positionId;
            this.executor.value = this.model.executorId;
            this.controller.value = this.model.controllerId;

            if (!this.isNew) {
                this.personStore.get(this.authorId).then(function (data) {
                    this.author.set('item', data);
                }.bind(this));
                this.personStore.get(this.executorId).then(function (data) {
                    this.executor.set('item', data);
                }.bind(this));
                this.personStore.get(this.controllerId).then(function (data) {
                    this.controller.set('item', data);
                }.bind(this));
            }
        },
        initPerson: function () {
            this.position.store = this.postStore;
            this.position.value = this.model.positionId;
            if (!this.isNew) {
                this.postStore.get(this.positionId).then(function (data) {
                    this.position.set('item', data);
                }.bind(this));
            }

            var up = this.uploader;
            up.set('label', 'Select photo');
            up.set('disabled', this.isNew);
            up.set('name', "file");
            up.set('onComplete', lang.hitch(this, function (file) {
                domAttr.set(this.avatar, "src", "data:image/png;base64, " + file.image);
            }));
            up.set('url', this.urlConfig.employeeURL + "/" + this.model.id + '/photo');

            var list = this.filelist;
            list.set('uploader', up);
            list.set('uploaderId', up.id);


            var btn = this.button;
            btn.set('label', 'Upload');
            btn.set('disabled', this.isNew);
            btn.set('onClick', function () {
                up.upload();
            });

            var avatar = this.avatar;
            if (!this.isNew) {
                xhr(this.urlConfig.employeeURL + "/" + this.model.id + "/photo", {
                    handleAs: "json",
                    method: "get"
                }).then(function (data) {
                    if (data != null && data.image != undefined) {
                        domAttr.set(avatar, "src", "data:image/png;base64, " + data.image);
                    }
                });
            }
        }
    });
});