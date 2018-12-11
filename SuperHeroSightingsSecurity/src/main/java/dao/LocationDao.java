package dao;

import java.util.List;
import model.Location;
import model.SuperHero;

public interface LocationDao {

    public Location addLocation(Location location);

    public void deleteLocation(Location location);

    public void updateLocation(Location location);

    public Location getLocationById(int locationId);

    public List<Location> getAllLocations();

    public void deleteLocationFromAllSightings(Location location);

    public void deleteHeroFromASighting(SuperHero hero, Location location);

    public void deleteHeroFromAllLocations(SuperHero hero);

}
