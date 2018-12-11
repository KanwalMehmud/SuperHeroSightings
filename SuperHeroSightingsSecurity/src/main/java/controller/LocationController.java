package controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import model.Location;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.LocationService;
import service.OrganizationService;
import service.SuperHeroService;

@Controller
@RequestMapping(value = ("/location"))
public class LocationController {

    SuperHeroService heroService;
    LocationService locationService;
    OrganizationService orgService;

    @Inject
    public LocationController(SuperHeroService heroService, LocationService locationService, OrganizationService orgService) {
        this.heroService = heroService;
        this.locationService = locationService;
        this.orgService = orgService;
    }

    @RequestMapping(value = "/displayLocationsPage", method = RequestMethod.GET)
    public String displayLocationsPage(Model model) {
        List<Location> locationList = locationService.getAllLocations();
        model.addAttribute("locationList", locationList);
        return "location";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(@ModelAttribute Location location) {
        locationService.addLocation(location);
        return "redirect:displayLocationsPage";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        Location location = new Location();
        location.setLocationId(locationId);
        locationService.deleteLocationFromAllSightings(location);
        locationService.deleteLocation(location);
        return "redirect:displayLocationsPage";
    }

    @RequestMapping(value = "/displayEditLocationsForm", method = RequestMethod.GET)
    public String displayEditLocationsForm(HttpServletRequest request, Model model) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);
        Location location = locationService.getLocationById(locationId);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("location") Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "editLocation";
        }

        locationService.updateLocation(location);

        return "redirect:displayLocationsPage";
    }

}
