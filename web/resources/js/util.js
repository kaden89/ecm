/**
 * Created by dkarachurin on 30.01.2017.
 */
require(["dojo/_base/declare",
    "dijit/layout/TabContainer",
    "dijit/layout/ContentPane",
    'dojo/Deferred',
    'dojo/store/util/QueryResults',
    "dojo/store/Memory",
    "dojo/store/JsonRest",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dijit/Dialog",
    "dijit/registry",
    "dojo/query",
    "dojo/dom",
    "dojo/domReady!"], function(declare, TabContainer, ContentPane, Deferred, QueryResults,Memory, JsonRest, Toolbar, Button, Dialog, Registry,query, Dom) {

        function updateTree() {
            tree = Registry.byId('personTree');
            tree.dndController.selectNone();
            tree.model.store.clearOnClose = true;
            tree._itemNodesMap = {};
            tree.rootNode.state = "UNCHECKED";
            tree.model.childrenCache = null;

            // Destroy the widget
            tree.rootNode.destroyRecursive();

            // Recreate the model, (with the model again)
            tree.model.constructor(dijit.byId("personTree").model);

            // Rebuild the tree
            tree.postMixInProperties();
            tree._load();
        }
});