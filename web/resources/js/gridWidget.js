/**
 * Created by dkarachurin on 03.02.2017.
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
    "dojo/store/JsonRest",
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
        isNew: false,
        store: null,
        script: null,
        tree: null,
        constructor: function (args, store, tree) {
            this.templateString = args.template;
            this.model = new Stateful(args.entity);
            this.store = store;
            this.script = args.script;
            this.tree = tree;
            if (args.entity.id == undefined) this.isNew = true;
        }
        ,
        startup: function () {
        }
    });
});