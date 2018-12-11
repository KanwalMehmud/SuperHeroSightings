package service;

import dao.SightingsDao;
import java.util.List;
import javax.inject.Inject;
import model.Sightings;

public class SightingsServiceImpl implements SightingsService {

    SightingsDao dao;

    @Inject
    public SightingsServiceImpl(SightingsDao dao) {
        this.dao = dao;
    }

    @Override
    public void addSighting(Sightings sightings) {
        dao.addSighting(sightings);
    }

    @Override
    public void deleteSighting(Sightings sightings) {
        dao.deleteSighting(sightings);
    }

    @Override
    public void updateSighting(Sightings sightings) {
        dao.updateSighting(sightings);
    }

    @Override
    public Sightings getSightingById(int sightingsId) {
        return dao.getSightingById(sightingsId);
    }

    @Override
    public List<Sightings> getAllSightings() {
        return dao.getAllSightings();
    }

    @Override
    public List<Sightings> getTenRecentSightings() {
        return dao.getTenRecentSightings();
    }
}
