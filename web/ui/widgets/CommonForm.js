/**
 * Created by dkarachurin on 19.01.2017.
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
    "dojox/mvc/at",
    "dojox/mvc/Output",
    "dojox/form/Uploader",
    "dojox/form/uploader/FileList",
    "dojox/form/uploader/plugins/IFrame",
    "dijit/form/Form",
    "dojo/_base/lang"

], function (declare, topic, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Editor, Select, Toolbar, Button, ValidationTextBox,
             DateTextBox, FilteringSelect, CheckBox, at, Output, Uploader, FileList, IFrame, Form, lang) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        templateString: null,
        isNew: null,
        constructor: function (params) {
            lang.mixin(this, params);
        }
        ,
        startup: function () {
            this.inherited(arguments);

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
                    topic.publish("commonForm/Close", this.isNew ? this.model.declaredClass : this.model.id);
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
        updateAfterSave: function (data) {
            this.isNew = false;
            this.model = new this.model.constructor(data);
            this.deleteButton.set('disabled', false);
        }
    });
});