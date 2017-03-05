/**
 * Добавляет колбеки модификации формы для инициализации специфичных для конкретной модели вещей.
 * @author dkarachurin
 */
define([
    "dojo/_base/declare"
], function (declare) {
    return declare([], {
        /**
         * Вызывается при создании формы
         * @param form Виджет общей формы
         */
        initForm: function (form) {
        },

        /**
         * Вызывается при сохранении новой формы
         * @param form Виджет общей формы
         */
        updateFormAfterSaveNew: function (form) {
        }
    });
});