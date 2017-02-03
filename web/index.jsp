<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <script type='text/javascript' src='//ajax.googleapis.com/ajax/libs/dojo/1.10.1/dojo/dojo.js'
            data-dojo-config="async: true, parseOnLoad: true"></script>
    <link rel="stylesheet" type="text/css"
          href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.1/dijit/themes/claro/claro.css">
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.rawgit.com/oria/gridx/1.3/resources/claro/Gridx.css">

    <link rel="stylesheet" type="text/css"
          href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.1/dijit/themes/claro/document.css">


    <script type='text/javascript'>
        var grid;
        var store;

        require({
            packages: [
                {
                    name: 'gridx',
                    location: '//cdn.rawgit.com/oria/gridx/1.3'
                }
            ]
        }, ["dojo/_base/declare",
            "dijit/layout/TabContainer",
            "dijit/layout/ContentPane",
            'gridx/Grid',
            'gridx/modules/Dod',
            'gridx/core/model/cache/Async',
            'dojo/Deferred',
            'dojo/store/util/QueryResults',
            "dojo/store/Memory",
            "gridx/modules/SingleSort",
            "dojo/store/JsonRest",
            "gridx/modules/Bar",
            "dijit/Toolbar",
            "dijit/form/Button",
            "gridx/modules/RowHeader",
            "gridx/modules/select/Row",
            "gridx/modules/IndirectSelect",
            "dijit/Dialog",
            "dijit/registry",
            "dojo/query",
            "dojo/dom",
            "dojo/parser",
            "dojo/request/xhr",
            "dojox/image/Lightbox",
            "dojo/dom",
            "dojo/store/JsonRest",
            "dijit/Tree",
            "dijit/tree/ObjectStoreModel",
            "dojo/Stateful",
            "dojox/mvc/at",
            "/ecm/resources/js/formsWidget.js",
            "dojo/store/Observable",
            "dijit/ConfirmDialog",
            /*'dojo/store/Memory',*/
            "dojo/domReady!"], function (declare, TabContainer, ContentPane, GridX, Dod, Cache, Deferred, QueryResults, Memory, SingleSort, JsonRest, Bar, Toolbar, Button,
                                         RowHeader, Row, IndirectSelect, Dialog, Registry, query, Dom, parser, xhr, Lightbox, dom, JsonRest,
                                         Tree, ObjectStoreModel, Stateful, at, formsWidget, Observable, ConfirmDialog) {
            var restURL = 'http://localhost:8080/ecm/rest/employees/';
            var personTreeStore = new JsonRest({
                idProperty: 'id',
                target: restURL+"personTree",
                mayHaveChildren: function(object){
                    return "haveChildren" in object;
                },
                getChildren: function(object){
                    return this.get(object.id).then(function(fullObject){
                        return fullObject.children;
                    });
                }
            });
            personTreeStore = new Observable(personTreeStore);

            var personStore = new JsonRest({
                idProperty: 'id',
                target: restURL,
                headers: {
                    'Content-Type': "application/json; charset=UTF-8"},
                getChildren: function(object){
                    return object;
                }
            });
            personStore = new Observable(personStore);


            //create structure......
            var columns = [
                {id: 'id', field: 'id', name: 'id', width: '5%'},
                {id: 'firstname', field: 'firstname', name: 'Firstname', width: '19%'},
                {id: 'surname', field: 'surname', name: 'Surname', width: '19%'},
                {id: 'patronymic', field: 'patronymic', name: 'Patronymic', width: '19%'},
                {id: 'position', field: 'position', name: 'Position', width: '19%'},
                {id: 'birthday', field: 'birthday', name: 'Birthday', width: '19%'}
            ];

            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                label: "Create",
                iconClass: "dijitEditorIcon dijitEditorIconPaste",
                onClick: createNewTab
            });
            var editButton = new Button({
                label: "Edit",
                iconClass: "dijitIcon dijitIconEdit",
                onClick: edit
            });
            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick: deleteSelected
            });
            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
//            toolbar.addChild(editButton);
            toolbar.startup();

            //Create grid widget.
            var grid = GridX({
                id: 'grid',
                cacheClass: Cache,
                store: personStore,
                structure: columns,
                selectRowMultiple: false,
                barTop: [
                    toolbar
                ],
                modules: [
                    //Declare sort module in the "modules" parameter.
                    {
                        moduleClass: SingleSort,
                        //Declare initialOrder as module parameter:
                        initialOrder: {colId: 'firstname', descending: true}
                    },
                    Bar,
                    RowHeader,
                    Row,
                    IndirectSelect
                ]
            });
            grid.placeAt('gridContainer');

            grid.connect(grid, "onRowDblClick", openTab);
            grid.startup();

            setupTrees();


            function deleteSelected() {
                        var items = grid.select.row.getSelected();
                        if (items.length) {
                            dojo.forEach(items, function (selectedItem) {
                                if (selectedItem !== null) {
                                    deleteDialog = new ConfirmDialog({
                                        title: "Delete",
                                        content: "Are you sure that you want to delete person with id "+selectedItem+"?",
                                        style: "width: 300px",
                                        onCancel: function () {
                                            return;
                                        },
                                        onExecute: function () {
                                            grid.store.remove(selectedItem).then(success, error);
                                        }

                                    });
                                    deleteDialog.show();
                                }
                            });
                        }
            }

            function success() {
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
                        var widget = new formsWidget({model: data}, personStore);
                        model = widget.get("model");
                        var tabContainer = Registry.byId("TabContainer");
                        var pane = new ContentPane({
                            title: "Person", content: widget, closable: true, onClose: function () {
                                return confirm("Do you really want to Close this?");
                            }
                        });
                        pane.set("id", data.id);
                        tabContainer.addChild(pane);
                        tabContainer.selectChild(pane);
//                        parser.parse(Dom.byId("personBorderContainer"+model.id));
                    });

                }
            }

            var incomingsStore = new JsonRest({
                idProperty: 'id',
                headers: {
                    'Content-Type': "application/json; charset=UTF-8"},
                target: 'http://localhost:8080/ecm/rest/documents/incomings',
                getChildren: function(object){
                    return object;
                }
            });
            incomingsStore = new Observable(incomingsStore);

            var outgoingsStore = new JsonRest({
                idProperty: 'id',
                target: 'http://localhost:8080/ecm/rest/documents/outgoings',
                getChildren: function(object){
                    return object;
                }
            });
            outgoingsStore = new Observable(outgoingsStore);

            function createNewTab() {
                xhr("/ecm/rest/widgets/person/", {
                    handleAs: "json"
                }).then(function(data){
                    var widget  = new formsWidget(data, personStore);
                    var tabContainer = Registry.byId("TabContainer");
                    var id = data.entity.id;
                    var pane = Registry.byId("newPane_");
                    if (pane != undefined){
                        tabContainer.selectChild(pane);
                        return;
                    }
                    var pane = new ContentPane({
                        title: "New person (Unsaved)", closable: true, onClose: function () {
                            return confirm("Do you really want to Close this?");
                        }
                    });
                    pane.set("id", "newPane_");
                    tabContainer.addChild(pane);
                    tabContainer.selectChild(pane);
                    pane.setContent(widget);
                    Registry.add(pane);

                }, function(err){
                    console.log(err);
                }, function(evt){
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
            }

            function myButtonHandler() {
                console.log('Clicked button');
            }

            function openTab(item) {
                var id;
                //Check if we are from Grid
                if(item.target){
                    id = item.rowId;
                }
                else{
                    id = item.id
                }
                //if the tab is already open, switch on it
                var tabContainer = Registry.byId("TabContainer");
                var pane = Registry.byId("pane_"+id);
                if (pane != undefined){
                    tabContainer.selectChild(pane);
                    return;
                }

                xhr("/ecm/rest/widgets/person/"+id, {
                    handleAs: "json"
                }).then(function(data){
                    var widget  = new formsWidget(data, personStore, Registry.byId('personTree'));
                    var id = data.entity.id;
                    var pane = new ContentPane({
                        title: data.entity.fullname, closable: true
                    });
                    pane.set("id", "pane_"+id);
                    tabContainer.addChild(pane);
                    tabContainer.selectChild(pane);
                    pane.setContent(widget);
                    Registry.add(pane);

                }, function(err){
                    console.log(err);
                }, function(evt){
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });

            }


            function setupTrees() {


                var restURL = 'http://localhost:8080/ecm/rest/employees';
                var personStore = new JsonRest({
                    idAttribute:"surname",
                    target: restURL,
                    getChildren: function(object){
                        console.dir(object);
//                        return this.query({parent: object.id});
                        return object;
                    }
                });
                personStore = new Observable(personStore);


                model = new ObjectStoreModel({
                    store: personTreeStore,
                    getRoot: function(onItem){
                        this.store.get("").then(onItem);
                    },
                    mayHaveChildren: function(object){
                        return "children" in object;
                    },
                    getLabel : function(item) {
                        if (item.fullname != undefined){
                            return item.fullname;
                        } else {
                            return item.name;
                        }
                    }

                });

                tree = new Tree({
                    model: model,
                    onDblClick: openTab
                }, "personTree"); // make sure you have a target HTML element with this id
                tree.startup();



                var documentsStore = new JsonRest({
                    target: "/ecm/rest/documents/tree",
                    idAttribute:"id",
                    mayHaveChildren: function(object){
                        return "haveChildren" in object;
                    },
                    getChildren: function(object){
                        return this.get(object.id).then(function(fullObject){
                            return fullObject.children;
                        });
                    }

                });

                documentsStore = new Observable(documentsStore);

                documentsModel = new ObjectStoreModel({
                    store: documentsStore,
                    getRoot: function(onItem){
                        this.store.get("").then(onItem);
                    },
                    mayHaveChildren: function(object){
                        return "haveChildren" in object;
                    }
                });

                documentsTree = new Tree({
                    model: documentsModel,
                    onDblClick: openDocTab
//                    autoExpand: true
                }, "documentsTree");
                documentsTree.startup();

                function openDocTab(item, node) {
                    var id, store;
                    var url = "/ecm/rest/widgets/";
                    //Check if we are from Grid
                    if(item.target){
                        id = item.rowId;
                    }
                    else{
                        if (node.getParent().label == "Incomings"){
                            id = item.id;
                            url = url +"incoming/";
                            store = incomingsStore;
                        }
                        else if (node.getParent().label == "Outgoings") {
                            id = item.id;
                            url = url +"outgoing/";
                            store = outgoingsStore;
                        }
                        else {
                            return;
                        }
                    }
                    //if the tab is already open, switch on it
                    var tabContainer = Registry.byId("TabContainer");
                    var pane = Registry.byId("pane_"+id);
                    if (pane != undefined){
                        tabContainer.selectChild(pane);
                        return;
                    }

                    xhr(url+id, {
                        handleAs: "json"
                    }).then(function(data){
                        var widget  = new formsWidget(data, store, Registry.byId('documentsTree'));
                        var id = data.entity.id;
                        var pane = new ContentPane({
                            title: data.entity.fullname, closable: true
                        });
                        pane.set("id", "pane_"+id);
                        tabContainer.addChild(pane);
                        tabContainer.selectChild(pane);
                        pane.setContent(widget);
                        Registry.add(pane);

                    }, function(err){
                        console.log(err);
                    }, function(evt){
                        // Handle a progress event from the request if the
                        // browser supports XHR2
                    });

                }

            }
        });
    </script>


</head>


<body class="claro">
<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'headline'"
     style="width: 100%; height:100%;">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'left', splitter: true" style="width: 15%">

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 35%">
            <div id="personTree"></div>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" style="height: 60%">
            <div id="documentsTree"></div>
        </div>
    </div>
    <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true" id="TabContainer">
        <div data-dojo-type="dijit/layout/ContentPane" title="Welcom" id="WelcomPane">
            <div id='gridContainer'></div>
        </div>
    </div>
</div>

</body>
</html>
