package dao;

import java.util.List;
import model.Organization;
import model.SuperHero;
import model.SuperHeroOrganization;
public interface OrganizationDao {

    public Organization addOrganization(Organization organization);

    public void deleteOrganization(Organization organization);

    public void updateOrganization(Organization organization);

    public Organization getOrganizationById(int organizationId);

    public List<Organization> getAllOrganization();

    public void addHeroToOrganization(SuperHero hero, Organization organization);

    public void deleteHeroFromOrganization(SuperHero hero, Organization organization);

    public List<SuperHeroOrganization> getAllHerosAndOrganization();

    public void deleteHeroFromAllOrganizations(SuperHero hero);

    public void deleteOrganizationFromAllHeros(Organization organization);
}
