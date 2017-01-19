

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

    var toolbar = new Toolbar({}, "toolbar");
    var createButton = new Button({
        label:"Save",
        iconClass:"dijitEditorIcon dijitEditorIconSave",
        onClick:createNewTab
    });
    var deleteButton = new Button({
        label:"Delete",
        iconClass:"dijitEditorIcon dijitEditorIconDelete"
    });
    var closeButton = new Button({
        label:"Close",
        iconClass:"dijitEditorIcon dijitEditorIconCancel",
        onClick:createNewTab
    });
    toolbar.addChild(createButton);
    toolbar.addChild(deleteButton);
    toolbar.addChild(closeButton);

    toolbar.startup();
});