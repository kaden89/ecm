package ecm.model.documents_factory;

import ecm.dao.GenericDAO;
import ecm.util.exceptions.DocumentExistsException;
import ecm.model.Document;
import ecm.model.Person;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dkarachurin on 10.01.2017.
 */
@Singleton
public class DocumentPopulator {
    @Inject
    private GenericDAO<Person> personDAO;

    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private char[] numbers = "0123456789".toCharArray();

    private HashMap<String, Document> documents = new HashMap<>();


    public Document populateBasicsOfDocument(Document document) throws DocumentExistsException {
        document.setId(null);
        document.setRegNumber(getRandomRegNumber(5));
        documents.put(document.getRegNumber(), document);
        document.setName(getRandomString(10));
        document.setText(getRandomString(50));
        document.setDate(getRandomDate(LocalDate.of(2017, 1, 1), LocalDate.of(2017, 1, 31)));
        document.setAuthor(getRandomPerson());
        return document;
    }

    public int getRandomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt(min, max + 1);
    }

    public String getRandomString(int length) {
        checkLength(length);

        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = chars[random.nextInt(chars.length)];
        }
        return new String(text);
    }

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

    public Person getRandomPerson() {
        List<Person> list = personDAO.findAll();
        if (list.size() != 0)
            return list.get(random.nextInt(list.size()));
        else return null;
    }

    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }

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

    private void checkNumber(String regNumber) throws DocumentExistsException {
        if (documents.containsKey(regNumber))
            throw new DocumentExistsException("Document with reg number " + regNumber + " already exist.");

    }

    private void checkLength(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
    }
}
