package ecm.util.exceptions;

/**
 * Исключение выбрасываемое в случае генерации документов с одинаковыми рег. номерами
 * @author dkarachurin
 */
public class DocumentExistsException extends Exception {
    public DocumentExistsException(String message) {
        super(message);
    }
}
