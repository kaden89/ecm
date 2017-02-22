define([
    "dijit/_WidgetBase",
    "dojo/_base/declare",
    "dojo/dom",
    "dojo/topic",
    "dojo/_base/lang",
    "dojo/Stateful",
    "dojo/store/JsonRest",
    "dojo/store/Observable",
    "dijit/registry",
    "dijit/layout/ContentPane",
    "myApp/ecm/ui/widgets/CommonForm",
    "myApp/ecm/ui/widgets/NavigationWidget",
    "myApp/ecm/ui/widgets/WelcomWidget",
    "dojo/text!/ecm/ui/templates/Person.html",
    "dojo/text!/ecm/ui/templates/Incoming.html",
    "dojo/text!/ecm/ui/templates/Outgoing.html",
    "dojo/text!/ecm/ui/templates/Task.html",
    "dojo/text!/ecm/ui/templates/Grid.html"
], function (_WidgetBase,
             declare,
             dom,
             topic,
             lang,
             Stateful,
             JsonRest,
             Observable,
             Registry,
             ContentPane,
             CommonForm,
             NavigationWidget,
             WelcomWidget,
             personTemplate,
             incomingTemplate,
             outgoingTemplate,
             taskTemplate,
             gridTemplate) {
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
            this.initWidgets();
            this.initStores();
            this.initSubscribes();
        },
        initWidgets: function () {
            var appDiv = dom.byId("app");
            this.navWidget = new NavigationWidget();
            this.welcomWidget = new WelcomWidget({navWidget: this.navWidget}, appDiv);
            this.welcomWidget.startup();
        },
        initSubscribes: function () {
            topic.subscribe("navigation/tree/openItem", lang.hitch(this, this.openItem));
            topic.subscribe("commonForm/Close", lang.hitch(this, this.closeTab));
            topic.subscribe("commonForm/Save", lang.hitch(this, this.closeTab));
            topic.subscribe("commonForm/Delete", lang.hitch(this, this.closeTab));
        },
        initStores: function () {
            var restUrl = 'http://localhost:8080/ecm/rest/';
            this.personStore = new Observable(new JsonRest({
                idProperty: 'id',
                target: restUrl + "employees/",
                headers: {
                    'Content-Type': "application/json; charset=UTF-8"
                },
                getChildren: function (object) {
                    return object;
                }
            }));

            this.taskStore = new Observable(new JsonRest({
                idProperty: 'id',
                target: restUrl + "documents/tasks",
                getChildren: function (object) {
                    return object;
                }
            }));

            this.incomingStore = new Observable(new JsonRest({
                idProperty: 'id',
                target: restUrl + "documents/incomings",
                getChildren: function (object) {
                    return object;
                }
            }));

            this.outgoingStore = new Observable(new JsonRest({
                idProperty: 'id',
                target: restUrl + "documents/outgoings",
                getChildren: function (object) {
                    return object;
                }
            }));


        },
        openItem: function (model) {
            console.log(model);
            if (model.children) return;

            this.welcomWidget.switchOnTabIfOpened(model.id);

            var formWidget = new CommonForm({
                templateString: this.getTemplateByModelType(model),
                model: new Stateful(model)
            });

            this.welcomWidget.openNewTab(formWidget, model);
        },
        closeTab: function close(model) {
            this.welcomWidget.closeModelTab(model);
        },
        getStoreByModelType: function (model) {
            switch (model.type) {
                case 'task':
                    return this.taskStore;
                    break;
                case 'incoming':
                    return this.incomingStore;
                    break;
                case 'outgoing':
                    return this.outgoingStore;
                    break;
                case 'person':
                    return this.personStore;
                    break;
            }
        },
        getTemplateByModelType: function (model) {
            switch (model.type) {
                case 'task':
                    return taskTemplate;
                    break;
                case 'incoming':
                    return incomingTemplate;
                    break;
                case 'outgoing':
                    return outgoingTemplate;
                    break;
                case 'person':
                    return personTemplate;
                    break;
            }
        }
    });
});