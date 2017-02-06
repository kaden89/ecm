/**
 * Created by dkarachurin on 03.02.2017.
 */
define([
    "dojo/_base/declare",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/_WidgetBase",
    "dojo/Stateful",
    "dojo/dom",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dojo/dom-form",
    "dojo/dom-attr",
    "dijit/registry",
    "dojo/request",
    "dojo/request/xhr",
    "dojo/dom-construct",
    "dojox/form/Uploader",
    "dojox/form/uploader/FileList",
    "dojox/form/uploader/plugins/IFrame",
    "dijit/form/Form",
    "dojo/_base/lang",
    'dojo/_base/json',
    "dojo/date/locale",
    "dijit/ConfirmDialog",
    "dijit/Dialog",
    "dijit/Editor",
    "dijit/form/Select",
    "dojo/store/JsonRest",
    "dijit/form/FilteringSelect",
    "dojox/mvc/at",
    "dojo/store/Memory",
    "dijit/form/CheckBox",
    "/ecm/resources/js/formsWidget.js",
    'gridx/Grid',
    'gridx/modules/Dod',
    'gridx/core/model/cache/Async',
    "gridx/modules/RowHeader",
    "gridx/modules/select/Row",
    "gridx/modules/IndirectSelect",
    "gridx/modules/SingleSort",
    "gridx/modules/Bar",
    "dojo/store/Observable",
    "dijit/layout/ContentPane",
    "dojo/on",
    "dojo/require",
    "dijit/layout/ContentPane",
    "dijit/layout/BorderContainer",
    "dijit/form/TextBox",
    "dijit/form/ValidationTextBox",
    "dijit/form/DateTextBox",
    "dojox/form/Uploader",
    "dojox/form/uploader/FileList",
    "dojox/mvc/Output",
    "/ecm/resources/js/util.js"


], function (declare, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Stateful, dom, Toolbar, Button, domForm, domAttr, Registry, request, xhr,
             domConstruct, Uploader, FileList, IFrame, Form, lang, dojo, locale, ConfirmDialog, Dialog, Editor, Select, JsonRest, FilteringSelect,
             at, Memory, CheckBox,formsWidget, GridX, Dod, Cache, RowHeader, Row, IndirectSelect, SingleSort, Bar, Observable, ContentPane) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        store: null,
        script: null,
        tree: null,
        constructor: function (args, store, tree) {
            this.templateString = args.template;
            this.store = store;
            this.script = args.script;
            this.tree = tree;
        }
        ,
        startup: function () {
            this.inherited(arguments);
            var store = this.store;
            var columns = [
                {id: 'id', field: 'id', name: 'id', width: '5%'},
                {id: 'name', field: 'name', name: 'Name', width: '9.5%'},
                {id: 'author', field: 'authorName', name: 'Author', width: '13.5%'},
                {id: 'executor', field: 'executorName', name: 'Executor', width: '13.5%'},
                {id: 'controller', field: 'controllerName', name: 'Controller', width: '13.5%'},
                {id: 'isControlled', field: 'isControlled', name: 'Is controlled', width: '5%'},
                {id: 'regNumber', field: 'regNumber', name: 'Reg number', width: '5%'},
                {id: 'date', field: 'date', name: 'Date', width: '5%'},
                {id: 'dateOfIssue', field: 'dateOfIssue', name: 'Date of issue', width: '5%'},
                {id: 'deadline', field: 'deadline', name: 'Deadline', width: '5%'},
                {id: 'text', field: 'text', name: 'Text', width: '20%'}
            ];

            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                label: "Create",
                iconClass: "dijitEditorIcon dijitEditorIconPaste",
                onClick: createNewTab
            });

            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick: deleteSelected
            });

            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick: lang.hitch(this, close)
            });

            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
            toolbar.addChild(closeButton);

            function close() {
                var tabPane = Registry.byId("TabContainer");
                var pane;
                if (this.isNew) {
                    pane = Registry.byId("newPane_");
                }
                else {
                    pane = Registry.byId("pane_tasks");
                }
                tabPane.removeChild(pane);
                tabPane.selectChild(Registry.byId("WelcomPane"));
                pane.destroy();
            }


            //Create grid widget.
            var grid = GridX({
                id: 'taskGrid',
                cacheClass: Cache,
                store: this.store,
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
                        initialOrder: {colId: 'name', descending: true}
                    },
                    Bar,
                    RowHeader,
                    Row,
                    IndirectSelect
                ]
            });
            grid.placeAt(this.grid);

            grid.connect(grid, "onRowDblClick", openTab);
            grid.startup();

            function deleteSelected() {
                var items = grid.select.row.getSelected();
                if (items.length) {
                    dojo.forEach(items, function (selectedItem) {
                        if (selectedItem !== null) {
                            deleteDialog = new ConfirmDialog({
                                title: "Delete",
                                content: "Are you sure that you want to delete person with id " + selectedItem + "?",
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


            function createNewTab() {
                xhr("/ecm/rest/widgets/person/", {
                    handleAs: "json"
                }).then(function (data) {
                    var widget = new formsWidget(data, personStore);
                    var tabContainer = Registry.byId("TabContainer");
                    var id = data.entity.id;
                    var pane = Registry.byId("newPane_");
                    if (pane != undefined) {
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

                }, function (err) {
                    console.log(err);
                }, function (evt) {
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
            }

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

                xhr("/ecm/rest/widgets/tasks/" + id, {
                    handleAs: "json"
                }).then(function (data) {
                    var widget = new formsWidget(data, store, Registry.byId('documentsTree'));
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

            function myButtonHandler() {
                console.log('Clicked button');
            }
        }
    });
});