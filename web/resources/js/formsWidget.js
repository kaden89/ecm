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
    'dojo/_base/json',
    "dojo/date/locale",
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
            domConstruct, Uploader, FileList, IFrame, Form, lang, dojo, locale) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        isNew: false,
        constructor: function (args) {
            this.templateString = args.template;
            this.model = new Stateful(args.entity);
            if (args.entity.id == undefined) this.isNew = true;
        }
        ,
        startup: function () {
            this.inherited(arguments);

            var toolbar = this.toolbar;
            var createButton = new Button({
                label: "Save",
                iconClass: "dijitEditorIcon dijitEditorIconSave",
                onClick: lang.hitch(this, save)
            });
            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete"
            });
            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick:  lang.hitch(this, close)
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
                label: 'Select photo',
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
                label: 'Upload',
                onClick: function() {
                    up.upload();
                }
            }).placeAt(form);


            btn.startup();
            up.startup();
            list.startup();


            var avatar = this.avatar;
            loadPhoto(this.model.id, avatar);

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

            function close() {
                var tabPane = registry.byId("TabContainer");
                var pane;
                if (this.isNew){
                    pane = registry.byId("newPersonPane_");
                }
                else {
                    pane = registry.byId("personPane_"+this.model.id);
                }
                tabPane.removeChild(pane);
                tabPane.selectChild(registry.byId("WelcomPane"));
                pane.destroy();
            }

            function save() {
                var form = this.form;
                //clone for change birthday to ISO type without time
                var data =  lang.clone(form.value);
                data.birthday = locale.format(data.birthday, {datePattern: "yyyy-MM-dd", selector: "date"});
                var formJson = dojo.toJson(data);
                var method = "put";
                var id;
                //create new user
                if (this.model.id==undefined){
                    method = "post";
                    id = "";
                }
                else {
                    id = this.model.id;
                }
                xhr("/ecm/rest/employees/"+id, {
                    handleAs: "json",
                    data: formJson,
                    method: method,
                    headers: { "Content-Type": "application/json", "Accept": "application/json" }
                }).then(function(data){
                    this.form.set('value', data);
                    // if (this.isNew){
                    //     var pane = registry.byId("newPersonPane_");
                    // }
                }.bind(this), function(err){
                    // Handle the error condition
                }, function(evt){
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
                // dijit.getEnclosingWidget(this.domNode.parentNode.parentNode).form.submit();
            }
        }

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