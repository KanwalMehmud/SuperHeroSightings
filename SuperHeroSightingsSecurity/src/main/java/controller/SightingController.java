package controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import model.Location;
import model.Sightings;
import model.SuperHero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.LocationService;
import service.OrganizationService;
import service.SightingsService;
import service.SuperHeroService;

@Controller
@RequestMapping(value = ("/sighting"))
public class SightingController {

    SuperHeroService heroService;
    LocationService locationService;
    OrganizationService orgService;
    SightingsService sightingsService;

    @Inject
    public SightingController(SuperHeroService heroService, LocationService locationService, OrganizationService orgService, SightingsService sightingsService) {
        this.heroService = heroService;
        this.locationService = locationService;
        this.orgService = orgService;
        this.sightingsService = sightingsService;
    }

    @RequestMapping(value = "/displaySightingPage", method = RequestMethod.GET)
    public String displaySightingPage(Model model) {
        List<Sightings> sightings = sightingsService.getAllSightings();
        List<SuperHero> heros = heroService.getAllSuperHeros();
        List<Location> locations = locationService.getAllLocations();

        model.addAttribute("sightingsList", sightings);
        model.addAttribute("herosList", heros);
        model.addAttribute("locationList", locations);

        return "sighting";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {

        String SightingIdParameter = request.getParameter("sightingsId");
        int sightingId = Integer.parseInt(SightingIdParameter);
        Sightings sighting = sightingsService.getSightingById(sightingId);
        sightingsService.deleteSighting(sighting);
        return "redirect:displaySightingPage";
    }

    @RequestMapping(value = "/displayTenRecentSightingPage", method = RequestMethod.GET)
    public String displayTenRecentSightingPage(Model model) {
        List<Sightings> sightings = sightingsService.getTenRecentSightings();
        model.addAttribute("tenRecentSightingsList", sightings);
        return "superHeroHomePage";
    }

    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(@ModelAttribute Sightings sightings) {
        sightingsService.addSighting(sightings);
        return "redirect:displaySightingPage";
    }
}
