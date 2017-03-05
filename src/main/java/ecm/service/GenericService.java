package ecm.service;

import ecm.dao.GenericDAO;
import ecm.util.filtering.Filter;
import ecm.util.paging.Page;
import ecm.util.paging.RangeHeader;
import ecm.util.sorting.Sort;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Базовый generic интерфейс слоя сервисов, предоставляющих общие методы доступа к DAO.
 * @author dkarachurin
 */

public interface GenericService<T> {

    /**
     * Поиск элемента по id
     * @param id ID объекта
     * @return Найденный объект
     * @throws NotFoundException В случае если объект не найден
     */
    T find(int id) throws NotFoundException;

    /**
     * Находит все записи
     * @return Все записи
     */
    List<T> findAll();

    /**
     * Поиск записей в БД в нужном диапозоне, их сортировка
     * @param sort Объект класса {@link Sort} содержащий данные для cортировки
     * @param range Объект класса {@link RangeHeader} содержащий данные о диапозоне запрашиваемых данных
     * @return Объект класса {@link Page}
     */
    Page<T> findAllSortedAndPageable(Sort sort, RangeHeader range) ;

    /**
     * Поиск записей в БД в нужном диапозоне, с необходимой фильтрацией и сортировкой
     * @param sort Объект класса {@link Sort} содержащий данные для cортировки
     * @param range Объект класса {@link RangeHeader} содержащий данные о диапозоне запрашиваемых данных
     * @param filter Объект класса {@link Filter} содержащий данные о фильтрации
     * @return Объект класса {@link Page}
     */
    Page<T> findAllSortedFilteredAndPageable(Sort sort, Filter filter, RangeHeader range);

    /**
     * Сохраняет нвоый объект в БД
     * @param newInstance новый объект
     * @return Сохраненный объект
     */
    T save(T newInstance);

    /**
     * Обновляет объект в БД
     * @param updateInstance Обновляемый объект
     * @return Обновленный объект
     */
    T update(T updateInstance);

    /**
     * Удаляет объект по его ID
     * @param id ID объекта
     * @throws NotFoundException В случае если объект не найден
     */
    void delete(int id) throws NotFoundException;

    /**
     * Удаляетв все объекты типа T из БД
     */
    void deleteAll();

    GenericDAO<T> getGenericDao();
}
