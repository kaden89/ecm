package dao;

import model.Document;

import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface DocumentDAO {
    List<Document> findAll();

    Document find(int id);

    void addDocument(Document document);

    void updateDocument(Document document);

    void deleteDocument(int id);
}
