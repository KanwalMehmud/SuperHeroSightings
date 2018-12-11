package service;

import dao.OrganizationDao;
import java.util.List;
import javax.inject.Inject;
import model.Organization;
import model.SuperHero;

public class OrganizationServiceImpl implements OrganizationService {

    OrganizationDao dao;

    @Inject
    public OrganizationServiceImpl(OrganizationDao dao) {
        this.dao = dao;
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return dao.addOrganization(organization);
    }

    @Override
    public void deleteOrganization(Organization organization) {
        dao.deleteOrganization(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        dao.updateOrganization(organization);
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        return dao.getOrganizationById(organizationId);
    }

    @Override
    public List<Organization> getAllOrganization() {
        return dao.getAllOrganization();
    }

    @Override
    public void deleteHeroFromAllOrganizations(SuperHero hero) {
        dao.deleteHeroFromAllOrganizations(hero);
    }

    @Override
    public void deleteOrganizationFromAllHeros(Organization organization) {
        dao.deleteOrganizationFromAllHeros(organization);
    }

    @Override
    public void deleteHeroFromOrganization(SuperHero hero,Organization organization) {
        dao.deleteHeroFromOrganization(hero, organization);
    }

    @Override
    public void addHeroToOrganization(SuperHero hero,Organization organization) {
        dao.addHeroToOrganization(hero, organization);
    }
}
