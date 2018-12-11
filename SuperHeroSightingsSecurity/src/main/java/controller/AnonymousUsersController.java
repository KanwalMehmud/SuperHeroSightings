package controller;

import java.util.List;
import javax.inject.Inject;
import model.SuperHero;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.LocationService;
import service.OrganizationService;
import service.SuperHeroService;

@Controller
@RequestMapping(value = ("/anonymousUsers"))
public class AnonymousUsersController {

    SuperHeroService heroService;

    @Inject
    public AnonymousUsersController(SuperHeroService heroService) {
        this.heroService = heroService;

    }

    @RequestMapping(value = "/displayHerosToAnonymousUsers", method = RequestMethod.GET)
    public String displayHerosToAnonymousUsers(Model model) {
        List<SuperHero> heroList = heroService.getAllSuperHeros();

        model.addAttribute("heroList", heroList);

        return "listOfSuperHeros";
    }
}
