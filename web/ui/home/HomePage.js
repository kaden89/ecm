/**
 * Created by dkarachurin on 16.02.2017.
 */
define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dojo/query",
    "../_global/widget/NavigationWidget",
    "dojo/text!./templates/HomePage.html",
    "dojo/text!./css/HomePage.css"
], function (
    declare,
    _WidgetBase,
    _TemplatedMixin,
    query,
    NavigationWidget,
    template,
    css
) {
    return declare([_WidgetBase, _TemplatedMixin], {
        templateString: template,

        postCreate: function () {
            this.inherited(arguments);
            var navigationWidget = new NavigationWidget({}, this.navigationNode);

            // setting style
            var styleElement = window.document.createElement('style');
            styleElement.setAttribute("type", "text/css");
            query('head')[0].appendChild(styleElement);

            if (styleElement.styleSheet) {
                styleElement.styleSheet.cssText = css; // IE
            } else {
                styleElement.innerHTML = css; // the others
            }
        }
    });
});