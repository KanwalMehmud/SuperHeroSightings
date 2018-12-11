package controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import model.Organization;
import model.SuperHero;
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
@RequestMapping(value = ("/organization"))
public class OrganizationController {

    SuperHeroService heroService;
    LocationService locationService;
    OrganizationService orgService;

    @Inject
    public OrganizationController(SuperHeroService heroService, LocationService locationService, OrganizationService orgService) {
        this.heroService = heroService;
        this.locationService = locationService;
        this.orgService = orgService;
    }

    @RequestMapping(value = "/displayOrganizationsPage", method = RequestMethod.GET)
    public String displayOrganizationsPage(Model model) {
        List<Organization> organizationList = orgService.getAllOrganization();
        List<SuperHero> heros = heroService.getAllSuperHeros();
        model.addAttribute("organizationList", organizationList);
        model.addAttribute("herosList", heros);
        return "organization";
    }

    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createOrganization(@ModelAttribute Organization organization) {
        orgService.addOrganization(organization);
        return "redirect:displayOrganizationsPage";
    }

    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        Organization organization = orgService.getOrganizationById(organizationId);
        model.addAttribute("organization", organization);
        return "organizationDetails";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(Organization organization) {
        orgService.deleteOrganizationFromAllHeros(organization);
        orgService.deleteOrganization(organization);
        return "redirect:displayOrganizationsPage";
    }

    @RequestMapping(value = "/displayEditOrganizationsForm", method = RequestMethod.GET)
    public String displayEditOrganizationsForm(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        Organization organization = orgService.getOrganizationById(organizationId);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("organization") Organization organization, BindingResult result) {
        if (result.hasErrors()) {
            return "editOrganization";
        }
        orgService.updateOrganization(organization);
        return "redirect:displayOrganizationsPage";
    }

    @RequestMapping(value = "/displayOrganizationMembers", method = RequestMethod.GET)
    public String displayOrganizationMembers(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);

        List<SuperHero> members = heroService.getAllSuperHerosByOrganization(organizationId);
        Organization organization = orgService.getOrganizationById(organizationId);

        model.addAttribute("organization", organization);
        model.addAttribute("members", members);

        return "organizationMembers";
    }

    @RequestMapping(value = "/removeMemberFromOrganization", method = RequestMethod.GET)
    public String removeMembersFromOrganization(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        String superHeroIdParameter = request.getParameter("superHeroId");
        int superHeroId = Integer.parseInt(superHeroIdParameter);

        SuperHero hero = heroService.getSuperHeroById(superHeroId);
        Organization organization = orgService.getOrganizationById(organizationId);

        orgService.deleteHeroFromOrganization(hero, organization);

        return "redirect:displayOrganizationsPage";
    }

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    public String addMemberToOrganization(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);
        String superHeroIdParameter = request.getParameter("superHeroId");
        int superHeroId = Integer.parseInt(superHeroIdParameter);

        SuperHero hero = heroService.getSuperHeroById(superHeroId);
        Organization organization = orgService.getOrganizationById(organizationId);

        orgService.addHeroToOrganization(hero, organization);
        return "redirect:displayOrganizationsPage";
    }
}
