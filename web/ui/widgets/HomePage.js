
define([
    "dojo/_base/lang",
    "dojo/_base/declare",
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
    "dojo/text!/ecm/ui/templates/HomePage.html",
    "myApp/ecm/ui/widgets/NavigationWidget",
    "dojo/i18n"
], function (lang,
             declare,
             registry,
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
             i18n) {
    return declare("HomePage", [_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        templateString: template,
        widgetsInTemplate: true,
        constructor: function (params) {
            var nav = new NavigationWidget({}, this.navigation);
            nav.startup();
        }
        ,
        startup: function () {
            this.inherited(arguments);
        }

    });
});