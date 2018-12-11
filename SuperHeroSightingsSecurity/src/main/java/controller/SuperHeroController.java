package controller;

import service.LocationService;
import service.OrganizationService;
import service.SuperHeroService;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import model.SuperHero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = ("/superHero"))
public class SuperHeroController {

    SuperHeroService heroService;
    LocationService locationService;
    OrganizationService orgService;

    @Inject
    public SuperHeroController(SuperHeroService heroService, LocationService locationService, OrganizationService orgService) {
        this.heroService = heroService;
        this.locationService = locationService;
        this.orgService = orgService;
    }

    @RequestMapping(value = "/displayHerosPage", method = RequestMethod.GET)
    public String displayHerosPage(Model model) {
        List<SuperHero> heroList = heroService.getAllSuperHeros();

        model.addAttribute("heroList", heroList);

        return "superHero";
    }

    @RequestMapping(value = "/createHero", method = RequestMethod.POST)
    public String createHero(SuperHero hero) {
        heroService.addHero(hero);
        return "redirect:displayHerosPage";
    }

    @RequestMapping(value = "/deleteHero", method = RequestMethod.GET)
    public String deleteHero(HttpServletRequest request) {
        String heroIdParameter = request.getParameter("superHeroId");
        int superHeroId = Integer.parseInt(heroIdParameter);
        SuperHero hero = heroService.getSuperHeroById(superHeroId);
        orgService.deleteHeroFromAllOrganizations(hero);
        locationService.deleteHeroFromAllLocations(hero);
        heroService.deleteSuperHero(hero);
        return "redirect:displayHerosPage";
    }

    @RequestMapping(value = "/displayEditHerosForm", method = RequestMethod.GET)
    public String displayEditHerosForm(HttpServletRequest request, Model model) {
        String heroIdParameter = request.getParameter("superHeroId");
        int superHeroId = Integer.parseInt(heroIdParameter);
        SuperHero hero = heroService.getSuperHeroById(superHeroId);
        model.addAttribute("hero", hero);
        return "editSuperHero";
    }

    @RequestMapping(value = "/editHero", method = RequestMethod.POST)
    public String editHero(@Valid @ModelAttribute("hero") SuperHero hero, BindingResult result) {
        if (result.hasErrors()) {
            return "editSuperHero";
        }
        heroService.updateSuperHero(hero);
        return "redirect:displayHerosPage";
    }

}
