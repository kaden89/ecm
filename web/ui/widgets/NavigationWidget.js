
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
    "dojo/store/JsonRest",
    "dojo/store/Observable",
    "dojo/store/Memory",
    "dojo/_base/array",
    "dojo/data/ObjectStore",
    "dijit/tree/ForestStoreModel",
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
             MemoryStore,
             array,
             ObjectStore,
             ForestStoreModel,
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
        constructor: function (params) {
            lang.mixin(this, params);
        }
        ,
        startup: function () {
            this.inherited(arguments);
            this.initTrees();
        },
        initTrees: function () {
            var personTreeStore = new Observable(this.initStore("/rest/employees/personTree"));
            var incomingTreeStore = new Observable(this.initStore("/rest/documents/tree/incomings"));
            var outgoingsTreeStore = new Observable(this.initStore("/rest/documents/tree/outgoings"));
            var tasksTreeStore = new Observable(this.initStore("/rest/documents/tree/tasks"));

            this.personTree = this.initTree(personTreeStore, this.employees, Person);
            this.incomingTree = this.initTree(incomingTreeStore, this.incoming, Incoming);
            this.outgoingsTree = this.initTree(outgoingsTreeStore, this.outgoings, Outgoing);
            this.tasksTree = this.initTree(tasksTreeStore, this.tasks, Task);

        },
        initStore: function(url){
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
        initTree: function (store, node, statefulModel) {
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

            return tree = new Tree({
                model: model,
                onDblClick: function (item) {
                    topic.publish("navigation/openItem", new statefulModel(item));
                }
            }, node);
        },
        updatePersonTree: function () {
            this.personTree.update();
        },
        updateIncomingTree: function () {
            this.incomingTree.update();
        },
        updateOutgoingTree: function () {
            this.outgoingsTree.update();
        },
        updateTaskTree: function () {
            this.tasksTree.update();
        }
    });
});