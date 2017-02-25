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
    "dijit/ConfirmDialog",
    "dijit/Dialog",
    "myApp/ecm/ui/widgets/CommonForm",
    "myApp/ecm/ui/widgets/NavigationWidget",
    "myApp/ecm/ui/widgets/WelcomWidget",
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
             ConfirmDialog,
             Dialog,
             CommonForm,
             NavigationWidget,
             WelcomWidget,
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
            topic.subscribe("commonForm/Save", lang.hitch(this, this.saveItem));
            topic.subscribe("commonForm/Delete", lang.hitch(this, this.deleteItem));
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
                type: model.type,
                model: new Stateful(model)
            });

            this.welcomWidget.openNewTab(formWidget, model);
        },
        closeTab: function close(model) {
            this.welcomWidget.closeTabByModel(model);
        },
        saveItem: function (model) {
            var store = this.getStoreByModelType(model.type);
            //create new user
            if (model.id == undefined) {
                store.add(model).then(function (data) {
                    this.welcomWidget.reopenTabForModel(data);
                    this.navWidget.updateTreeByModelType(model.type);
                }.bind(this));
            }
            else {
                store.put(model).then(function (data) {
                    var pane = Registry.byId("pane_" + data.id);
                    pane.set("title", data.fullname);
                    this.navWidget.updateTreeByModelType(model.type);
                }.bind(this));
            }
        },
        deleteItem: function (model) {
            var id = model.id;
            var store = this.getStoreByModelType(model.type);
            deleteDialog = new ConfirmDialog({
                title: "Delete",
                content: "Are you sure that you want to delete " + model.fullname + "?",
                style: "width: 300px",
                onCancel: function () {
                    return;
                },
                onExecute: function () {
                    store.remove(id).then(success.bind(this, model), error);
                }.bind(this)

            });
            deleteDialog.show();

            function success(model) {
                this.welcomWidget.closeTabByModel(model);
                this.navWidget.updateTreeByModelType(model.type);
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
        },
        getStoreByModelType: function (type) {
            switch (type) {
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
        }
    });
});