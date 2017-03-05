/**
 * Модель для документа Task
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/Stateful",
    "myApp/ecm/ui/utils/Utils",
    "myApp/ecm/ui/mixins/_FormAwareMixin"
], function (declare, Stateful, Utils, _FormAwareMixin) {
    var Task = declare("Task", [Stateful, _FormAwareMixin], {
        date: null,
        _dateSetter: function (value) {
            this.date = Utils.addTimezoneToDate(value);
        },
        dateOfIssue: null,
        _dateOfIssueSetter: function (value) {
            this.dateOfIssue = Utils.addTimezoneToDate(value);
        },
        deadline: null,
        _deadlineSetter: function (value) {
            this.deadline = Utils.addTimezoneToDate(value);
        },
        initForm: function (form) {
            form.author.store = form.personStore;
            form.executor.store = form.personStore;
            form.controller.store = form.personStore;

            form.author.value = this.positionId;
            form.executor.value = this.executorId;
            form.controller.value = this.controllerId;

            if (!form.isNew) {
                form.personStore.get(this.authorId).then(function (data) {
                    form.author.set('item', data);
                }.bind(this));
                form.personStore.get(this.executorId).then(function (data) {
                    form.executor.set('item', data);
                }.bind(this));
                form.personStore.get(this.controllerId).then(function (data) {
                    form.controller.set('item', data);
                }.bind(this));
            }
        }
    });
    //static field
    Task.columns = [
        {id: 'id', field: 'id', name: 'id', width: '5%'},
        {id: 'name', field: 'name', name: 'Name', width: '9.5%'},
        {id: 'author.fullname', field: 'authorName', name: 'Author', width: '13.5%'},
        {id: 'executor.fullname', field: 'executorName', name: 'Executor', width: '13.5%'},
        {id: 'controller.fullname', field: 'controllerName', name: 'Controller', width: '13.5%'},
        {id: 'isControlled', field: 'isControlled', name: 'Is controlled', width: '5%'},
        {id: 'regNumber', field: 'regNumber', name: 'Reg number', width: '5%'},
        {id: 'date', field: 'date', name: 'Date', width: '5%'},
        {id: 'dateOfIssue', field: 'dateOfIssue', name: 'Date of issue', width: '5%'},
        {id: 'deadline', field: 'deadline', name: 'Deadline', width: '5%'},
        {id: 'text', field: 'text', name: 'Text', width: '20%'}
    ];
    Task.tableName = "Tasks";

    return Task;
})