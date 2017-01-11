package model.documents_factory.util;

import exceptions.DocumentExistsException;
import model.Document;
import model.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dkarachurin on 10.01.2017.
 */
public class DocumentPopulator {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private static char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static char[] numbers = "0123456789".toCharArray();
    private static List<Person> personList = new ArrayList<Person>(){
        {
            add(new Person("Petr", "Petrov", "Petrovich","manager"));
            add(new Person("Ivan", "Ivanov", "Ivanovich","engineer"));
            add(new Person("Ivan", "Petrov", "Vasilievich","clerk"));
            add(new Person("Nikolay", "Nikolaev", "Nikolaevich","security"));
            add(new Person("Vasya", "Vasiliev", "Vasilievich","tester"));
            add(new Person("Petr", "Vasiliev", "Vasilievich","cleaner"));

        }

    };
    private static HashMap<String, Document> documents = new HashMap<>();

    public static Document populateBasicsOfDocument(Document document){
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
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = chars[random.nextInt(chars.length)];
        }
        return new String(text);
    }

    public static LocalDate getRandomDate(LocalDate min, LocalDate max){
        long minDay = min.toEpochDay();
        long maxDay = max.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }

    public static Person getRandomPerson(){
        if (personList.size()!=0)
         return personList.get(random.nextInt(personList.size()));
        else return null;
    }

    public static boolean getRandomBoolean(){
        return random.nextBoolean();
    }

    public static String getRandomRegNumber(int length){

        char[] text = new char[length];

        for (int i = 0; i < length; i++)
        {
            text[i] = numbers[random.nextInt(numbers.length)];
        }

        String regNumber = new String(text);
        checkNumber(regNumber);
        return regNumber;
    }

    private static void checkNumber(String regNumber){
        if (documents.containsKey(regNumber)) throw new DocumentExistsException();
    }
}
