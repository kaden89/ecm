

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

    function deleteSelected() {
        // Get all selected items from the Grid:
        var items = grid.select.row.getSelected();
        if(items.length){
            dojo.forEach(items, function(selectedItem){
                if(selectedItem !== null){
                    grid.store.remove(selectedItem).then(success, error);
                    grid.model.clearCache();
                    grid.body.refresh();
                }
            });
//                    var args = {onError: function() {alert('error!');}};
//                    grid.store.add(args);

        }
    }

    function success() {
        console.log("succes");
    }

    function error(err) {
        myDialog = new Dialog({
            title: "Error!",
            content: err.responseText,
            style: "width: 300px"
        });
        console.log("error");
        myDialog.show();
    }

    function edit() {

        var id;
        var items = grid.select.row.getSelected();
        if (items.length) {
            dojo.forEach(items, function (selectedItem) {
                if (selectedItem !== null) {
                    id = selectedItem;
                }
            });
            xhr(restURL + "/" + id, {
                handleAs: "json"
            }).then(function (data) {
                var widget = new formsWidget({model: data});
                model = widget.get("model");
                var tabContainer = Registry.byId("TabContainer");
                var pane = new ContentPane({
                    title: "Person", content: widget, closable: true, onClose: function () {
                        return confirm("Do you really want to Close this?");
                    }
                });
                tabContainer.addChild(pane);
                tabContainer.selectChild(pane);
                parser.parse(Dom.byId("personDiv"));
            });

        }
    }

    function createNewTab() {
        var widget = new formsWidget({ model:{firstName:"firstname", surname:"surname", patronymic: "patronymic"}});
        model = widget.get("model");
        var tabContainer = Registry.byId("TabContainer");
        var pane = new ContentPane({ title:"Person",  content: widget, closable: true, onClose: function(){
            return confirm("Do you really want to Close this?");
        }});
        tabContainer.addChild(pane);
        tabContainer.selectChild(pane);
        parser.parse(Dom.byId("personDiv"));
    }

});