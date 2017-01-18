
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
            "dijit/registry",
            "dojo/query",
            /*'dojo/store/Memory',*/
            "dojo/domReady!"], function(declare, TabContainer, ContentPane, GridX, Dod, Cache, Deferred, QueryResults,Memory,SingleSort, JsonRest, Bar, Toolbar, Button,
                                        RowHeader, Row, IndirectSelect, Registry,query) {

            var restURL = 'http://localhost:8080/ecm/rest/employees';
            var store = new JsonRest({
                idProperty: 'id',
                target: restURL
            });


            //create structure......
            var columns = [
                {id: 'firstname', field: 'firstName', name: 'Firstname'},
                {id: 'surname', field: 'surname', name: 'Surname'},
                {id: 'patronymic', field: 'patronymic', name: 'Patronymic'}
            ];

            var toolbar = new Toolbar({}, "toolbar");
            var createButton = new Button({
                        label:"Create",
                iconClass:"dijitEditorIcon dijitEditorIconPaste",
                onClick:myButtonHandler
            });
            var deleteButton = new Button({
                label:"Delete",
                iconClass:"dijitEditorIcon dijitEditorIconDelete",
                onClick:deleteSelected
            });
            toolbar.addChild(createButton);
            toolbar.addChild(deleteButton);
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


            function deleteSelected() {
                // Get all selected items from the Grid:
                var items = grid.select.row.getSelected();
                if(items.length){
                    dojo.forEach(items, function(selectedItem){
                        if(selectedItem !== null){
                            grid.store.remove(selectedItem);
                        }
                    });
                    var args = {onError: function() {alert('error!');}};
                    grid.store.add(args);
                }
            }

        });


        function myButtonHandler() {
            console.log('Clicked button');
        }
    </script>


</head>



<body class="claro">

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'headline'" style="width: 100%; height:100%;">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'left', splitter: true">
        Sidebar content (left)
    </div>
    <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true">
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