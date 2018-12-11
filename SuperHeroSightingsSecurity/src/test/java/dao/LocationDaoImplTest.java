package dao;

import java.math.BigDecimal;
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

public class LocationDaoImplTest {

    SuperHeroDao superHeroDao;
    LocationDao locationDao;
    SightingsDao sightingsDao;
    OrganizationDao organizationDao;

    public LocationDaoImplTest() {
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

    @Test
    public void testAddLocation() {

        Location location = new Location();
        location.setAddress(" 1 address");
        location.setDescription("1 description");
        location.setLatitude(new BigDecimal("50"));
        location.setLongitude(new BigDecimal("50"));
        location.setLocationName("1 location Name");
        Location newLocation = locationDao.addLocation(location);
        assertNotNull(newLocation.getLocationId());

    }

    @Test
    public void testDeleteLocation_GetLocationByID() {
        Location location = new Location();
        location.setAddress(" 1 address");
        location.setDescription("1 description");
        location.setLatitude(new BigDecimal("50"));
        location.setLongitude(new BigDecimal("50"));
        location.setLocationName("1 location Name");
        
        Location newLocation = locationDao.addLocation(location);
        
        locationDao.deleteLocation(newLocation);
        
        Location location2 = locationDao.getLocationById(newLocation.getLocationId());
        
        assertNull(location2);
    }

    @Test
    public void testGetAllLocations() {
        Location location = new Location();
        location.setAddress(" 1 address");
        location.setDescription("1 description");
        location.setLatitude(new BigDecimal("50"));
        location.setLongitude(new BigDecimal("50"));
        location.setLocationName("1 location Name");

        locationDao.addLocation(location);

        Location location2 = new Location();
        location2.setAddress(" 1 address");
        location2.setDescription("1 description");
        location2.setLatitude(new BigDecimal("50"));
        location2.setLongitude(new BigDecimal("50"));
        location2.setLocationName("1 location Name");

        locationDao.addLocation(location2);

        List<Location> allLocations = locationDao.getAllLocations();

        assertEquals(2, allLocations.size());

    }

}
