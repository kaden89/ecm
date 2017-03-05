/**
 * Модель для документа Outgoing
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/Stateful",
    "myApp/ecm/ui/utils/Utils"
], function (declare, Stateful, Utils) {
    var Outgoing = declare("Outgoing", [Stateful], {
        date: null,
        _dateSetter: function (value) {
            this.date = Utils.addTimezoneToDate(value);
        }
    });
    //static field
    Outgoing.columns = [
        {id: 'id', field: 'id', name: 'id', width: '5%'},
        {id: 'name', field: 'name', name: 'Name'},
        {id: 'author.fullname', field: 'authorName', name: 'Author'},
        {id: 'recipient.fullname', field: 'recipientName', name: 'Recipient'},
        {id: 'deliveryMethod', field: 'deliveryMethod', name: 'Delivery method'},
        {id: 'regNumber', field: 'regNumber', name: 'Reg number'},
        {id: 'date', field: 'date', name: 'Date'},
        {id: 'text', field: 'text', name: 'Text'}
    ];
    Outgoing.tableName = "Outgoings";

    return Outgoing;
})