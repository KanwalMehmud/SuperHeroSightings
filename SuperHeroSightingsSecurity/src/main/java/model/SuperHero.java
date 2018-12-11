
package model;

import java.util.Objects;
import org.hibernate.validator.constraints.NotEmpty;

public class SuperHero {

    private int superHeroId;

    @NotEmpty(message = "Hero name cannot be empty")
    private String heroName;
    @NotEmpty(message = "Super power cannot be empty")
    private String superPower;
    @NotEmpty(message = "Description cannot be empty")
    private String description;

    public int getSuperHeroId() {
        return superHeroId;
    }

    public void setSuperHeroId(int superHeroId) {
        this.superHeroId = superHeroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.superHeroId;
        hash = 41 * hash + Objects.hashCode(this.heroName);
        hash = 41 * hash + Objects.hashCode(this.superPower);
        hash = 41 * hash + Objects.hashCode(this.description);
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
        final SuperHero other = (SuperHero) obj;
        if (this.superHeroId != other.superHeroId) {
            return false;
        }
        if (!Objects.equals(this.heroName, other.heroName)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

}
