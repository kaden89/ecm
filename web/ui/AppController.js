/**
 * Контроллер приложения. Управляет виджетами и хранилищами
 * @author dkarachurin
 */
define([
    "dijit/_WidgetBase",
    "dojo/_base/declare",
    "dojo/dom",
    "dojo/topic",
    "dojo/_base/lang",
    "dojo/_base/json",
    "dojo/Stateful",
    "dojo/request/xhr",
    "myApp/ecm/ui/modules/JsonRest",
    "dojo/store/Observable",
    "dijit/registry",
    "dijit/ConfirmDialog",
    "dijit/Dialog",
    "myApp/ecm/ui/widgets/CommonForm",
    "myApp/ecm/ui/widgets/NavigationWidget",
    "myApp/ecm/ui/widgets/WelcomWidget",
    "myApp/ecm/ui/widgets/CommonGrid",
    "myApp/ecm/ui/models/Incoming",
    "myApp/ecm/ui/models/Outgoing",
    "myApp/ecm/ui/models/Person",
    "myApp/ecm/ui/models/Task"
], function (_WidgetBase,
             declare,
             dom,
             topic,
             lang,
             dojo,
             Stateful,
             xhr,
             JsonRest,
             Observable,
             Registry,
             ConfirmDialog,
             Dialog,
             CommonForm,
             NavigationWidget,
             WelcomWidget,
             CommonGrid, Incoming, Outgoing, Person, Task) {
    return declare("AppController", [_WidgetBase], {
        navWidget: null,
        welcomWidget: null,
        personStore: null,
        incomingStore: null,
        outgoingStore: null,
        taskStore: null,
        postStore: null,
        urlConfig: null,
        constructor: function (params) {
            lang.mixin(this, params);
        },
        startup: function () {
            this.inherited(arguments);
            xhr("rest/config", {
                handleAs: "json"
            }).then(function (data) {
                this.urlConfig = data;
                this.initStores();
                this.initWidgets();
                this.initSubscribes();
            }.bind(this))
        },
        initWidgets: function () {
            this.navWidget = new NavigationWidget({urlConfig: this.urlConfig});
            var welcomGrid = new CommonGrid({
                store: this.personStore,
                id: Person.tableName,
                modelClass: Person,
                closable: false
            });
            this.welcomWidget = new WelcomWidget({
                navWidget: this.navWidget,
                welcomGridWidget: welcomGrid
            }, dom.byId("app"));
            this.welcomWidget.startup();
        },
        initSubscribes: function () {
            topic.subscribe("commonEvent/openItem", lang.hitch(this, this.openItem));
            topic.subscribe("commonEvent/Close", lang.hitch(this, this.closeTab));
            topic.subscribe("navigation/openGrid", lang.hitch(this, this.openGrid));
            topic.subscribe("commonForm/Save", lang.hitch(this, this.saveItem));
            topic.subscribe("commonForm/Delete", lang.hitch(this, this.deleteItem));
            topic.subscribe("commonGrid/Create", lang.hitch(this, this.createItem));
            topic.subscribe("commonGrid/Delete", lang.hitch(this, this.deleteFromGrid));
        },
        initStores: function () {
            this.personStore = this.initStore(this.urlConfig.employeeURL);
            this.outgoingStore = this.initStore(this.urlConfig.outgoingURL);
            this.incomingStore = this.initStore(this.urlConfig.incomingURL);
            this.taskStore = this.initStore(this.urlConfig.taskURL);
            this.postStore = this.initStore(this.urlConfig.postURL);
        },
        initStore: function (url) {
            return new Observable(new JsonRest({
                idProperty: 'id',
                target: url,
                getChildren: function (object) {
                    return object;
                }
            }));
        },
        openItem: function (model) {
            console.log(model);
            if (this.welcomWidget.switchOnTabById(model.id == undefined ? model.declaredClass : model.id)) return;
            var formWidget = new CommonForm({
                model: model,
                isNew: model.id == undefined,
                personStore: this.personStore,
                postStore: this.postStore,
                urlConfig: this.urlConfig
            });
            this.welcomWidget.openNewTab(formWidget);
        },
        createItem: function (ModelClass) {
            var model = new ModelClass();
            this.openItem(model);
        },
        openGrid: function (ModelClass) {
            if (this.welcomWidget.switchOnTabById(ModelClass.tableName)) return;
            var gridWidget = new CommonGrid({
                store: this.getStoreByModel(new ModelClass()),
                id: ModelClass.tableName,
                modelClass: ModelClass
            });
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
                    formWidget.updateAfterSaveNew(data);
                    this.welcomWidget.reopenTabForModel(formWidget.model);
                    this.navWidget.updateTreeByModel(formWidget.model);
                }.bind(this));
            }
            else {
                store.put(formWidget.model).then(function (data) {
                    var pane = Registry.byId(this.welcomWidget.paneSuffix + data.id);
                    pane.set("title", data.fullname);
                    this.navWidget.updateTreeByModel(formWidget.model);
                }.bind(this));
            }
        },
        deleteItem: function (model) {
            var id = model.id;
            var store = this.getStoreByModel(model);
            var deleteDialog = new ConfirmDialog({
                title: "Delete",
                content: "Are you sure that you want to delete " + model.fullname + "?",
                style: "width: 300px",
                onCancel: function () {
                    return;
                },
                onExecute: function () {
                    store.remove(id).then(success.bind(this, model), this.handleError);
                }.bind(this)

            });
            deleteDialog.show();

            function success(model) {
                this.welcomWidget.closeTabById(model.id);
                this.navWidget.updateTreeByModel(model);
            }
        },
        deleteFromGrid: function (items, ModelClass) {
            var store = this.getStoreByModel(new ModelClass());
            if (items.length) {
                dojo.forEach(items, function (selectedItem) {
                    if (selectedItem !== null) {
                        store.get(selectedItem).then(function (data) {
                            this.deleteItem(new ModelClass(data));
                        }.bind(this))
                    }
                }.bind(this));
            }
        },
        handleError: function (err) {
            new Dialog({
                title: "Error!",
                content: err.responseText,
                style: "width: 300px"
            }).show();
            console.log("error");
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
        }
    });
});