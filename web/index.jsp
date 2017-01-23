
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <script type='text/javascript' src='//ajax.googleapis.com/ajax/libs/dojo/1.10.1/dojo/dojo.js' data-dojo-config="async: true, parseOnLoad: true"></script>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.1/dijit/themes/claro/claro.css">
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.rawgit.com/oria/gridx/1.3/resources/claro/Gridx.css">

    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.1/dijit/themes/claro/document.css">



    <script type='text/javascript'>
        var grid;
        var store;

        require({
            packages: [
                {
                    name: 'gridx',
                    location: '//cdn.rawgit.com/oria/gridx/1.3'
                }
            ]
        }, ["dojo/_base/declare",
            "dijit/layout/TabContainer",
            "dijit/layout/ContentPane",
            'gridx/Grid',
            'gridx/modules/Dod',
            'gridx/core/model/cache/Async',
            'dojo/Deferred',
            'dojo/store/util/QueryResults',
            "dojo/store/Memory",
            "gridx/modules/SingleSort",
            "dojo/store/JsonRest",
            "gridx/modules/Bar",
            "dijit/Toolbar",
            "dijit/form/Button",
            "gridx/modules/RowHeader",
            "gridx/modules/select/Row",
            "gridx/modules/IndirectSelect",
            "dijit/Dialog",
            "dijit/registry",
            "dojo/query",
            "dojo/dom",
            "/ecm/resources/js/formsWidget.js",
            "dojo/parser",
            "dojo/request/xhr",
            "dojox/image/Lightbox",
            "dojo/dom",
            /*'dojo/store/Memory',*/
            "dojo/domReady!"], function(declare, TabContainer, ContentPane, GridX, Dod, Cache, Deferred, QueryResults,Memory,SingleSort, JsonRest, Bar, Toolbar, Button,
                                        RowHeader, Row, IndirectSelect, Dialog, Registry,query, Dom, formsWidget, parser, xhr, Lightbox, dom) {
            var restURL = 'http://localhost:8080/ecm/rest/employees';
            var store = new JsonRest({
                idProperty: 'id',
                target: restURL
            });


            //create structure......
            var columns = [
                {id: 'id', field: 'id', name: 'id'},
                {id: 'firstname', field: 'firstName', name: 'Firstname'},
                {id: 'surname', field: 'surname', name: 'Surname'},
                {id: 'patronymic', field: 'patronymic', name: 'Patronymic'},
                {id: 'position', field: 'position', name: 'Position'}
            ];

            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                        label:"Create",
                iconClass:"dijitEditorIcon dijitEditorIconPaste",
                onClick:createNewTab
            });
            var editButton = new Button({
                label:"Edit",
                iconClass:"dijitIcon dijitIconEdit",
                onClick:edit
            });
            var deleteButton = new Button({
                label:"Delete",
                iconClass:"dijitEditorIcon dijitEditorIconDelete",
                onClick:deleteSelected
            });
            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
            toolbar.addChild(editButton);
            toolbar.startup();

            //Create grid widget.
            var grid = GridX({
                id: 'grid',
                cacheClass: Cache,
                store: store,
                structure: columns,
                barTop: [
                    toolbar
                ],
                modules: [
                    //Declare sort module in the "modules" parameter.
                    {
                        moduleClass: SingleSort,
                        //Declare initialOrder as module parameter:
                        initialOrder: { colId: 'firstname', descending: true }
                    },
                    Bar,
                    RowHeader,
                    Row,
                    IndirectSelect
                ]
            });
            grid.placeAt('gridContainer');

            grid.startup();

//            parser.parse();

            function deleteSelected() {
                // Get all selected items from the Grid:
                var items = grid.select.row.getSelected();
                if(items.length){
                    dojo.forEach(items, function(selectedItem){
                        if(selectedItem !== null){
                            grid.store.remove(selectedItem).then(success, error);
                            grid.model.clearCache();
                            grid.body.refresh();
                        }
                    });
//                    var args = {onError: function() {alert('error!');}};
//                    grid.store.add(args);

                }
            }

            function success() {
                console.log("succes");
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

            function edit() {

                var id;
                var items = grid.select.row.getSelected();
                if (items.length) {
                    dojo.forEach(items, function (selectedItem) {
                        if (selectedItem !== null) {
                            id = selectedItem;
                        }
                    });
                    xhr(restURL + "/" + id, {
                        handleAs: "json"
                    }).then(function (data) {
                        var widget = new formsWidget({model: data});
                        model = widget.get("model");
                        var tabContainer = Registry.byId("TabContainer");
                        var pane = new ContentPane({
                            title: "Person", content: widget, closable: true, onClose: function () {
                                return confirm("Do you really want to Close this?");
                            }
                        });
                        tabContainer.addChild(pane);
                        tabContainer.selectChild(pane);
                        parser.parse(Dom.byId("personDiv"));
                    });

                }
            }

            function createNewTab() {
                var widget = new formsWidget({ model:{firstName:"firstname", surname:"surname", patronymic: "patronymic"}});
                model = widget.get("model");
                var tabContainer = Registry.byId("TabContainer");
                var pane = new ContentPane({ title:"Person",  content: widget, closable: true, onClose: function(){
                    return confirm("Do you really want to Close this?");
                }});
                tabContainer.addChild(pane);
                tabContainer.selectChild(pane);
                parser.parse(Dom.byId("personDiv"));
            }

            function myButtonHandler() {
                console.log('Clicked button');
            }
        });
    </script>


</head>



<body class="claro">
<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'headline'" style="width: 100%; height:100%;">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'left', splitter: true">
        Sidebar content (left)
    </div>
    <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true" id="TabContainer">
        <div data-dojo-type="dijit/layout/ContentPane" title="Welcom">
            <div id='gridContainer'></div>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" title="tab #2">tab pane #2</div>
    </div>
</div>

</body>
</html>

<%--html, body {--%>
<%--height: 100%;--%>
<%--margin: 0;--%>
<%--overflow: hidden;--%>
<%--padding: 0;--%>
<%--}--%>