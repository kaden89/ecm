
define([
    "dojo/_base/declare",
    "dojo/topic",
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
    "dojo/text!/ecm/ui/templates/Grid.html"


], function (declare, topic, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Stateful, dom, Toolbar, Button, domForm, domAttr, Registry, request, xhr,
             domConstruct, Uploader, FileList, IFrame, Form, lang, dojo, locale, ConfirmDialog, Dialog, Editor, Select, JsonRest, FilteringSelect,
             at, Memory, CheckBox,formsWidget, GridX, Dod, Cache, RowHeader, Row, IndirectSelect, SingleSort, Bar, Observable, ContentPane,Resizer,
             NestedSort, Filter, FilterBar, QuickFilter, Pagination, PaginationBar, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        store: null,
        grid: null,
        id: null,
        tree: null,
        restUrl: null,
        closable: true,
        modelClass: null,
        constructor: function (params) {
            lang.mixin(this, params);
            this.templateString = template;
            console.log(this.store);
            console.log(params);
        }
        ,
        startup: function () {
            this.inherited(arguments);

            var store = this.store;

            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                label: "Create",
                iconClass: "dijitEditorIcon dijitEditorIconPaste",
                onClick: lang.hitch(this, function () {
                    topic.publish("commonGrid/Create", this.modelClass);
                })
            });

            var deleteButton = new Button({
                label: "Delete",
                iconClass: "dijitEditorIcon dijitEditorIconDelete",
                onClick: lang.hitch(this, function () {
                    topic.publish("commonGrid/Delete", this.grid);
                })
            });

            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick: lang.hitch(this, function () {
                    topic.publish("commonGrid/Close", this.modelClass.tableName);
                })
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
            this.grid = GridX({
                id: this.restUrl,
                cacheClass: Cache,
                store: this.store,
                structure: this.modelClass.columns,
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
            this.grid.placeAt(this.gridWidget);

            this.grid.connect(this.grid, "onRowDblClick", lang.hitch(this, function (item) {
                this.grid.store.get(item.rowId).then(function (data) {
                    topic.publish("commonGrid/openItem", new this.modelClass(data));
                }.bind(this));
            }));
            this.grid.startup();
        }
    });
});