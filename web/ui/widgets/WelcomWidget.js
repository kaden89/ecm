/**
 * Виджет стартовой страницы приложения
 * @author dkarachurin
 */
define([
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/topic",
    "dijit/registry",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/layout/ContentPane",
    "dijit/layout/TabContainer",
    "dijit/layout/BorderContainer",
    "dijit/layout/AccordionContainer",
    "dijit/layout/AccordionPane",
    "dojo/store/Cache",
    "dojo/store/JsonRest",
    "dojo/store/Observable",
    "dojo/store/Memory",
    "dojo/_base/array",
    "dojo/data/ObjectStore",
    "dijit/tree/ForestStoreModel",
    "dojo/text!/ecm/ui/templates/WelcomWidget.html"
], function (lang,
             declare,
             topic,
             Registry,
             _WidgetBase,
             _TemplatedMixin,
             _WidgetsInTemplateMixin,
             ContentPane,
             TabContainer,
             BorderContainer,
             AccordionContainer,
             AccordionPane,
             Cache,
             JsonRest,
             Observable,
             MemoryStore,
             array,
             ObjectStore,
             ForestStoreModel,
             template) {
    return declare("WelcomWidget", [_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        templateString: template,
        navWidget: null,
        welcomGridWidget: null,
        paneSuffix: null,
        constructor: function (params) {
            lang.mixin(this, params);
            this.paneSuffix = "pane_";
        }
        ,
        startup: function () {
            this.inherited(arguments);
            this.navWidget.placeAt(this.navigation);
            this.welcomGridWidget.placeAt(this.welcomGrid);
        },
        getTabContainer: function () {
            return this.tabContainer;
        },
        switchOnTabById: function (id) {
            var tabContainer = this.tabContainer;
            var existPane = Registry.byId(this.paneSuffix + id);
            if (existPane != undefined) {
                tabContainer.selectChild(existPane);
                return true;
            }
        },
        openNewTab: function (widget) {
            var tabContainer = this.tabContainer;
            var title, id;
            if (widget.isNew) {
                title = "New " + widget.model.declaredClass;
                id = this.paneSuffix + widget.model.declaredClass;
            } else {
                title = widget.model.fullname;
                id = this.paneSuffix + widget.model.id;
            }
            var pane = new ContentPane({

                title: title, closable: true
            });
            pane.set("id", id);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
            pane.setContent(widget);
            Registry.add(pane);
        },
        openNewGridTab: function (widget) {
            var tabContainer = this.tabContainer;
            var pane = new ContentPane({
                title: widget.modelClass.tableName, closable: true
            });
            pane.set("id", this.paneSuffix + widget.modelClass.tableName);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
            pane.setContent(widget);
            Registry.add(pane);
        },
        reopenTabForModel: function (model) {
            var pane = Registry.byId(this.paneSuffix + model.declaredClass);
            var tabContainer = this.tabContainer;
            tabContainer.removeChild(pane);
            pane.set("title", model.fullname);
            pane.set("id", this.paneSuffix + model.id);
            Registry.remove(this.paneSuffix + model.declaredClass);
            Registry.add(pane);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
        },
        closeTabById: function (id) {
            var tabPane = this.tabContainer;
            var pane = Registry.byId(this.paneSuffix + id);
            if (pane != undefined) {
                tabPane.removeChild(pane);
                pane.destroy();
            }
        }
    });
});