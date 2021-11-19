package fr.m2i.medical.controller;

import fr.m2i.medical.entities.UserEntity;
import fr.m2i.medical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
@Secured("ROLE_ADMIN")
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String listPatient(Model model, HttpServletRequest request ){
        String search = request.getParameter("search");
        String page = (request.getParameter("page") == null) ? "1" : request.getParameter("page");
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );
        model.addAttribute( "search" , search );

        Page<UserEntity> pages = us.findAllByPage(Integer.parseInt(page) , search);

        model.addAttribute("nombrePatients", pages.getNumberOfElements());
        model.addAttribute("nombrePages", pages.getTotalPages());
        model.addAttribute("users", pages.getContent());
        model.addAttribute( "page" , page );

        return "user/list_user";
    }

    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("user" , new UserEntity() );
        return "user/add_edit";
    }

    @PostMapping(value = "/add")
    public String addUser( HttpServletRequest request, Model model){

        UserEntity u = new UserEntity();
        populateUser(u, request);

        try{
            us.saveUser( u );
        }catch( Exception e ){
            model.addAttribute("user" , u );
            model.addAttribute("error" , e.getMessage() );
            return "ville/add_edit";
        }
        return "redirect:/user?success=true";
    }

    @GetMapping(value = "/edit")
    public String edit( Model model, HttpServletRequest request){
        try {
            model.addAttribute("user", us.findById(Integer.parseInt(request.getParameter("id"))));
        } catch (Exception e){
            return "redirect:/user?error=User%20introuvalble";
        }
        return "user/add_edit";
    }

    @PostMapping(value = "/edit")
    public String editPost( Model model, HttpServletRequest request){

        UserEntity u = new UserEntity();
        populateUser(u, request);

        try{
            us.updateUser( Integer.parseInt(request.getParameter("id")), u );
        }catch( Exception e ){
            u.setId(  -1 ); // hack
            model.addAttribute("user" , u );
            model.addAttribute("error" , e.getMessage() );
            return "user/add_edit";
        }
        return "redirect:/user?success=true";
    }

    @GetMapping(value = "/delete")
    public String delete( Model model, HttpServletRequest request){
        try {
            us.deleteById(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            return "redirect:/user?error=Patient%20introuvalble";
        }
        return "redirect:/user";
    }

    private void populateUser(UserEntity u, HttpServletRequest request){
        u.setUsername(request.getParameter("username"));
        u.setEmail(request.getParameter("email"));
        u.setName(request.getParameter("name"));
        if (request.getParameter("password") != null)
            u.setPassword(passwordEncoder.encode(request.getParameter("password")));
        u.setPhotouser(request.getParameter("photouser"));
        u.setRoles(request.getParameter("role"));
    }
}
