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
    "myApp/ecm/ui/widgets/CommonGrid",
    "myApp/ecm/ui/models/Incoming",
    "myApp/ecm/ui/models/Outgoing",
    "myApp/ecm/ui/models/Person",
    "myApp/ecm/ui/models/Task",
    "dojo/text!/ecm/ui/templates/Person.html",
    "dojo/text!/ecm/ui/templates/Incoming.html",
    "dojo/text!/ecm/ui/templates/Outgoing.html",
    "dojo/text!/ecm/ui/templates/Task.html"
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
             CommonGrid, Incoming, Outgoing, Person, Task, personTemplate, incomingTemplate, outgoingTemplate, taskTemplate) {
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
            this.initWidgets();
            this.initSubscribes();
        },
        initWidgets: function () {
            var appDiv = dom.byId("app");
            this.navWidget = new NavigationWidget();
            this.welcomWidget = new WelcomWidget({navWidget: this.navWidget, welcomStore: this.personStore}, appDiv);
            this.welcomWidget.startup();
        },
        initSubscribes: function () {
            topic.subscribe("navigation/openItem", lang.hitch(this, this.openItem));
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
            var formWidget = new CommonForm({model: model, templateString: this.getTemplateByModel(model)});
            this.welcomWidget.openNewTab(formWidget, model);
        },
        closeTab: function close(model) {
            this.welcomWidget.closeTabByModel(model);
        },
        saveItem: function (model) {
            var store = this.getStoreByModel(model);
            //create new user
            if (model.id == undefined) {
                store.add(model).then(function (data) {
                    this.welcomWidget.reopenTabForModel(data);
                    this.updateTreeByModel(model);
                }.bind(this));
            }
            else {
                store.put(model).then(function (data) {
                    var pane = Registry.byId("pane_" + data.id);
                    pane.set("title", data.fullname);
                    this.updateTreeByModel(model);
                }.bind(this));
            }
        },
        deleteItem: function (model) {
            var id = model.id;
            var store = this.getStoreByModel(model);
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
                this.updateTreeByModel(model);
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
        getStoreByModel: function (model) {
            if (model instanceof Person){
                return this.personStore;
            } else  if (model instanceof Incoming) {
                return this.incomingStore;
            } else  if (model instanceof Outgoing) {
                return this.outgoingStore;
            } else  if (model instanceof Task) {
                return this.taskStore;
            }
        },
        updateTreeByModel: function (model) {
            if (model instanceof Person){
                this.navWidget.updatePersonTree();
            } else  if (model instanceof Incoming) {
                this.navWidget.updateIncomingTree();
            } else  if (model instanceof Outgoing) {
                this.navWidget.updateOutgoingTree();
            } else  if (model instanceof Task) {
                this.navWidget.updateTaskTree();
            }
        },
        getTemplateByModel: function (model) {
            if (model instanceof Person){
                return personTemplate;
            } else  if (model instanceof Incoming) {
                return incomingTemplate;
            } else  if (model instanceof Outgoing) {
                return outgoingTemplate;
            } else  if (model instanceof Task) {
                return taskTemplate;
            }
        }
    });
});