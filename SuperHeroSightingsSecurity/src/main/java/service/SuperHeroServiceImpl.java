package service;

import dao.SuperHeroDao;
import java.util.List;
import javax.inject.Inject;
import model.SuperHero;

public class SuperHeroServiceImpl implements SuperHeroService {

    SuperHeroDao dao;

    @Inject
    public SuperHeroServiceImpl(SuperHeroDao dao) {
        this.dao = dao;
    }

    @Override
    public SuperHero addHero(SuperHero hero) {
     return   dao.addHero(hero);
    }

    @Override
    public void deleteSuperHero(SuperHero hero) {
        dao.deleteSuperHero(hero);
    }

    @Override
    public void updateSuperHero(SuperHero hero) {
        dao.updateSuperHero(hero);
    }

    @Override
    public SuperHero getSuperHeroById(int SuperHeroId) {
        return dao.getSuperHeroById(SuperHeroId);
    }

    @Override
    public List<SuperHero> getAllSuperHeros() {
        return dao.getAllSuperHeros();
    }

    @Override
    public List<SuperHero> getAllSuperHerosByLocation(int locationId) {
        return dao.getAllSuperHerosByLocation(locationId);
    }

    @Override
    public List<SuperHero> getAllSuperHerosByOrganization(int organizationId) {
        return dao.getAllSuperHerosByOrganization(organizationId);
    }
}
