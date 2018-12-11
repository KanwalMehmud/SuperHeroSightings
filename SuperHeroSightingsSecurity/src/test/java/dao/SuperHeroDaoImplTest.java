package dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Location;
import model.Organization;
import model.Sightings;
import model.SuperHero;
import model.SuperHeroOrganization;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SuperHeroDaoImplTest {
    
    SuperHeroDao superHeroDao;
    LocationDao locationDao;
    SightingsDao sightingsDao;
    OrganizationDao organizationDao;
    
    public SuperHeroDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
                ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        superHeroDao = ctx.getBean("SuperHeroDao", SuperHeroDao.class);
        locationDao = ctx.getBean("LocationDao", LocationDao.class);
        sightingsDao = ctx.getBean("SightingsDao", SightingsDao.class);
        organizationDao = ctx.getBean("OrganizationDao", OrganizationDao.class);

        List<SuperHero> sh = superHeroDao.getAllSuperHeros();
        List<Location> loc = locationDao.getAllLocations();
        List<Sightings> sightings = sightingsDao.getAllSightings();
        List<SuperHeroOrganization> organization = organizationDao.getAllHerosAndOrganization();
// delete records from organization bridge table
        sightings.forEach((s) -> {
            sightingsDao.deleteSighting(s);
        });
        loc.forEach((l) -> {
            locationDao.deleteLocation(l);
        });
// delete records from organization bridge table

        for (SuperHeroOrganization shOrg : organization) {
            SuperHero hero = superHeroDao.getSuperHeroById(shOrg.getSuperHeroId());
            Organization org = organizationDao.getOrganizationById(shOrg.getOrganizationId());

            organizationDao.deleteHeroFromOrganization(hero, org);
        }

        sh.forEach((c) -> {
            superHeroDao.deleteSuperHero(c);
        });
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addHero method, of class SuperHeroDaoImpl.
     */
    @Test
    public void testAddHero() {
        SuperHero hero1 = new SuperHero();
        hero1.setHeroName("SuperWoman");
        hero1.setSuperPower("flying");
        hero1.setDescription("SuperWoman is a hero who has the power to fly");
        
        SuperHero newHero = superHeroDao.addHero(hero1);
        
        assertNotNull(newHero.getSuperHeroId());
        assertEquals("SuperWoman", newHero.getHeroName());
        
    }

    /**
     * Test of deleteSuperHero method, of class SuperHeroDaoImpl.
     */
    @Test
    public void testDeleteSuperHero() {
        SuperHero hero1 = new SuperHero();
        hero1.setHeroName("SuperWoman");
        hero1.setSuperPower("flying");
        hero1.setDescription("SuperWoman is a hero who has the power to fly");
        
        SuperHero newHero = superHeroDao.addHero(hero1);
        superHeroDao.deleteSuperHero(newHero);
        
        SuperHero hero = superHeroDao.getSuperHeroById(newHero.getSuperHeroId());
        assertNull(hero);
    }

    /**
     * Test of updateSuperHero method, of class SuperHeroDaoImpl.
     */
    @Test
    public void testUpdateSuperHero() {
        SuperHero hero1 = new SuperHero();
        hero1.setHeroName("WonderWoman");
        hero1.setSuperPower("strength");
        hero1.setDescription("Wonder Woman is a hero who has super strength");
        superHeroDao.addHero(hero1);
        hero1.setDescription("WonderWoman has super strength");
        superHeroDao.updateSuperHero(hero1);
        SuperHero heroUpdated = superHeroDao.getSuperHeroById(hero1.getSuperHeroId());
        assertEquals(heroUpdated.getDescription(), "WonderWoman has super strength");
    }

    /**
     * Test of getSuperHeroById method, of class SuperHeroDaoImpl.
     */
    @Test
    public void testGetSuperHeroById() {
        SuperHero hero1 = new SuperHero();
        hero1.setHeroName("WonderWoman");
        hero1.setSuperPower("strength");
        hero1.setDescription("Wonder Woman is a hero who has super strength");
        superHeroDao.addHero(hero1);
        int heroId = hero1.getSuperHeroId();
        SuperHero heroUpdated = superHeroDao.getSuperHeroById(heroId);
        assertNotNull(heroUpdated);
    }

    /**
     * Test of getAllSuperHeros method, of class SuperHeroDaoImpl.
     */
    @Test
    public void testGetAllSuperHeros() {
        SuperHero hero1 = new SuperHero();
        hero1.setHeroName("WonderWoman");
        hero1.setSuperPower("strength");
        hero1.setDescription("Wonder Woman is a hero who has super strength");
        superHeroDao.addHero(hero1);
        SuperHero hero2 = new SuperHero();
        hero2.setHeroName("SuperWoman");
        hero2.setSuperPower("flying");
        hero2.setDescription("SuperWoman is a hero who has the power to fly");
        SuperHero newHero = superHeroDao.addHero(hero1);
        List<SuperHero> sh = superHeroDao.getAllSuperHeros();
        assertEquals(2, sh.size());
    }

    /**
     * Test of getAllSuperHerosByLocation method, of class SuperHeroDaoImpl.
     */
    @Test
    public void testGetAllSuperHerosByLocation() {
        SuperHero hero1 = new SuperHero();
        hero1.setHeroName("SuperWoman");
        hero1.setSuperPower("flying");
        hero1.setDescription("SuperWoman is a hero who has the power to fly");
        SuperHero newHero = superHeroDao.addHero(hero1);
        
        Location location = new Location();
        location.setAddress(" 1 address");
        location.setDescription("1 description");
        location.setLatitude(new BigDecimal("50"));
        location.setLongitude(new BigDecimal("50"));
        location.setLocationName("1 location Name");
        locationDao.addLocation(location);
        
        Sightings sighting = new Sightings();
        String str = "2015/03/15";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate sightingDate = LocalDate.parse(str, formatter);
        sighting.setSightingDate(sightingDate);
        sighting.setSightingTime("06:45:00");
        sighting.setSuperHero(newHero);
        sighting.setLocation(location);
        
        sightingsDao.addSighting(sighting);
        
        List<SuperHero> allHeros = superHeroDao.getAllSuperHerosByLocation(location.getLocationId());
        
        assertEquals(1, allHeros.size());
        assertEquals(hero1.getHeroName(), allHeros.get(0).getHeroName());
    }
    
}
