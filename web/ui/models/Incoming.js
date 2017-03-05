/**
 * Модель для документа Incoming
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/Stateful",
    "myApp/ecm/ui/utils/Utils"
], function (declare, Stateful, Utils) {
    var Incoming = declare("Incoming", [Stateful], {
        date: null,
        _dateSetter: function (value) {
            this.date = Utils.addTimezoneToDate(value);
        },
        outboundRegDate: null,
        _outboundRegDateSetter: function (value) {
            this.outboundRegDate = Utils.addTimezoneToDate(value);
        }
    });
    /**
     * Статическое поле структуры грида
     */
    Incoming.columns = [
        {id: 'id', field: 'id', name: 'id', width: '5%'},
        {id: 'name', field: 'name', name: 'Name', width: '10%'},
        {id: 'author.fullname', field: 'authorName', name: 'Author', width: '15%'},
        {id: 'sender.fullname', field: 'senderName', name: 'Sender', width: '15%'},
        {id: 'recipient.fullname', field: 'recipientName', name: 'Recipient', width: '15%'},
        {id: 'regNumber', field: 'regNumber', name: 'Reg number', width: '5%'},
        {id: 'date', field: 'date', name: 'Date', width: '5%'},
        {id: 'referenceNumber', field: 'referenceNumber', name: 'Reference number', width: '5%'},
        {id: 'outboundRegDate', field: 'outboundRegDate', name: 'Outbound reg. date', width: '5%'},
        {id: 'text', field: 'text', name: 'Text', width: '20%'}
    ];
    Incoming.tableName = "Incomings";

    return Incoming;
})