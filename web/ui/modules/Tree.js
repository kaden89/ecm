/**
 * Наследованный от Tree модуль. Для добавления функции обновления дерева после изменения данных в  Store,
 * т.к. Observable JsonRest и Tree вместе не работают.
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dijit/Tree",
    "dojo/_base/lang"
], function (declare,
             Tree,
             lang) {
    return declare([Tree], {
        showRoot: true,
        itemTemplate: null,
        reloadStoreOnRefresh: true,
        constructor: function (params) {
            lang.mixin(this, params);
        },
        update: function () {
            this.dndController.selectNone();
            this.model.store.clearOnClose = true;
            this._itemNodesMap = {};
            this.rootNode.state = "UNCHECKED";
            this.model.childrenCache = null;
            this.rootNode.destroyRecursive();
            this.model.constructor(this.model);
            this.postMixInProperties();
            this._load();
        },
        startup: function () {
            this.inherited(arguments);
        }
    });
});