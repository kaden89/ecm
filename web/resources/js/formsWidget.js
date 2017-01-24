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
    "dojo/_base/declare",
    "dojo/request",
    "dojo/dom-form",
    "dijit/registry",
    "dojox/image/Lightbox",
    "dojo/dom",
    "dojo/dom-attr",
    "dojo/query",

], function(declare, _Widget, _TemplatedMixin, template, Toolbar, Button, Stateful, declare, request, domForm, registry, Lightbox, dom, domAttr, query){
    return declare([_Widget, _TemplatedMixin], {
        templateString: template,
        _setModel: function(model){

            this._set("model", new Stateful(model));
        },
        startup: function(){

            query(".toChange").forEach(function(node){
                var nodeToChange = dom.byId(node.id);
                domAttr.set(nodeToChange, "id", node.id+model.id);
            });


            var toolbarNode = dom.byId("toolbar"+model.id);
            var toolbar = new Toolbar({}, toolbarNode);
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

            var node = dom.byId("avatar"+model.id);
            // domAttr.set(node,"src", "data:image/png;base64, "+model.photo);

            function save() {
                var formObj = domForm.toObject("personForm"+model.id);
                var widget = registry.byId("photo"+model.id);
                var v = dom.byId("photo"+model.id);
                var photo =  domAttr.get(node,"src").replace("data:image/png;base64, ", "");
                // var photo = widget.getFileList()[0];

                formObj.photo = photo;
                request.put("http://localhost:8080/ecm/rest/employees/"+formObj.id, {
                    data: formObj,
                    headers: {
                        "X-Something": "A value"
                    }
                }).then(function(text){
                    console.log("The server returned: ", text);
                });
            }
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