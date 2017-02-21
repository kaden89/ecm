define([
    "dijit/_WidgetBase",
    "dojo/_base/declare",
    "dojo/topic",
    "dojo/_base/lang",
    "dojo/Stateful",
    "dijit/registry",
    "dijit/layout/ContentPane",
    "myApp/ecm/ui/widgets/CommonForm",
    "dojo/text!/ecm/ui/templates/Person.html"
], function (_WidgetBase,
             declare,
             topic,
             lang,
             Stateful,
             Registry,
             ContentPane,
             CommonForm,
             personTemplate) {
    return declare("AppController", [_WidgetBase], {
        navWidget: null,
        welcomWidget: null,
        personStore: null,
        incomingStore: null,
        outgoingStore: null,
        taskStore: null,
        constructor: function (params) {
            lang.mixin(this, params);
        },
        startup: function () {
            this.inherited(arguments);
            this.initStores();
            this.initSubscribes();
        },
        initStores: function () {

        },
        initSubscribes: function () {
            topic.subscribe("navigation/tree/openItem", lang.hitch(this, this.openItem));
        },
        openItem: function (item) {
            console.log(item);
            var tabContainer = this.welcomWidget.getTabContainer();
            var existPane = Registry.byId("pane_" + item.id);
            if (existPane != undefined) {
                tabContainer.selectChild(existPane);
                return;
            }

            var formWidget;

            switch(item.restUrl) {
                case 'tasks':
                    break;
                case 'incomings':
                    break;
                case 'outgoings':
                    break;
                case 'persons':
                    formWidget = new CommonForm({
                        templateString: personTemplate,
                        store: this.personStore,
                        model: new Stateful(item)});
                    break;
            }

            var pane = new ContentPane({
                title: item.fullname, closable: true
            });
            pane.set("id", "pane_" + item.id);
            tabContainer.addChild(pane);
            tabContainer.selectChild(pane);
            pane.setContent(formWidget);
            Registry.add(pane);
        }
    });
});