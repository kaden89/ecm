<%--
  Created by IntelliJ IDEA.
  User: dkarachurin
  Date: 12.01.2017
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html >
<head>

  <%--<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.4/dijit/themes/claro/claro.css">--%>
  <link rel="stylesheet" href="/webjars/dojo/1.10.4/dojo/dojo.js">
  <%--<link rel="stylesheet" href="dgrid/css/dgrid.css">--%>
  <style type="text/css">
    html, body {
      width: 100%;
      height: 100%;
      margin: 0;
      overflow:hidden;
    }

    #borderContainerTwo {
      width: 100%;
      height: 100%;
    }

  </style>
  <script>dojoConfig = {
      parseOnLoad: true,
     packages: ['dojo', 'dijit', 'dgrid']
  }
  </script>
  <%--<script src='http://ajax.googleapis.com/ajax/libs/dojo/1.10.4/dojo/dojo.js'></script>--%>

  <script>
      require(["dojo/parser", "dijit/layout/ContentPane", "dijit/layout/BorderContainer", "dijit/layout/TabContainer", "dijit/layout/AccordionContainer", "dijit/layout/AccordionPane"]);
  </script>

  <%--<script>--%>
      <%--require([--%>
          <%--"dojo/_base/lang",--%>
          <%--"dgrid/List",--%>
          <%--"dgrid/OnDemandGrid",--%>
          <%--"dgrid/Selection",--%>
          <%--"dgrid/Editor",--%>
          <%--"dgrid/Keyboard",--%>
          <%--"dgrid/Tree",--%>
          <%--"dojo/_base/declare",--%>
          <%--"dojo/query",--%>
          <%--"dstore/Rest",--%>
          <%--"dstore/Trackable",--%>
          <%--"dstore/Cache",--%>
          <%--"dstore/Tree",--%>
          <%--"dojo/domReady!"--%>
      <%--], function (lang, List, Grid, Selection, Editor, Keyboard, Tree, declare, query, Rest, Trackable, Cache, TreeStore) {--%>
          <%--var CustomGrid = declare([Grid, Selection, Keyboard, Editor, Tree], {--%>
              <%--insertRow: function () {--%>
                  <%--refreshed = true;--%>
                  <%--return this.inherited(arguments);--%>
              <%--},--%>
              <%--removeRow: function () {--%>
                  <%--refreshed = true;--%>
                  <%--return this.inherited(arguments);--%>
              <%--},--%>
              <%--logPreload: function () {--%>
                  <%--var line = '';--%>
                  <%--for (var i = 0; i < 160; i++) {--%>
                      <%--line += '\u2500';--%>
                  <%--}--%>
                  <%--console.log(line);--%>
                  <%--var preload = this.preload;--%>
                  <%--if (preload) {--%>
                      <%--while (preload.previous) {--%>
                          <%--preload = preload.previous;--%>
                      <%--}--%>
                      <%--var preloads = [];--%>
                      <%--var preloadNodes = [];--%>
                      <%--var height = 0;--%>
                      <%--var totalPossibleRows = 0;--%>
                      <%--while (preload) {--%>
                          <%--preloads.push(preload);--%>
                          <%--var node = preload.node;--%>
                          <%--height += node.offsetHeight;--%>
                          <%--totalPossibleRows += preload.count;--%>
                          <%--preloadNodes.push({--%>
                              <%--preloadId: node.getAttribute('data-preloadid'),--%>
                              <%--rowIndex: node.rowIndex,--%>
                              <%--height: node.style.height,--%>
                              <%--offsetTop: node.offsetTop,--%>
                              <%--offsetHeight: node.offsetHeight--%>
                          <%--});--%>
                          <%--preload = preload.next;--%>
                      <%--}--%>
                      <%--console.table(preloads);--%>
                      <%--console.table(preloadNodes);--%>
                      <%--var realRowCount = query('.dgrid-row', grid.contentNode).length;--%>
                      <%--height += 24 * realRowCount;--%>
                      <%--totalPossibleRows += realRowCount;--%>
                      <%--console.log('Height calculated from preloads = ', height);--%>
                      <%--console.log('Actual grid content height = ', gridContentHeight());--%>
                      <%--console.log('Total possible rows = ', totalPossibleRows);--%>
                      <%--console.log('Current row count = ', realRowCount);--%>
                  <%--}--%>
              <%--}--%>
          <%--});--%>
          <%--function createStore(config) {--%>
              <%--var store = new declare([Rest, Trackable, Cache, TreeStore])(lang.mixin({--%>
                  <%--target: "/rest/employees",--%>
                  <%--put: function (object) {--%>
                      <%--return object;--%>
                  <%--}--%>
              <%--}, config));--%>
              <%--store.getRootCollection = function () {--%>
                  <%--return this.root.filter({ parent: undefined });--%>
              <%--};--%>
              <%--return store;--%>
          <%--}--%>
          <%--function getColumns() {--%>
              <%--return [--%>
                  <%--{ label: 'Name', field: 'name', sortable: false, renderExpando: true },--%>
                  <%--{ label: 'Id', field: 'id' }--%>
<%--//                  { label: 'Comment', field: 'comment', sortable: false, editor: "text" },--%>
<%--//                  { label: 'Boolean', field: 'boo', sortable: false, autoSave: true, editor: "checkbox" }--%>
              <%--];--%>
          <%--}--%>
          <%--window.grid = new CustomGrid({--%>
              <%--pagingMethod: 'throttleDelayed',--%>
              <%--collection: createStore().getRootCollection(),--%>
              <%--sort: "id",--%>
              <%--getBeforePut: false,--%>
              <%--columns: getColumns()--%>
          <%--}, "grid");--%>
          <%--var refreshed = false;--%>
          <%--var prevContentHeight = 0;--%>
          <%--setInterval(function () {--%>
              <%--var contentHeight = gridContentHeight();--%>
              <%--if (refreshed || prevContentHeight != contentHeight) {--%>
                  <%--prevContentHeight = contentHeight;--%>
                  <%--refreshed = false;--%>
                  <%--grid.logPreload();--%>
              <%--}--%>
          <%--}, 1500);--%>
          <%--function gridContentHeight() {--%>
              <%--var contentHeight = 0;--%>
              <%--var childNodes = grid.contentNode.childNodes;--%>
              <%--var len = childNodes.length;--%>
              <%--for (var i = 0; i < len; i++) {--%>
                  <%--contentHeight += childNodes[i].offsetHeight;--%>
              <%--}--%>
              <%--return contentHeight;--%>
          <%--}--%>
          <%--new CustomGrid({--%>
              <%--collection: createStore({ useRangeHeaders: true }).getRootCollection(),--%>
              <%--sort: "id",--%>
              <%--getBeforePut: false,--%>
              <%--columns: getColumns()--%>
          <%--}, "gridRangeHeaders");--%>
      <%--});--%>
  <%--</script>--%>
</head>


<body class="claro">
<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="gutters:true, liveSplitters:false" id="borderContainerTwo">
  <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top', splitter:false">
    Hi, usually here you would have important information, maybe your company logo, or functions you need to access all the time..
  </div>
  <div data-dojo-type="dijit/layout/AccordionContainer" data-dojo-props="minSize:20, region:'leading', splitter:true" style="width: 300px;" id="leftAccordion">
    <div data-dojo-type="dijit/layout/AccordionPane" title="One fancy Pane">
      <div id="mainMenu" data-dojo-type="dijit/Menu">
        <div id="edit" data-dojo-type="dijit/MenuItem">Edit</div>
        <div id="view" data-dojo-type="dijit/MenuItem">View</div>
        <div id="task" data-dojo-type="dijit/PopupMenuItem">
          <span>Task</span>
          <div id="taskMenu" data-dojo-type="dijit/Menu">
            <div id="complete" data-dojo-type="dijit/MenuItem">
              Mark as Complete
            </div>
            <div id="cancel" data-dojo-type="dijit/MenuItem">
              Cancel
            </div>
            <div id="begin" data-dojo-type="dijit/MenuItem">
              Begin
            </div>
          </div>
        </div><!-- end of sub-menu -->
      </div>
    </div>
    <div data-dojo-type="dijit/layout/AccordionPane" title="Another one">
    </div>
    <div data-dojo-type="dijit/layout/AccordionPane" title="Even more fancy" selected="true">
    </div>
    <div data-dojo-type="dijit/layout/AccordionPane" title="Last, but not least">
    </div>
  </div><!-- end AccordionContainer -->
  <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true">
    <div data-dojo-type="dijit/layout/ContentPane" title="My first tab" selected="true">
      <div id="grid"></div>
      <script>
          require([ 'dgrid/Grid', 'dojo/domReady!' ], function (Grid) {
              var data = [
                  { first: 'Bob', last: 'Barker', age: 89 },
                  { first: 'Vanna', last: 'White', age: 55 },
                  { first: 'Pat', last: 'Sajak', age: 65 }
              ];

              var grid = new Grid({
                  columns: {
                      first: 'First Name',
                      last: 'Last Name',
                      age: 'Age'
                  }
              }, 'grid');
              grid.renderArray(data);
          });
      </script>
    </div>
    <div data-dojo-type="dijit/layout/ContentPane" title="My second tab">
      Lorem ipsum and all around - second...
    </div>
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="closable:true" title="My last tab">
      Lorem ipsum and all around - last...
    </div>
  </div><!-- end TabContainer -->
</div><!-- end BorderContainer -->
</body>
</html>

