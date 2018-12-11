package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Sightings {

    private int sightingsId;
    private LocalDate sightingDate;
    private String sightingDateString;
    private String sightingTime;
    private Location location;
    private SuperHero superHero;

    public int getSightingsId() {
        return sightingsId;
    }

    public void setSightingsId(int sightingsId) {
        this.sightingsId = sightingsId;
    }

    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    public void setSightingDateString(String sightingDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.parse(sightingDateString, formatter);
        this.sightingDate = localDate;
    }

    public String getSightingTime() {
        return sightingTime;
    }

    public void setSightingTime(String sightingTime) {
        this.sightingTime = sightingTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public SuperHero getSuperHero() {
        return superHero;
    }

    public void setSuperHero(SuperHero superHero) {
        this.superHero = superHero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.sightingsId;
        hash = 41 * hash + Objects.hashCode(this.sightingDate);
        hash = 41 * hash + Objects.hashCode(this.sightingTime);
        hash = 41 * hash + Objects.hashCode(this.location);
        hash = 41 * hash + Objects.hashCode(this.superHero);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sightings other = (Sightings) obj;
        if (this.sightingsId != other.sightingsId) {
            return false;
        }
        if (!Objects.equals(this.sightingTime, other.sightingTime)) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.superHero, other.superHero)) {
            return false;
        }
        return true;
    }

}
