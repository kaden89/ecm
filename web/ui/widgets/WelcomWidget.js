
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
    // "widgets/CommonFormWidget",
    // "widgets/MyTree",
    "dojo/data/ObjectStore",
    "dijit/tree/ForestStoreModel",
    "dojo/text!/ecm/ui/templates/WelcomWidget.html",
    "myApp/ecm/ui/widgets/NavigationWidget",
    "myApp/ecm/ui/AppController",
    "dojo/i18n"
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
             template,
             NavigationWidget,
             AppController,
             i18n) {
    return declare("WelcomWidget", [_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        templateString: template,
        widgetsInTemplate: true,
        personStore: null,
        incomingStore: null,
        outgoingStore: null,
        taskStore: null,
        navWidget: null,
        constructor: function (params) {
            lang.mixin(this, params);
        }
        ,
        startup: function () {
            this.inherited(arguments);
            this.navWidget.placeAt(this.navigation);
        },
        getTabContainer: function () {
            return this.tabContainer;
        },
        switchOnTabIfOpened: function (id) {
            var tabContainer = this.tabContainer;
            var existPane = Registry.byId("pane_" + id);
            if (existPane != undefined) {
                tabContainer.selectChild(existPane);
                return;
            }
        },
        openNewTab: function (widget, model) {
            var tabContainer = this.tabContainer;
            var pane = new ContentPane({
                title: model.fullname, closable: true
            });
            pane.set("id", "pane_" + model.id);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
            pane.setContent(widget);
            Registry.add(pane);
        },
        closeModelTab: function (model) {
            var tabPane = this.tabContainer;
            var pane;
            if (model.id == undefined) {
                pane = Registry.byId("newPane_" + model.type);
            }
            else {
                pane = Registry.byId("pane_" + model.id);
            }
            tabPane.removeChild(pane);
            tabPane.selectChild(this.welcomPane);
            pane.destroy();
        }



    });
});