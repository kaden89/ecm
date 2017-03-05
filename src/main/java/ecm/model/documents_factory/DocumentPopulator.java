package ecm.model.documents_factory;

import ecm.dao.GenericDAO;
import ecm.model.Document;
import ecm.model.Person;
import ecm.util.exceptions.DocumentExistsException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Утильнай класс для заполнения полей документов рандомными значениями
 * @author dkarachurin
 */
@Singleton
public class DocumentPopulator {
    @Inject
    private GenericDAO<Person> personDAO;

    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private char[] numbers = "0123456789".toCharArray();

    private HashMap<String, Document> documents = new HashMap<>();


    /**
     * Заполняет общие для всех документов поля рандомными значениями
     * @param document Документ для заполнения
     * @return Документ с заполненными полями
     * @throws DocumentExistsException
     */
    public Document populateBasicsOfDocument(Document document) throws DocumentExistsException {
        document.setRegNumber(getRandomRegNumber(5));
        documents.put(document.getRegNumber(), document);
        document.setName(getRandomString(10));
        document.setText(getRandomString(50));
        document.setDate(getRandomDate(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 31)));
        document.setAuthor(getRandomPerson());
        return document;
    }

    /**
     * Генерирует рандомное число в диапозоне
     * @param min Минимальное значение
     * @param max Максимальное значение
     * @return Сгенерированное число
     */
    public int getRandomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt(min, max + 1);
    }

    /**
     * Генерирует рандомную строку
     * @param length Длинна нужной строки
     * @return Сгенерированная строка
     */
    public String getRandomString(int length) {
        checkLength(length);

        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars[random.nextInt(chars.length)];
        }
        return new String(text);
    }

    /**
     * Генерирует случайную дату в диапозоне
     * @param min Мин. дата
     * @param max Макс. дата
     * @return Сгенерированная дата
     */
    public LocalDate getRandomDate(LocalDate min, LocalDate max) {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        long minDay = min.toEpochDay();
        long maxDay = max.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }

    /**
     * Возвращает случайный объект класса {@link Person} из БД
     * @return случайный объект {@link Person}
     */
    public Person getRandomPerson() {
        List<Person> list = personDAO.findAll();
        if (list.size() != 0)
            return list.get(random.nextInt(list.size()));
        else return null;
    }

    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }

    /**
     * Генерирует случайное число
     * @param length Длинна числа
     * @return Сгенерированнео число
     * @throws DocumentExistsException В случае сли документ с таким рег. номером уже существует
     */
    public String getRandomRegNumber(int length) throws DocumentExistsException {
        checkLength(length);

        char[] text = new char[length];

        for (int i = 0; i < length; i++) {
            text[i] = numbers[random.nextInt(numbers.length)];
        }

        String regNumber = new String(text);
        checkNumber(regNumber);
        return regNumber;
    }

    /**
     * Проверяет уникальность RegNumber
     * @param regNumber рег. номер
     * @throws DocumentExistsException
     */
    private void checkNumber(String regNumber) throws DocumentExistsException {
        if (documents.containsKey(regNumber))
            throw new DocumentExistsException("Document with reg number " + regNumber + " already exist.");

    }

    /**
     * Проверяет валидность длинны
     * @param length Длинна
     */
    private void checkLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
    }
}
