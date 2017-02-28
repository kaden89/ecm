define([
    "dojo/_base/declare",
    "dijit/Tree",
    "dojo/dom",
    "dojo/dom-style",
    "dojo/on",
    "dojo/_base/lang",
    "dojo/data/ObjectStore",
    "dijit/registry",
    "dijit/tree/ForestStoreModel",
    "dijit/layout/ContentPane"
], function (declare,
             Tree,
             dom,
             domStyle,
             on,
             lang,
             ObjectStore,
             registry,
             ForestStoreModel,
             ContentPane) {
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