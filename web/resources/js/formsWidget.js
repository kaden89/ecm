/**
 * Created by dkarachurin on 19.01.2017.
 */
define([
    "dojo/_base/declare",
    "dijit/_Widget",
    "dijit/_TemplatedMixin",
    "dojo/text!/ecm/resources/html/person.html",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dojo/Stateful",
    "dojo/_base/declare"
], function(declare, _Widget, _TemplatedMixin, template, Toolbar, Button, Stateful, declare){
    return declare([_Widget, _TemplatedMixin], {
        templateString: template,
        _setModel: function(model){

            this._set("model", new Stateful(model));
        },
        _setSurnameAttr: { node: "surnameNode", type: "innerHTML" },
        _setNameAttr: function(val){
            this.nameNode.innerHTML = val;
            this._set("name", val);
        },
        startup: function(){
            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                label:"Save",
                iconClass:"dijitEditorIcon dijitEditorIconSave"
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
        },
        baseClass: "formsWidget"
        // postCreate: function() {
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