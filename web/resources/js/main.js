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
    "/ecm/resources/js/myJsonRest.js",
    "dijit/Tree",
    "dijit/tree/ObjectStoreModel",
    "dojo/Stateful",
    "dojox/mvc/at",
    "/ecm/resources/js/formsWidget.js",
    "dojo/store/Observable",
    "dijit/ConfirmDialog",
    "/ecm/resources/js/gridWidget.js",
    /*'dojo/store/Memory',*/
    "dojo/domReady!"], function (declare, TabContainer, ContentPane, GridX, Dod, Cache, Deferred, QueryResults, Memory, SingleSort, JsonRest, Bar, Toolbar, Button,
                                 RowHeader, Row, IndirectSelect, Dialog, Registry, query, Dom, parser, xhr, Lightbox, dom, JsonRest,
                                 Tree, ObjectStoreModel, Stateful, at, formsWidget, Observable, ConfirmDialog, gridWidget) {
    var restURL = 'http://localhost:8080/ecm/rest/';
    var personTreeStore = new JsonRest({
        idProperty: 'id',
        target: restURL + "employees/personTree",
        mayHaveChildren: function (object) {
            return "haveChildren" in object;
        },
        getChildren: function (object) {
            return this.get(object.id).then(function (fullObject) {
                return fullObject.children;
            });
        }
    });
    personTreeStore = new Observable(personTreeStore);

    var personStore = new JsonRest({
        idProperty: 'id',
        target: restURL+"employees/",
        headers: {
            'Content-Type': "application/json; charset=UTF-8"
        },
        getChildren: function (object) {
            return object;
        }
    });
    personStore = new Observable(personStore);


    var taskStore = new JsonRest({
        idProperty: 'id',
        target: restURL+"documents/tasks",
        getChildren: function (object) {
            return object;
        }
    });
    taskStore = new Observable(taskStore);

    var incomingStore = new JsonRest({
        idProperty: 'id',
        target: restURL+"documents/incomings",
        getChildren: function (object) {
            return object;
        }
    });
    incomingStore = new Observable(incomingStore);

    var outgoingStore = new JsonRest({
        idProperty: 'id',
        target: restURL+"documents/outgoings",
        getChildren: function (object) {
            return object;
        }
    });
    outgoingStore = new Observable(outgoingStore);

    setupTrees();


    function openTab(item) {
        var id;
        if (item.id == undefined){
            id = "new";
        }
        //Check if we are from Grid
        if (item.target) {
            id = item.rowId;
        }
        else {
            id = item.id
        }
        //if the tab is already open, switch on it
        var tabContainer = Registry.byId("TabContainer");
        var pane = Registry.byId("pane_" + id);
        if (pane != undefined) {
            tabContainer.selectChild(pane);
            return;
        }

        xhr("/ecm/rest/widgets/persons/" + id, {
            handleAs: "json"
        }).then(function (data) {
            var params = {store: personStore, tree: Registry.byId('personTree')};
            var widget = new formsWidget(data, params);
            var id = data.entity.id;
            var pane = new ContentPane({
                title: data.entity.fullname, closable: true
            });
            pane.set("id", "pane_" + id);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
            pane.setContent(widget);
            Registry.add(pane);

        }, function (err) {
            console.log(err);
        }, function (evt) {
            // Handle a progress event from the request if the
            // browser supports XHR2
        });

    }


    function setupTrees() {


        var restURL = 'http://localhost:8080/ecm/rest/employees';
        var personStore = new JsonRest({
            idAttribute: "surname",
            target: restURL,
            getChildren: function (object) {
                console.dir(object);
//                        return this.query({parent: object.id});
                return object;
            }
        });
        personStore = new Observable(personStore);


        model = new ObjectStoreModel({
            store: personTreeStore,
            getRoot: function (onItem) {
                this.store.get("").then(onItem);
            },
            mayHaveChildren: function (object) {
                return "children" in object;
            },
            getLabel: function (item) {
                if (item.fullname != undefined) {
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
            idAttribute: "id",
            mayHaveChildren: function (object) {
                return "haveChildren" in object;
            },
            getChildren: function (object) {
                return this.get(object.id).then(function (fullObject) {
                    return fullObject.children;
                });
            }

        });

        documentsStore = new Observable(documentsStore);

        documentsModel = new ObjectStoreModel({
            store: documentsStore,
            getRoot: function (onItem) {
                this.store.get("").then(onItem);
            },
            mayHaveChildren: function (object) {
                return "haveChildren" in object;
            }
        });

        documentsTree = new Tree({
            model: documentsModel,
            onDblClick: openDocTab
//                   autoExpand: true
        }, "documentsTree");
        documentsTree.startup();


        function openDocTab(item, node) {
            var itsParent = item.hasOwnProperty("haveChildren");
            var restUrl = item.restUrl;
            var widget;
            var id = item.id;
            var widgetUrl;
            var store;

            switch(restUrl) {
                case 'tasks':  store = taskStore;
                    break;
                case 'incomings':  store = incomingStore;
                    break;
                case 'outgoings':  store = outgoingStore;
                    break;
            }

            //Check if we are from Grid
            if (item.hasOwnProperty("target")) {

            }
            //Check if we are from parent node
            else if (itsParent) {
                widgetUrl = "/ecm/rest/widgets/" + item.id
            }

            else {
                widgetUrl = "/ecm/rest/widgets/" + item.restUrl + "/" + item.id;
            }

            //if the tab is already open, switch on it
            var tabContainer = Registry.byId("TabContainer");
            var pane = Registry.byId("pane_" + id);
            if (pane != undefined) {
                tabContainer.selectChild(pane);
                return;
            }


            xhr(widgetUrl, {
                handleAs: "json"
            }).then(function (data) {
                var id, title;
                params = {store: store, tree:  Registry.byId('documentsTree'), restUrl: restUrl};
                if (itsParent) {

                    widget = new gridWidget(data, params);
                    id = restUrl;
                    title = restUrl.charAt(0).toUpperCase() + restUrl.slice(1);
                }
                else {
                    var params = {store: store, tree: Registry.byId('documentsTree')};
                    widget = new formsWidget(data, params);
                    id = data.entity.id;
                    title = data.entity.fullname;
                }

                var pane = new ContentPane({
                    title: title, closable: true
                });
                pane.set("id", "pane_" + id);
                tabContainer.addChild(pane);
                tabContainer.selectChild(pane);
                pane.setContent(widget);
                Registry.add(pane);

            }, function (err) {
                console.log(err);
            }, function (evt) {
                // Handle a progress event from the request if the
                // browser supports XHR2
            });

        }

    }

    xhr(restURL+"widgets/persons", {
        handleAs: "json"
    }).then(function (data) {
        var id, title;
        params = {store: personStore, tree:  Registry.byId('personTree'), restUrl: "persons", closable: false};
        widget = new gridWidget(data, params);
        widget.placeAt(dom.byId('gridContainer'));

    }.bind(this), function (err) {
        console.log(err);
    }, function (evt) {
        // Handle a progress event from the request if the
        // browser supports XHR2
    });
});