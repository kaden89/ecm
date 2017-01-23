/**
 * Created by dkarachurin on 23.01.2017.
 */
define([
    "dojo/_base/declare",
    "dijit/_Widget",
    "dijit/_TemplatedMixin",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dojo/Stateful",
    "dojo/_base/declare"
], function(declare, _Widget, _TemplatedMixin, Toolbar, Button, Stateful, declare){
    return declare([_Widget, _TemplatedMixin], {

        _setModel: function(model){

            this._set("model", model);
        },
        _setDiv: function(div){

            this._set("div", div);
        },
        startup: function(){
            var toolbar = new Toolbar({}, div);
            array.forEach(model, function(object) {
                var button = new Button({
                    label: object.label,
                    iconClass: "dijitEditorIcon dijitEditorIcon" + label,
                    onClick: object.function
                }).startup();
                toolbar.addChild(button);
            });
        },
        baseClass: "toolbarWidget"
    });
});