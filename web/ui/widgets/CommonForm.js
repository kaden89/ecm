/**
 * Created by dkarachurin on 19.01.2017.
 */
define([
    "dojo/_base/declare",
    "dojo/topic",
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
    "dojo/text!/ecm/ui/templates/Person.html",
    "dojo/text!/ecm/ui/templates/Incoming.html",
    "dojo/text!/ecm/ui/templates/Outgoing.html",
    "dojo/text!/ecm/ui/templates/Task.html"

], function (declare, topic, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Stateful, dom, Toolbar, Button, domForm, domAttr, registry, request, xhr,
             domConstruct, Uploader, FileList, IFrame, Form, lang,  personTemplate, incomingTemplate, outgoingTemplate, taskTemplate) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        templateString: null,
        isNew: false,
        type: null,
        constructor: function (params) {
            lang.mixin(this, params);
            this.setTemplateByModelType(this.type);
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
                    topic.publish("commonForm/Save", this.model);
                })
            });
            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick:  lang.hitch(this, function () {
                    topic.publish("commonForm/Delete", this.model);
                })
            });
            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick:  lang.hitch(this, function () {
                    topic.publish("commonForm/Close", this.model);
                })
            });

            if (this.isNew) {
                deleteButton.set('disabled', true);
            } else {
                deleteButton.set('disabled', false);
            }
            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
            toolbar.addChild(closeButton);
        },
        setTemplateByModelType: function (type) {
        switch (type) {
            case 'task':
                this.templateString = taskTemplate;
                break;
            case 'incoming':
                this.templateString = incomingTemplate;
                break;
            case 'outgoing':
                this.templateString = outgoingTemplate;
                break;
            case 'person':
                this.templateString = personTemplate;
                break;
        }
    }
    });
});