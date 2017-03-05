package ecm.util.paging;

import java.util.List;

/**
 * Класс представляет собой представление страницы объектов полученных из базы с помощью пейджинга.
 * Включает в себя список элементов и значения диапазона этих элементов.
 * @author dkarachurin
 */
public class Page<T> {
    List<T> items;
    private int startIndex;
    private int endIndex;
    private int allItemsCount;

    public Page(List<T> items, int startIndex, int endIndex, int allItemsCount) {
        this.items = items;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.allItemsCount = allItemsCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getAllItemsCount() {
        return allItemsCount;
    }

    public void setAllItemsCount(int allItemsCount) {
        this.allItemsCount = allItemsCount;
    }
}
