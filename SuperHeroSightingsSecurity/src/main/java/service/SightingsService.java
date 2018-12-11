package service;

import java.util.List;
import model.Sightings;

public interface SightingsService {

    public void addSighting(Sightings sightings);

    public void deleteSighting(Sightings sightings);

    public void updateSighting(Sightings sightings);

    public Sightings getSightingById(int sightingsId);

    public List<Sightings> getAllSightings();

    public List<Sightings> getTenRecentSightings();

}
