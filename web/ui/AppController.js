define([
    "dijit/_WidgetBase",
    "dojo/_base/declare",
    "dojo/dom",
    "dojo/topic",
    "dojo/_base/lang",
    "dojo/Stateful",
    "myApp/ecm/ui/modules/JsonRest",
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
            this.navWidget = new NavigationWidget();
            var welcomGrid = new CommonGrid({store: this.personStore, id: Person.tableName, modelClass: Person, closable: false});
            this.welcomWidget = new WelcomWidget({navWidget: this.navWidget, welcomGridWidget: welcomGrid}, dom.byId("app"));
            this.welcomWidget.startup();
        },
        initSubscribes: function () {
            topic.subscribe("navigation/openItem", lang.hitch(this, this.openItem));
            topic.subscribe("navigation/openGrid", lang.hitch(this, this.openGrid));
            topic.subscribe("commonForm/Close", lang.hitch(this, this.closeTab));
            topic.subscribe("commonForm/Save", lang.hitch(this, this.saveItem));
            topic.subscribe("commonForm/Delete", lang.hitch(this, this.deleteItem));
            topic.subscribe("commonGrid/openItem", lang.hitch(this, this.openItem));
            topic.subscribe("commonGrid/Close", lang.hitch(this, this.closeTab));
            topic.subscribe("commonGrid/Create", lang.hitch(this, this.createItem));
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
            if (this.welcomWidget.switchOnTabById(model.id==undefined ? model.declaredClass : model.id)) return;
            var formWidget = new CommonForm({model: model, templateString: this.getTemplateByModel(model), isNew: model.id==undefined});
            this.welcomWidget.openNewTab(formWidget);
        },
        createItem: function (ModelClass) {
            var model = new ModelClass();
            this.openItem(model);
        },
        openGrid: function (ModelClass) {
            if (this.welcomWidget.switchOnTabById(ModelClass.tableName)) return;
            var gridWidget = new CommonGrid({store: this.getStoreByModel(new ModelClass()), id: ModelClass.tableName, modelClass: ModelClass});
            this.welcomWidget.openNewGridTab(gridWidget);
        },
        closeTab: function close(id) {
            this.welcomWidget.closeTabById(id);
        },
        saveItem: function (formWidget) {
            var store = this.getStoreByModel(formWidget.model);
            //save new
            if (formWidget.isNew) {
                store.add(formWidget.model).then(function (data) {
                    formWidget.updateAfterSave(data);
                    this.welcomWidget.reopenTabForModel(formWidget.model);
                    this.updateTreeByModel(formWidget.model);
                }.bind(this));
            }
            else {
                store.put(formWidget.model).then(function (data) {
                    var pane = Registry.byId(this.welcomWidget.paneSuffix + data.id);
                    pane.set("title", data.fullname);
                    this.updateTreeByModel(formWidget.model);
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
                this.welcomWidget.closeTabById(model.id);
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
            if (model instanceof Person) {
                return this.personStore;
            } else if (model instanceof Incoming) {
                return this.incomingStore;
            } else if (model instanceof Outgoing) {
                return this.outgoingStore;
            } else if (model instanceof Task) {
                return this.taskStore;
            }
        },
        updateTreeByModel: function (model) {
            if (model instanceof Person) {
                this.navWidget.updatePersonTree();
            } else if (model instanceof Incoming) {
                this.navWidget.updateIncomingTree();
            } else if (model instanceof Outgoing) {
                this.navWidget.updateOutgoingTree();
            } else if (model instanceof Task) {
                this.navWidget.updateTaskTree();
            }
        },
        getTemplateByModel: function (model) {
            if (model instanceof Person) {
                return personTemplate;
            } else if (model instanceof Incoming) {
                return incomingTemplate;
            } else if (model instanceof Outgoing) {
                return outgoingTemplate;
            } else if (model instanceof Task) {
                return taskTemplate;
            }
        }
    });
});