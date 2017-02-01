
define(["dojo/_base/declare", "dijit/form/DateTextBox", "dojo/date/stamp"], function(declare, DateTextBox, stamp){
    return declare( 'LocalDateTextBox', [ DateTextBox ], {
        _getValueAttr: function(){
            value = this.inherited("_getValueAttr", arguments);
            return new Date(Date.UTC(value.getFullYear(), value.getMonth(), value.getDate()));
            }
        ,

        _setValueAttr: function( value, priorityChange, formattedValue){
            var v = stamp.fromISOString( value );
            if( v ){
                v.setTime( v.getTime() + v.getTimezoneOffset() * 60 * 1000 );
                value = v;
            }


            this.inherited(arguments);
        }
    });
});

