package dao;

import java.util.List;
import model.Sightings;

public interface SightingsDao {

    public void addSighting(Sightings sightings);

    public void deleteSighting(Sightings sightings);

    public void updateSighting(Sightings sightings);

    public Sightings getSightingById(int sightingsId);

    public List<Sightings> getAllSightings();

    public List<Sightings> getTenRecentSightings();
}
