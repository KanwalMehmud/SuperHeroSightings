package service;

import dao.LocationDao;
import java.util.List;
import javax.inject.Inject;
import model.Location;
import model.SuperHero;

public class LocationServiceImpl implements LocationService {

    LocationDao dao;

    @Inject
    public LocationServiceImpl(LocationDao dao) {
        this.dao = dao;
    }

    @Override
    public void addLocation(Location location) {
        dao.addLocation(location);
    }

    @Override
    public void deleteLocation(Location location) {
        dao.deleteLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        dao.updateLocation(location);
    }

    @Override
    public Location getLocationById(int locationId) {
        return dao.getLocationById(locationId);
    }

    @Override
    public List<Location> getAllLocations() {
        return dao.getAllLocations();
    }

    @Override
    public void deleteLocationFromAllSightings(Location location) {
        dao.deleteLocationFromAllSightings(location);
    }

    @Override
    public void deleteHeroFromALocation(SuperHero hero, Location location) {
        dao.deleteHeroFromASighting(hero, location);
    }

    @Override
    public void deleteHeroFromAllLocations(SuperHero hero) {
       dao.deleteHeroFromAllLocations(hero);
    }

 

 

}
