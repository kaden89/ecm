package ecm.dao;

import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import java.util.List;

/**
 * Базовый generic интерфейс DAO, предоставляющих общие методы доступа к данным.
 * @see AbstractGenericDaoJpaImpl
 * @author dkarachurin
 */
public interface GenericDAO<T> {
    /**
     * Поиск всех записей
     * @return список найденных записей
     */
    List<T> findAll();
    /**
     * Поиск записи по ID
     * @param id идентификатор записи
     * @return найденная запись (null если не найдено)
     */
    T find(int id);
    /**
     * Сохранение объекта в БД
     * @param entity Объект для записи
     * @return Сохраненный объект
     */
    T save(T entity);
    /**
     * Обновление объекта в БД
     * @param entity Объект для обновления
     * @return Обновленный объект
     */
    T update(T entity);
    /**
     * Удаление объекта из БД
     * @param entity Объект для удаления
     */
    void delete(T entity);
    /**
     * Удаление всех объектов из БД
     */
    void deleteAll();
    /**
     * Поиск записей в БД в нужном диапозоне, их сортировка
     * @param sort Объект класса {@link Sort} содержащий данные для cортировки
     * @param range Объект класса {@link RangeHeader} содержащий данные о диапозоне запрашиваемых данных
     * @return Объект класса {@link Page}
     */
    Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range);
    /**
     * Поиск записей в БД в нужном диапозоне, с необходимой фильтрацией и сортировкой
     * @param sort Объект класса {@link Sort} содержащий данные для cортировки
     * @param range Объект класса {@link RangeHeader} содержащий данные о диапозоне запрашиваемых данных
     * @param filter Объект класса {@link Filter} содержащий данные о фильтрации
     * @return Объект класса {@link Page}
     */
    Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range);
}
