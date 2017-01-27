/**
 * Created by dkarachurin on 19.01.2017.
 */
define([
    "dojo/_base/declare",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/_WidgetBase",
    "dojo/Stateful",
    "dojo/dom",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dojo/dom-form",
    "dojo/dom-attr",
    "dijit/registry",
    "dojo/request",
    "dojo/request/xhr",
    "dojo/dom-construct",
    "dojox/form/Uploader",
    "dojox/form/uploader/FileList",
    "dojox/form/uploader/plugins/IFrame",
    "dijit/form/Form",
    "dojo/_base/lang",
    "dojox/mvc/at",
    "dojo/on",
    "dojo/require",
    "dijit/layout/ContentPane",
    "dijit/layout/BorderContainer",
    "dijit/form/TextBox",
    "dijit/form/ValidationTextBox",
    "dijit/form/DateTextBox",
    "dojox/form/Uploader",
    "dojox/form/uploader/FileList",
    "dojox/mvc/Output"


], function (declare, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Stateful, dom, Toolbar, Button, domForm, domAttr, registry, request, xhr,
            domConstruct, Uploader, FileList, IFrame, Form, lang) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        // _setModel: function(model){
        //
        //     this._set("model", new Stateful(model));
        // },
        constructor: function (args) {
            this.templateString = args.template;
            this.model = new Stateful(args.entity);
        }
        ,
        startup: function () {
            this.inherited(arguments);

             var someId = this.model.id;
            // var form = registry.byId("personForm");
            // form.id = form.id+id;


            // var avatar = dom.byId("avatar");
            // domAttr.set(avatar,"src","data:image/png;base64, "+this.model.photo);


            var toolbar = this.toolbar;
            var createButton = new Button({
                label: "Save",
                iconClass: "dijitEditorIcon dijitEditorIconSave",
                onClick: save
            });
            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete"
            });
            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel"
            });
            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
            toolbar.addChild(closeButton);
            toolbar.startup();

            var form = domConstruct.create('form', {
                method: 'post',
                enctype: 'multipart/form-data',
                class: 'Uploader'
            }, this.uploader);

            var up = new Uploader({
                label: 'Pick photo',
                multiple: true,
                url: '/ecm/rest/employees/'+ someId+'/photo',
                name: "file",
                onComplete:  lang.hitch(this, function(file) {
                    domAttr.set(this.avatar, "src", "data:image/png;base64, " + file.image);
                })
            }).placeAt(form);

            var list = new FileList({
                uploader: up
            }).placeAt(form);

            var btn = new Button({
                label: 'upload',
                onClick: function() {
                    up.upload();
                }
            }).placeAt(form);


            btn.startup();
            up.startup();
            list.startup();

            // var uploadButton = this.uploadButton;
            function uploadImage() {
                var currentPane = dijit.byId("personPane_"+this.model.id);
                var file = currentPane.uploader.getFileList()[0];
                xhr("/ecm/rest/employees/" + id + "/photo", {
                    handleAs: "json",
                    method: "post",
                    data: file,
                    headers: { "Content-Type": "multipart/form-data", "Accept": "application/json" }
                }).then(function (data) {
                    domAttr.set(avatar, "src", "data:image/png;base64, " + data.image);
                }, function (err) {
                    // Handle the error condition
                }, function (evt) {
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
            }
            // uploadButton.on('click', uploadImage);


            var avatar = this.avatar;
            loadPhoto(this.model.id, avatar);

            function loadPhoto(id, avatarNode) {
                xhr("/ecm/rest/employees/" + id + "/photo", {
                    handleAs: "json",
                    method: "get"
                }).then(function (data) {
                    domAttr.set(avatar, "src", "data:image/png;base64, " + data.image);
                }, function (err) {
                    // Handle the error condition
                }, function (evt) {
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
            }

            function save() {
                var currentPane = dijit.getEnclosingWidget(this.domNode.parentNode.parentNode);
                var form = currentPane.form.domNode;
                var formObject = domForm.toObject(form);
                var formJson = domForm.toJson(form);
                xhr("/ecm/rest/employees/"+formObject.id, {
                    handleAs: "json",
                    data: formJson,
                    method: "put",
                    headers: { "Content-Type": "application/json", "Accept": "application/json" }
                }).then(function(data){
                    // Do something with the handled data
                }, function(err){
                    // Handle the error condition
                }, function(evt){
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
                // dijit.getEnclosingWidget(this.domNode.parentNode.parentNode).form.submit();
            }
        }
        //
        //     query(".toChange").forEach(function(node){
        //         var nodeToChange = dom.byId(node.id);
        //         domAttr.set(nodeToChange, "id", node.id+model.id);
        //     });
        //
        //


        ,
        baseClass: "formsWidget",

        postCreate: function () {
            var a = 1;
        }
        //     switch(this.alertType) // обращение к переменой в теле виджета.
        //     {
        //         case “informing”: {
        //             this.Nodetitle.innerHTML = “Інформування”; // переменная, которая     подтягивается с шаблона виджета.
        //             this.Nodemessageimage.src = require.toUrl(“./image/informing.png”);
        //             domStyle.set(this._startupWidgets[1].domNode, “display”, “none”); /* this._startupWidgets[1] this is button cancel*/
        //             break;
        //         }
        //         case “error”: {
        //             this.Nodetitle.innerHTML = “Помилка”;
        //             this.Nodemessageimage.src = require.toUrl(“./image/error.png”);
        //             domStyle.set(this._startupWidgets[1].domNode, “display”, “none”); /* this._startupWidgets[1] this is button cancel*/
        //             break;
        //         }
        //
        //         case “success”: {
        //             this.Nodetitle.innerHTML = “Успішно”;
        //             this.Nodemessageimage.src = require.toUrl(“./image/success.png”);
        //             domStyle.set(this._startupWidgets[1].domNode, “display”, “none”); /* this._startupWidgets[1] this is button cancel*/
        //             break;
        //         }
        //         case “question”: {
        //             this.Nodetitle.innerHTML = “Видійсно хочете виконати данну операцію?”;
        //             this.Nodemessageimage.src = require.toUrl(“./image/question.png”);
        //             break;
        //         }
        //         case “warning”: {
        //             this.Nodetitle.innerHTML = “Попередження”;
        //             this.Nodemessageimage.src = require.toUrl(“./image/warning.png”);
        //             domStyle.set(this._startupWidgets[1].domNode, “display”, “none”); /* this._startupWidgets[1] this is button cancel*/
        //             break;
        //         }
        //         default: break;
        //
        //     }
    });
});