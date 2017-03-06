/**
 * Виджет общей формы для отображения таблицы документов и Person
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/topic",
    "dijit/registry",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/_WidgetBase",
    "dijit/Toolbar",
    "dijit/form/Button",
    "dojo/_base/lang",
    'gridx/Grid',
    'gridx/modules/Dod',
    'gridx/core/model/cache/Async',
    "gridx/modules/RowHeader",
    "gridx/modules/select/Row",
    "gridx/modules/IndirectSelect",
    "gridx/modules/SingleSort",
    "gridx/modules/Bar",
    "gridx/modules/ColumnResizer",
    "gridx/modules/NestedSort",
    "gridx/modules/Filter",
    "gridx/modules/filter/FilterBar",
    "gridx/modules/filter/QuickFilter",
    "gridx/modules/Pagination",
    "gridx/modules/pagination/PaginationBar",
    "dojo/text!/ecm/ui/templates/Grid.html"

], function (declare, topic, Registry, _TemplatedMixin, _WidgetsInTemplateMixin, _WidgetBase, Toolbar, Button, lang,
             GridX, Dod, Cache, RowHeader, Row, IndirectSelect, SingleSort, Bar, Resizer,
             NestedSort, Filter, FilterBar, QuickFilter, Pagination, PaginationBar, template) {
    return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        store: null,
        grid: null,
        id: null,
        closable: true,
        modelClass: null,
        constructor: function (params) {
            lang.mixin(this, params);
            this.templateString = template;
        }
        ,
        startup: function () {
            this.inherited(arguments);

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
                    topic.publish("commonGrid/Delete", this.grid.select.row.getSelected(), this.modelClass);
                })
            });

            var closeButton = new Button({
                label: "Close",
                iconClass: "dijitEditorIcon dijitEditorIconCancel",
                onClick: lang.hitch(this, function () {
                    topic.publish("commonEvent/Close", this.modelClass.tableName);
                })
            });

            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);

            if (this.closable) {
                toolbar.addChild(closeButton);
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
                filterSetupQuery: function (expr) {
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
                    {
                        moduleClass: SingleSort,
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
                    topic.publish("commonEvent/openItem", new this.modelClass(data));
                }.bind(this));
            }));
            this.grid.startup();
        }
    });
});