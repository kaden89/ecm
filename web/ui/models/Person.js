/**
 * Модель для сотрудников Person
 * @author dkarachurin
 */
define([
    "dojo/_base/declare",
    "dojo/Stateful",
    "myApp/ecm/ui/utils/Utils"
], function (declare,
             Stateful,
             Utils) {
    var Person = declare("Person", [Stateful], {
        birthday: null,
        _birthdaySetter: function (value) {
            this.birthday = Utils.addTimezoneToDate(value);
        }
    });
    //static field
    Person.columns = [
        {id: 'id', field: 'id', name: 'id', width: '5%'},
        {id: 'firstname', field: 'firstname', name: 'Firstname', width: '19%'},
        {id: 'surname', field: 'surname', name: 'Surname', width: '19%'},
        {id: 'patronymic', field: 'patronymic', name: 'Patronymic', width: '19%'},
        {id: 'position.post', field: 'positionName', name: 'Position', width: '19%'},
        {id: 'birthday', field: 'birthday', name: 'Birthday', width: '19%'}
    ];
    Person.tableName = "Persons";

    return Person;
})