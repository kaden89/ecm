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
    "dijit/Editor",
    "dijit/form/Select",
    "/ecm/resources/js/myJsonRest.js",
    "dijit/form/FilteringSelect",
    "dojox/mvc/at",
    "dojo/store/Memory",
    "dijit/form/CheckBox",
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
             domConstruct, Uploader, FileList, IFrame, Form, lang, dojo, locale, ConfirmDialog, Dialog, Editor, Select, JsonRest, FilteringSelect,
             at, Memory, CheckBox) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        store: null,
        script: null,
        tree: null,
        deletable: true,
        constructor: function (response, params) {
            this.templateString = response.template;
            this.model = new Stateful(response.entity);
            this.store = params.store;
            this.script = response.script;
            this.tree = params.tree;
            if (params.deletable != undefined) {
                this.deletable = params.deletable;
            }
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
                onClick: lang.hitch(this, close)
            });
            toolbar.addChild(createButton);
            if (this.deletable) {
                toolbar.addChild(deleteButton);
            }
            toolbar.addChild(closeButton);
            toolbar.startup();


            function close() {
                var tabPane = registry.byId("TabContainer");
                var pane;
                if (this.model.id == undefined) {
                    pane = registry.byId("newPane_" + this.model.restUrl);
                }
                else {
                    pane = registry.byId("pane_" + this.model.id);
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
                    content: "Are you sure that you want to delete " + this.model.fullname + "?",
                    style: "width: 300px",
                    onCancel: function () {
                        return;
                    },
                    onExecute: function () {
                        store.remove(id).then(success.bind(this), error);
                    }.bind(this)

                });
                deleteDialog.show();

                function success() {
                    var tabPane = registry.byId("TabContainer");
                    pane = registry.byId("pane_" + id);
                    tabPane.removeChild(pane);
                    tabPane.selectChild(registry.byId("WelcomPane"));
                    pane.destroy();
                    updateTree.call(this, this.tree);
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
                if (!this.form.validate()) return;
                //create new user
                if (this.model.id == undefined) {
                    this.store.add(this.model).then(function (data) {
                        for (var k in data) {
                            this.model.set(k, data[k]);
                        }
                        //Dojo can't correctly handle id changing, so close old pane and create new one
                        var pane = registry.byId("newPane_" + data.restUrl);
                        var tabContainer = registry.byId("TabContainer");
                        tabContainer.removeChild(pane);
                        pane.set("title", data.fullname);
                        pane.set("id", "pane_" + data.id);
                        registry.remove("newPane_" + data.restUrl);
                        registry.add(pane);
                        tabContainer.addChild(pane);
                        tabContainer.selectChild(pane);
                        toolbar.addChild(deleteButton, 1);
                        this.uploader.set('disabled', false);
                        this.uploader.set('url', '/ecm/rest/employees/'+ data.id+'/photo');
                        this.button.set('disabled', false);
                        updateTree.call(this, this.tree);
                    }.bind(this));
                }
                else {
                    this.store.put(this.model).then(function (data) {
                        var pane = registry.byId("pane_" + data.id);
                        pane.set("title", data.fullname);
                        updateTree.call(this, this.tree);
                    }.bind(this));
                }
            }

           eval(this.script)

        },
        baseClass: "formsWidget",
        postCreate: function () {
            var a = 1;
        }
    });
});