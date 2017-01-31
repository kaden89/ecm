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
    "dijit/ConfirmDialog",
    "dijit/Dialog",
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
    "dojox/mvc/Output",
    "/ecm/resources/js/util.js"


], function (declare, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Stateful, dom, Toolbar, Button, domForm, domAttr, registry, request, xhr,
            domConstruct, Uploader, FileList, IFrame, Form, lang, dojo, locale, ConfirmDialog, Dialog) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        isNew: false,
        store: null,
        script: null,
        constructor: function (args, store) {
            this.templateString = args.template;
            this.model = new Stateful(args.entity);
            this.store = store;
            this.script = args.script;
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
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick: lang.hitch(this, remove)
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

            function remove() {
                var id = this.model.id;
                var store = this.store;
                deleteDialog = new ConfirmDialog({
                    title: "Delete",
                    content: "Are you sure that you want to delete "+this.model.name+"?",
                    style: "width: 300px",
                    onCancel: function () {
                        return;
                    },
                    onExecute: function () {
                        store.remove(id).then(success, error);
                    }

                });
                deleteDialog.show();

                function success() {
                    var tabPane = registry.byId("TabContainer");
                    pane = registry.byId("personPane_"+id);
                    tabPane.removeChild(pane);
                    tabPane.selectChild(registry.byId("WelcomPane"));
                    pane.destroy();
                    updateTree();
                }

                function error(err) {
                    myDialog = new Dialog({
                        title: "Error!",
                        content: err.responseText,
                        style: "width: 300px"
                    });
                    console.log("error");
                    myDialog.show();
                }

            }

            function save() {
                var form = this.form;
                if (!form.validate()) return;
                //clone for change birthday to ISO type without time
                var data =  lang.clone(form.value);
                data.birthday = locale.format(data.birthday, {datePattern: "yyyy-MM-dd", selector: "date"});
                var formJson = dojo.toJson(data);
                var method = "put";
                var id;
                //create new user
                if (this.model.id==undefined){
                    this.store.add(data).then(function(data){
                        this.form.set('value', data);
                        var pane = registry.byId("newPersonPane_");
                        pane.set("title", data.firstname+" "+data.surname+" "+data.patronymic);
                        // pane.set("id", "personPane_"+data.id);
                        // this.isNew = false;
                        updateTree();
                    }.bind(this), function(err){
                        // Handle the error condition
                    }, function(evt){
                        // Handle a progress event from the request if the
                        // browser supports XHR2
                    });
                }
                else {
                    this.store.put(data).then(function(data){
                        this.form.set('value', data);
                        var pane = registry.byId("personPane_"+data.id);
                        pane.set("title", data.firstname+" "+data.surname+" "+data.patronymic);
                        updateTree();

                    }.bind(this), function(err){
                        // Handle the error condition
                    }, function(evt){
                        // Handle a progress event from the request if the
                        // browser supports XHR2
                    });
                }


            }
            function updateTree() {
                tree = registry.byId('personTree');
                tree.dndController.selectNone();
                tree.model.store.clearOnClose = true;
                tree._itemNodesMap = {};
                tree.rootNode.state = "UNCHECKED";
                tree.model.childrenCache = null;

                // Destroy the widget
                tree.rootNode.destroyRecursive();

                // Recreate the model, (with the model again)
                tree.model.constructor(dijit.byId("personTree").model);

                // Rebuild the tree
                tree.postMixInProperties();
                tree._load();
            }

            eval(this.script);
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