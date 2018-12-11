package dao;

import java.util.List;
import model.SuperHero;

public interface SuperHeroDao {

    public SuperHero addHero(SuperHero hero);

    public void deleteSuperHero(SuperHero hero);

    public void updateSuperHero(SuperHero hero);

    public SuperHero getSuperHeroById(int SuperHeroId);

    public List<SuperHero> getAllSuperHeros();

    public List<SuperHero> getAllSuperHerosByLocation(int locationId);

    public List<SuperHero> getAllSuperHerosByOrganization(int organizationId);

}
