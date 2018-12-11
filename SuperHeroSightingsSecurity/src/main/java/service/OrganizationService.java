package service;

import java.util.List;
import model.Organization;
import model.SuperHero;

public interface OrganizationService {

    public Organization addOrganization(Organization organization);

    public void deleteOrganization(Organization organization);

    public void updateOrganization(Organization organization);

    public Organization getOrganizationById(int organizationId);

    public List<Organization> getAllOrganization();

    public void deleteHeroFromAllOrganizations(SuperHero hero);

    public void deleteOrganizationFromAllHeros(Organization organization);

    public void deleteHeroFromOrganization(SuperHero hero,Organization organization);

    public void addHeroToOrganization(SuperHero hero,Organization organization);
}
