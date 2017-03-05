package ecm.util.paging;

/**
 * Объектное представление range header приходящего с клиента для пейджинговых запросов
 * @author dkarachurin
 */
public class RangeHeader {
    private static final String RANGE_PREFIX = "items=";
    private Integer limit;
    private Integer offset;

    public RangeHeader(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public RangeHeader(String header) {
        String range = header.replaceAll(RANGE_PREFIX, "");
        String[] parsed = range.split("-");
        if (parsed.length == 2) {
            this.offset = new Integer(parsed[0]);
            this.limit = new Integer(parsed[1]) - offset;
            //Stub for gridx range format from filter window : 'items=0-' and other possible problems...
        } else {
            this.offset = 0;
            this.limit = 9999;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}