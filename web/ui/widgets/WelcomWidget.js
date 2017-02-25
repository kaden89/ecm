
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
    "myApp/ecm/ui/widgets/CommonGrid"
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
             CommonGrid,
             AppController,
             i18n) {
    return declare("WelcomWidget", [_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        templateString: template,
        widgetsInTemplate: true,
        navWidget: null,
        welcomStore: null,
        welcomGridWidget: null,
        constructor: function (params) {
            lang.mixin(this, params);
        }
        ,
        startup: function () {
            this.inherited(arguments);
            this.navWidget.placeAt(this.navigation);
            var columns = [
                {id: 'id', field: 'id', name: 'id', width: '5%'},
                {id: 'firstname', field: 'firstname', name: 'Firstname', width: '19%'},
                {id: 'surname', field: 'surname', name: 'Surname', width: '19%'},
                {id: 'patronymic', field: 'patronymic', name: 'Patronymic', width: '19%'},
                {id: 'position.post', field: 'positionName', name: 'Position', width: '19%'},
                {id: 'birthday', field: 'birthday', name: 'Birthday', width: '19%'}
            ];
            new CommonGrid({store: this.welcomStore, columns: columns}).placeAt(this.welcomGrid);
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
        reopenTabForModel: function (model) {
            var pane = Registry.byId("newPane_" + model.type);
            var tabContainer = this.tabContainer;
            tabContainer.removeChild(pane);
            pane.set("title", model.fullname);
            pane.set("id", "pane_" + model.id);
            Registry.remove("newPane_" + model.type);
            Registry.add(pane);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
            // toolbar.addChild(deleteButton, 1);
            this.uploader.set('disabled', false);
            this.uploader.set('url', '/ecm/rest/employees/'+ model.id+'/photo');
            this.button.set('disabled', false);
        },
        closeTabByModel: function (model) {
            var tabPane = this.tabContainer;
            var pane;
            if (model.id == undefined) {
                pane = Registry.byId("newPane_" + model.type);
            }
            else {
                pane = Registry.byId("pane_" + model.id);
            }
            tabPane.removeChild(pane);
            // tabPane.selectChild(this.welcomPane);
            pane.destroy();
        }



    });
});