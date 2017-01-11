import model.Document;
import model.Person;
import model.documents_factory.DocumentsFactory;
import model.documents_factory.FactoryEnum;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static model.documents_factory.FactoryEnum.*;


public class Main {
    private static DocumentsFactory factory = DocumentsFactory.INSTANCE;
    private static TreeMap<Person, ArrayList<Document>> result = new TreeMap<>();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        for (int i = 0; i < 10; i++) {
            createDocument(INCOMING);
            createDocument(OUTGOING);
            createDocument(TASK);
        }

        for (Map.Entry<Person, ArrayList<Document>> entry : result.entrySet()) {
            System.out.println(entry.getKey());
            ArrayList<Document> documents = entry.getValue();
            for (Document document : documents) {
                System.out.println("  "+document);
            }
        }
    }

    public static void createDocument(FactoryEnum factoryEnum) throws IllegalAccessException, InstantiationException {
       Document document = factory.createDocument(factoryEnum);
       Person author = document.getAuthor();
       if (result.containsKey(author)){
           result.get(author).add(document);
       }
       else {
           result.put(author, new ArrayList<>());
       }
    }
}
