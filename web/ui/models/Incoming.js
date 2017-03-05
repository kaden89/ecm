/**
 * Модель для документа Incoming
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/Stateful",
    "myApp/ecm/ui/utils/Utils",
    "myApp/ecm/ui/mixins/_FormAwareMixin"
], function (declare, Stateful, Utils, _FormAwareMixin) {
    var Incoming = declare("Incoming", [Stateful, _FormAwareMixin], {
        date: null,
        _dateSetter: function (value) {
            this.date = Utils.addTimezoneToDate(value);
        },
        outboundRegDate: null,
        _outboundRegDateSetter: function (value) {
            this.outboundRegDate = Utils.addTimezoneToDate(value);
        },
        initForm: function (form) {
            form.author.store = form.personStore;
            form.sender.store = form.personStore;
            form.recipient.store = form.personStore;

            form.author.value = this.authorId;
            form.sender.value = this.senderId;
            form.recipient.value = this.recipientId;

            if (!form.isNew) {
                form.personStore.get(this.authorId).then(function (data) {
                    form.author.set('item', data);
                }.bind(this));
                form.personStore.get(this.senderId).then(function (data) {
                    form.sender.set('item', data);
                }.bind(this));
                form.personStore.get(this.recipientId).then(function (data) {
                    form.recipient.set('item', data);
                }.bind(this));
            }
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