package service;

import java.util.List;
import model.Location;
import model.SuperHero;

public interface LocationService {

    public void addLocation(Location location);

    public void deleteLocation(Location location);

    public void updateLocation(Location location);

    public Location getLocationById(int locationId);

    public List<Location> getAllLocations();

    public void deleteLocationFromAllSightings(Location location);

    public void deleteHeroFromALocation(SuperHero hero, Location location);

    public void deleteHeroFromAllLocations(SuperHero hero);

}
