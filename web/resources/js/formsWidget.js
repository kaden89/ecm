/**
 * Created by dkarachurin on 19.01.2017.
 */
define([
    "dojo/_base/declare",
    "dijit/_Widget",
    "dijit/_TemplatedMixin",
    "dojo/text!./resources/hetml/person.html"
], function(declare, _Widget, _TemplatedMixin, template){
    return declare([_Widget, _TemplatedMixin], {
        templateString: template,
        _setSurnameAttr: { node: "surnameNode", type: "innerHTML" },
        _setNameAttr: function(val){
            this.nameNode.innerHTML = val;
            this._set("name", val);
        }
    });
});