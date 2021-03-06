/**
 * Наследованный от JsonRest модуль. Для переопределение метода который генерирует сортировочный GET запрос
 *  Чтобы вместо 'sort(+-fieldName)' был JSON 'sort={field: fieldName, direction=DESC/ASC}'
 * @author dkarachurin
 */
define(["dojo/store/JsonRest", "dojo/_base/declare", "dojo/_base/lang", "dojo/_base/xhr", "dojo/store/util/QueryResults"], function (JsonRest, declare, h, e, m) {
    return declare([JsonRest], {
        query: function (b, a) {
            a = a || {};
            var c = h.mixin({
                Accept: this.accepts
            }, this.headers, a.headers)
                , d = -1 < this.target.indexOf("?");
            b && "object" == typeof b && (b = (b = e.objectToQuery(b)) ? (d ? "\x26" : "?") + b : "");
            if (0 <= a.start || 0 <= a.count)
                c["X-Range"] = "items\x3d" + (a.start || "0") + "-" + ("count" in a && Infinity != a.count ? a.count + (a.start || 0) - 1 : ""),
                    this.rangeParam ? (b += (b || d ? "\x26" : "?") + this.rangeParam + "\x3d" + c["X-Range"],
                            d = !0) : c.Range = c["X-Range"];
            if (a && a.sort) {
                var k = this.sortParam;
                b += (b || d ? "\x26" : "?") + (k ? k + "\x3d" : "");
                //Изменения тут:
                for (d = 0; d < a.sort.length; d++) {
                    var f = a.sort[d];
                    var path;
                    if (f.hasOwnProperty("colId")){
                        path = encodeURIComponent(f.colId);
                    } else {
                        path = encodeURIComponent(f.attribute);
                    }

                    b += "sort={field: " + path + ", direction: " + (f.descending ? 'DESC' : 'ASC') + "}"
                }
            }
            var g = e("GET", {
                url: this.target + (b || ""),
                handleAs: "json",
                headers: c
            });
            g.total = g.then(function () {
                var a = g.ioArgs.xhr.getResponseHeader("Content-Range");
                a || (a = g.ioArgs.xhr.getResponseHeader("X-Content-Range"));
                return a && (a = a.match(/\/(.*)/)) && +a[1]
            });
            return m(g)
        }
    });
});