define([
    "dojo/_base/declare",
    "dojo/Stateful",
    "myApp/ecm/ui/utils/Utils",
    "myApp/ecm/ui/mixins/_FormAwareMixin"
], function (declare, Stateful, Utils, _FormAwareMixin) {
    var Outgoing = declare("Outgoing", [Stateful, _FormAwareMixin], {
        date: null,
        _dateSetter: function (value) {
            this.date = Utils.addTimezoneToDate(value);
        },
        initForm: function (form) {
            form.author.store = form.personStore;
            form.recipient.store = form.personStore;

            form.author.value = this.positionId;
            form.recipient.value = this.recipientId;

            if (!form.isNew){
                form.personStore.get(this.authorId).then(function (data) {
                    form.author.set('item', data);
                }.bind(this));
                form.personStore.get(this.recipientId).then(function (data) {
                    form.recipient.set('item', data);
                }.bind(this));
            }
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