package ecm.model.documents_factory.util;

import ecm.dao.GenericDAO;
import ecm.dao.GenericDaoJpa;
import ecm.dao.MemoryStore;
import ecm.dao.PersonDaoJPA;
import ecm.util.exceptions.DocumentExistsException;
import ecm.model.Document;
import ecm.model.Person;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static ecm.web.StartClass.result;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class DocumentPopulator {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private static char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static char[] numbers = "0123456789".toCharArray();
    private static List<Person> persons = new ArrayList<Person>(){
        {
            add(new Person(getRandomInt(0,10000), "Petr", "Petrov", "Petrovich","manager"));
            add(new Person(getRandomInt(0,10000), "Ivan", "Ivanov", "Ivanovich","engineer"));
            add(new Person(getRandomInt(0,10000), "Ivan", "Petrov", "Vasilievich","clerk"));
            add(new Person(getRandomInt(0,10000), "Nikolay", "Nikolaev", "Nikolaevich","security"));
            add(new Person(getRandomInt(0,10000), "Vasya", "Vasiliev", "Vasilievich","tester"));
            add(new Person(getRandomInt(0,10000), "Petr", "Vasiliev", "Vasilievich","cleaner"));

        }

    };
    private static HashMap<String, Document> documents = new HashMap<>();

//    private static GenericDAO<Person> personDAO = new PersonDaoJPA();

    public static Document populateBasicsOfDocument(Document document) throws DocumentExistsException {
        document.setId(getRandomInt(0,10000));
        document.setRegNumber(getRandomRegNumber(5));
        documents.put(document.getRegNumber(), document);
        document.setName(getRandomString(10));
        document.setText(getRandomString(50));
        document.setDate(getRandomDate(LocalDate.of(2017, 1,1), LocalDate.of(2017, 1,31)));
        document.setAuthor(getRandomPerson());
        return document;
    }

    public static int getRandomInt(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt(min, max+1);
    }

    public static String getRandomString(int length){
        checkLength(length);

        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = chars[random.nextInt(chars.length)];
        }
        return new String(text);
    }

    public static LocalDate getRandomDate(LocalDate min, LocalDate max){
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        long minDay = min.toEpochDay();
        long maxDay = max.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }

    public static Person getRandomPerson(){
        List<Person> list = new ArrayList<>(result.keySet());
        if (list.size()!=0)
            return list.get(random.nextInt(MemoryStore.personStore.size()));
        else return null;
    }

    public static boolean getRandomBoolean(){
        return random.nextBoolean();
    }

    public static String getRandomRegNumber(int length) throws DocumentExistsException {
        checkLength(length);

        char[] text = new char[length];

        for (int i = 0; i < length; i++)
        {
            text[i] = numbers[random.nextInt(numbers.length)];
        }

        String regNumber = new String(text);
        checkNumber(regNumber);
        return regNumber;
    }

    private static void checkNumber(String regNumber) throws DocumentExistsException {
        if (documents.containsKey(regNumber)) throw new DocumentExistsException("Document with reg number "+regNumber+" already exist.");

    }

    private static void checkLength(int length){
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
    }
}
