/**
 * Виджет Меню навигации
 * @author dkarachurin
 */
define([
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/topic",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/layout/AccordionContainer",
    "dijit/layout/AccordionPane",
    "dijit/tree/ObjectStoreModel",
    "dojo/store/Cache",
    "myApp/ecm/ui/modules/JsonRest",
    "dojo/store/Observable",
    "dojo/text!/ecm/ui/templates/NavigationWidget.html",
    "myApp/ecm/ui/modules/Tree",
    "myApp/ecm/ui/models/Incoming",
    "myApp/ecm/ui/models/Outgoing",
    "myApp/ecm/ui/models/Person",
    "myApp/ecm/ui/models/Task"
], function (lang,
             declare,
             topic,
             _WidgetBase,
             _TemplatedMixin,
             _WidgetsInTemplateMixin,
             AccordionContainer,
             AccordionPane,
             ObjectStoreModel,
             Cache,
             JsonRest,
             Observable,
             template,
             Tree,
             Incoming, Outgoing, Person, Task) {
    return declare("NavigationWidget", [_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
        templateString: template,
        widgetsInTemplate: true,
        personTree: null,
        incomingTree: null,
        outgoingsTree: null,
        tasksTree: null,
        urlConfig: null,
        constructor: function (params) {
            lang.mixin(this, params);
        }
        ,
        startup: function () {
            this.inherited(arguments);
            this.initTrees();
        },
        initTrees: function () {
            var personTreeStore = new Observable(this.initStore(this.urlConfig.employeeTree));
            var incomingTreeStore = new Observable(this.initStore(this.urlConfig.incomingTree));
            var outgoingsTreeStore = new Observable(this.initStore(this.urlConfig.outgoingTree));
            var tasksTreeStore = new Observable(this.initStore(this.urlConfig.taskTree));

            this.personTree = this.initTree(personTreeStore, this.employees, Person);
            this.incomingTree = this.initTree(incomingTreeStore, this.incoming, Incoming);
            this.outgoingsTree = this.initTree(outgoingsTreeStore, this.outgoings, Outgoing);
            this.tasksTree = this.initTree(tasksTreeStore, this.tasks, Task);

        },
        initStore: function (url) {
            return new JsonRest({
                idProperty: 'id',
                target: window.location + url,
                mayHaveChildren: function (object) {
                    return "haveChildren" in object;
                },
                getChildren: function (object) {
                    return this.get(object.id).then(function (fullObject) {
                        return fullObject.children;
                    });
                }
            });
        },
        initTree: function (store, node, StatefulModel) {
            var model = new ObjectStoreModel({
                store: store,
                getRoot: function (onItem) {
                    this.store.get("").then(onItem);
                },
                mayHaveChildren: function (object) {
                    return "children" in object;
                },
                getLabel: function (item) {
                    if (item.fullname != undefined) {
                        return item.fullname;
                    } else {
                        return item.name;
                    }
                }
            });

            return new Tree({
                model: model,
                onDblClick: function (item) {
                    if (item.hasOwnProperty("children")) {
                        topic.publish("navigation/openGrid", StatefulModel);
                    } else {
                        topic.publish("commonEvent/openItem", new StatefulModel(item));
                    }
                }
            }, node);
        },
        updateTreeByModel: function (model) {
            if (model instanceof Person) {
                this.personTree.update();
            } else if (model instanceof Incoming) {
                this.incomingTree.update();
            } else if (model instanceof Outgoing) {
                this.outgoingsTree.update();
            } else if (model instanceof Task) {
                this.tasksTree.update();
            }
        }
    });
});