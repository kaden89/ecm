<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <script type='text/javascript' src='//ajax.googleapis.com/ajax/libs/dojo/1.10.1/dojo/dojo.js'
            data-dojo-config="async: true, parseOnLoad: true"></script>
    <link rel="stylesheet" type="text/css"
          href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.1/dijit/themes/claro/claro.css">
    <link rel="stylesheet" type="text/css" href="resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="http://cdn.rawgit.com/oria/gridx/1.3/resources/claro/Gridx.css">

    <link rel="stylesheet" type="text/css"
          href="http://ajax.googleapis.com/ajax/libs/dojo/1.10.1/dijit/themes/claro/document.css">

    <script type='text/javascript' src="resources/js/main.js"></script>


</head>


<body class="claro">
<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'headline'"
     style="width: 100%; height:100%;">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'left', splitter: true" style="width: 15%">

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 35%">
            <div id="personTree"></div>
        </div>
        <div data-dojo-type="dijit/layout/ContentPane" style="height: 60%">
            <div id="documentsTree"></div>
        </div>
    </div>
    <div data-dojo-type="dijit/layout/TabContainer" data-dojo-props="region:'center', tabStrip:true" id="TabContainer">
        <div data-dojo-type="dijit/layout/ContentPane" title="Welcom" id="WelcomPane">
            <div id='gridContainer'></div>
        </div>
    </div>
</div>

</body>
</html>
