define([], function () {
    var Utils = {
        addTimezoneToDate: function (value) {
            if (typeof value != "string") {
                var v = value;
            }
            if (v) {
                v.setTime(v.getTime() - v.getTimezoneOffset() * 60 * 1000);
                value = v;
            }
            return value;
        }
    }
    return Utils;
})