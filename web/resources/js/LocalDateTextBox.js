
define(["dojo/_base/declare", "dijit/form/DateTextBox", "dojo/date/stamp"], function(declare, DateTextBox, stamp){
    function isValidDate(value) {
        return value instanceof Date && isFinite(value.getTime());
    }
    function toUTCDate(value) {
        if (isValidDate(value)) {
            value = new Date(
                Date.UTC(value.getFullYear(), value.getMonth(), value.getDate())
            );
        }
        return value;
    }
    return declare(DateTextBox, {
        _getValueAttr : function() {
            return toUTCDate(this.inherited("_getValueAttr", arguments));
        }

        // _setValueAttr: function( value, priorityChange, formattedValue){
        //
        //     var v = stamp.fromISOString( value );
        //     if( v ){
        //         v.setTime( v.getTime() + v.getTimezoneOffset() * 60 * 1000 );
        //         value = v;
        //     }
        //
        //     this.inherited(arguments);
        // }
    });

    // return declare( 'LocalDateTextBox', [ DateTextBox ], {
    //     _getValueAttr: function(){
            // value = this.inherited("_getValueAttr", arguments);
            // // var _userOffset = _date.getTimezoneOffset()*60*1000; // user's offset time
            // // var _centralOffset = 6*60*60*1000; // 6 for central time - use whatever you need
            // // _date = new Date(_date.getTime() - _userOffset + _centralOffset); // redefine variable
            //
            // // var d = new Date(xiYear, xiMonth, xiDate);
            // // d.setTime( d.getTime() + d.getTimezoneOffset()*60*1000 );
            // value =  new Date(Date.UTC(value.getFullYear(), value.getMonth(), value.getDate()));
            // return value;
            // var ov = this.inherited(arguments);
            // if( ov ){
            //     ov.setTime( ov.getTime() - ov.getTimezoneOffset() * 60 * 1000 );
            // }
            // return ov;
        // }
        // ,

        // _setValueAttr: function( value, priorityChange, formattedValue){
        //
        //     var v = stamp.fromISOString(value);
        //      if( v ) {
        //          v.setTime(v.getTime() + v.getTimezoneOffset() * 60 * 1000);
        //          value = v;
        //      }
        //     else {
        //         var v = stamp.toISOString( value );
        //         value = v;
        //     }
        //
        //     this.inherited(arguments);
        // }
    // });
});

