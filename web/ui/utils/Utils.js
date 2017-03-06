/**
 * Утильные функции
 * @author dkarachurin
 */
define([], function () {
    var Utils = {
        /**
         * Увеличивает дату на таймзону, для корректной обработки на бек-енде
         * @param value Дата
         * @returns Дата с добавленной таймзоной
         */
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