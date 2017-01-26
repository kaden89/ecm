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
    "dijit/form/Form",
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


], function(declare, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Stateful, dom, Toolbar, Button, domForm, domAttr, registry, request){
    return declare([ _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        model: null,
        // _setModel: function(model){
        //
        //     this._set("model", new Stateful(model));
        // },
        constructor: function(args) {
            this.templateString = args.template;
            this.model = new Stateful(args.entity);
        }
        ,
        startup: function(){
            this.inherited(arguments);

            // var id = this.model.id;
            // var form = registry.byId("personForm");
            // form.id = form.id+id;


            // var avatar = dom.byId("avatar");
            // domAttr.set(avatar,"src","data:image/png;base64, "+this.model.photo);



            var toolbar = this.toolbar;
            var createButton = new Button({
                label:"Save",
                iconClass:"dijitEditorIcon dijitEditorIconSave",
                onClick: save
            });
            var deleteButton = new Button({
                label:"Delete",
                iconClass:"dijitEditorIcon dijitEditorIconDelete"
            });
            var closeButton = new Button({
                label:"Close",
                iconClass:"dijitEditorIcon dijitEditorIconCancel"
            });
            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
            toolbar.addChild(closeButton);

            toolbar.startup();
            //
                var avatar = this.avatar;
                domAttr.set(avatar,"src", "data:image/png;base64, "+ this.model.photo);
            //
            function save() {
               dijit.getEnclosingWidget(this.domNode.parentNode.parentNode).form.submit();
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

        postCreate: function() {
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