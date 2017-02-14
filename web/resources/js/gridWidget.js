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
    "/ecm/resources/js/myJsonRest.js",
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
    "gridx/modules/ColumnResizer",
    "gridx/modules/NestedSort",
    "gridx/modules/Filter",
    "gridx/modules/filter/FilterBar",
    "gridx/modules/filter/QuickFilter",
    "gridx/modules/Pagination",
    "gridx/modules/pagination/PaginationBar",
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
             at, Memory, CheckBox,formsWidget, GridX, Dod, Cache, RowHeader, Row, IndirectSelect, SingleSort, Bar, Observable, ContentPane,Resizer,
             NestedSort, Filter, FilterBar, QuickFilter, Pagination, PaginationBar) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        store: null,
        script: null,
        tree: null,
        restUrl: null,
        closable: true,
        constructor: function (args, params) {
            this.templateString = args.template;
            this.store = params.store;
            this.script = args.script;
            this.tree = params.tree;
            this.restUrl = params.restUrl;
            if (params.closable != undefined){
                this.closable = params.closable;
            }

        }
        ,
        startup: function () {
            this.inherited(arguments);

            eval(this.script);

            var store = this.store;

            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                label: "Create",
                iconClass: "dijitEditorIcon dijitEditorIconPaste",
                onClick: lang.hitch(this, createNewTab)
            });

            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick: lang.hitch(this, deleteSelected)
            });

            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick: lang.hitch(this, close)
            });

            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);

            if (this.closable){
                toolbar.addChild(closeButton);
            }

            function close() {
                var tabPane = Registry.byId("TabContainer");
                var pane = Registry.byId("pane_"+this.restUrl);
                tabPane.removeChild(pane);
                tabPane.selectChild(Registry.byId("WelcomPane"));
                Registry.remove(pane);
                pane.destroy();
            }


            //Create grid widget.
            var grid = GridX({
                id: this.restUrl,
                cacheClass: Cache,
                store: this.store,
                structure: columns,
                pageSize: 11,
                selectRowMultiple: true,
                filterServerMode: true,
                filterSetupQuery: function(expr){
                    console.log(JSON.stringify(expr));
                    console.log(expr);

                    if (!expr) {
                        return;
                    }
                    var exprJson = JSON.stringify(expr);
                    return {filter: exprJson}

                },
                barTop: [
                    toolbar
                ],
                modules: [
                    //Declare sort module in the "modules" parameter.
                    {
                        moduleClass: SingleSort,
                        //Declare initialOrder as module parameter:
                        initialOrder: {colId: 'id', descending: false}
                    },
                    Bar,
                    RowHeader,
                    Row,
                    IndirectSelect,
                    Resizer,
                    Filter,
                    FilterBar,
                    Pagination,
                    PaginationBar
                ]
            });
            grid.placeAt(this.grid);

            grid.connect(grid, "onRowDblClick", lang.hitch(this, openTab));
            grid.startup();

            function deleteSelected() {
                var items = grid.select.row.getSelected();
                if (items.length) {
                    dojo.forEach(items, function (selectedItem) {
                        if (selectedItem !== null) {
                            deleteDialog = new ConfirmDialog({
                                title: "Delete",
                                content: "Are you sure that you want to delete "+this.restUrl.slice(0, -1)+" with id " + selectedItem + "?",
                                style: "width: 300px",
                                onCancel: function () {
                                    return;
                                },
                                onExecute: function () {
                                    var pane = Registry.byId("pane_"+selectedItem);
                                    if (pane){
                                        var tabContainer = Registry.byId("TabContainer");
                                        tabContainer.removeChild(pane);
                                        Registry.remove(pane);
                                        pane.destroy();
                                    }
                                    grid.store.remove(selectedItem).then(success.bind(this), error);
                                }.bind(this)

                            });
                            deleteDialog.show();
                        }
                    }.bind(this));
                }
            }

            function success() {
                updateTree.call(this, this.tree);
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
                xhr("/ecm/rest/widgets/"+this.restUrl+"/new", {
                    handleAs: "json"
                }).then(function (data) {
                    var params = {store: this.store, tree: this.tree, deletable: false};
                    var widget = new formsWidget(data, params);
                    var tabContainer = Registry.byId("TabContainer");
                    var pane = Registry.byId("newPane_"+this.restUrl);
                    if (pane != undefined) {
                        tabContainer.selectChild(pane);
                        return;
                    }
                    var pane = new ContentPane({
                        title: "New "+this.restUrl.slice(0, -1)+ " (Unsaved)",
                        closable: true
                        // onClose: function () {
                        //     return confirm("Do you really want to Close this?");
                        // }
                    });
                    pane.set("id", "newPane_"+this.restUrl);
                    tabContainer.addChild(pane);
                    tabContainer.selectChild(pane);
                    pane.setContent(widget);
                    Registry.add(pane);

                }.bind(this), function (err) {
                    console.log(err);
                }, function (evt) {
                    // Handle a progress event from the request if the
                    // browser supports XHR2
                });
            }

            function openTab(item) {
                var id = item.rowId;

                //if the tab is already open, switch on it
                var tabContainer = Registry.byId("TabContainer");
                var pane = Registry.byId("pane_" + id);
                if (pane != undefined) {
                    tabContainer.selectChild(pane);
                    return;
                }

                xhr("/ecm/rest/widgets/"+this.restUrl+"/" + id, {
                    handleAs: "json"
                }).then(function (data) {
                    var params = {store: this.store, tree: this.tree};
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

                }.bind(this), function (err) {
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