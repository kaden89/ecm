/**
 * Created by dkarachurin on 20.02.2017.
 */
define([
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dijit/registry",
    "dojo/store/Cache",
    "dojo/store/JsonRest",
    "dojo/store/Observable",
    "dojo/store/Memory",
    "dojo/_base/array",
    "dojo/data/ObjectStore",
    "dojo/i18n",
    "dojo/dom-construct",
    "dojo/dom",
    "/ecm/ui/widgets/HomePage.js"
], function (lang,
             declare,
             registry,
             Cache,
             JsonRest,
             Observable,
             MemoryStore,
             array,
             ObjectStore,
             i18n,
             domConstruct,
             dom,
             HomePage) {
    return declare("App", [], {
        domNode: null,
        constructor: function (params) {
            lang.mixin(this, params);
            var domNode = domConstruct.create("div", {
                innerHTML: "Six"
            }, "app");
            this.prepareDomNode();
            var homePage = new HomePage();
            homePage.placeAt(domNode);
            var b;

        }
        ,
        prepareDomNode: function () {



        },
        startup: function () {

        }

    });
});