package dao;

import model.Organization;

import java.util.List;

/**
 * Created by dkarachurin on 12.01.2017.
 */
public interface OrganizationDAO {
    List<Organization> findAll();

    Organization find(int id);

    void addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(int id);
}
