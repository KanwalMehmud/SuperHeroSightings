/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDao;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserDao dao;
    private PasswordEncoder encoder;

    @Inject
    public UserController(UserDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/displayUserList", method = RequestMethod.GET)
    public String displayUserList(Map<String, Object> model) {
        List<User> users = dao.getAllUsers();
        model.put("usersList", users);
        return "displayUserList";
    }

    @RequestMapping(value = "/displayUserForm", method = RequestMethod.GET)
    public String displayUserForm(Map<String, Object> model) {
        return "addUserForm";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) {
        User newUser = new User();

        newUser.setUsername(req.getParameter("username"));
        String clearPw = req.getParameter("password");
        String hashPw = encoder.encode(clearPw);
        newUser.setPassword(hashPw);
        newUser.addAuthority("ROLE_USER");
        if (null != req.getParameter("isAdmin")) {
            newUser.addAuthority("ROLE_ADMIN");
        }

        dao.addUser(newUser);

        return "redirect:displayUserList";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deletUser(@RequestParam("userId") long userId,
            Map<String, Object> model) {
        dao.deleteUser(userId);
        return "redirect:displayUserList";
    }

    @RequestMapping(value = "/displayEditUserForm", method = RequestMethod.GET)
    public String displayEditUserForm(HttpServletRequest request, Model model) {
        String userIdParameter = request.getParameter("userId");
        int userId = Integer.parseInt(userIdParameter);
        User user = dao.getUserById(userId);
        user.setPassword("");
        model.addAttribute("usersList", user);
        return "editUser";
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editHero(@Valid @ModelAttribute("usersList") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "editUser";
        }
        String hashPw = encoder.encode(user.getPassword());
        user.setPassword(hashPw);
        dao.editUser(user);
        return "redirect:displayUserList";
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public String changeStatus(@RequestParam("userId") long userId,
            Map<String, Object> model) {
        dao.changeUserStatus(userId);

        return "redirect:displayUserList";
    }

}
