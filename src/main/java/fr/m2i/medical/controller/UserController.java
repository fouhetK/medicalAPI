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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
@Secured("ROLE_ADMIN")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
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
        try {
            populateUser(u, request);
        } catch (IOException | ServletException e) {
            model.addAttribute("error" , e.getMessage() );
        }

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
        try {
            populateUser(u, request);
        } catch (IOException | ServletException e) {
            model.addAttribute("error" , e.getMessage() );
        }

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

    @PostMapping(value = "/profil/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER" })
    public String editProfil(Model model, @PathVariable int id , HttpServletRequest request ){

        UserEntity u = new UserEntity();
        try {
            populateUser(u, request);
        } catch (IOException | ServletException e) {
            model.addAttribute("error" , e.getMessage() );
        }

        u.setId( id );

        try{
            us.editProfil( id, u );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }

        // Mettre à jour l'utilisateur ????

        return "redirect:/patient?success=true";
    }

    private void populateUser(UserEntity u, HttpServletRequest request) throws ServletException, IOException {
        u.setUsername(request.getParameter("username"));
        u.setEmail(request.getParameter("email"));
        u.setName(request.getParameter("name"));
        if (request.getParameter("password") != null)
            u.setPassword(passwordEncoder.encode(request.getParameter("password")));
        u.setRoles(request.getParameter("role"));

        File uploadDir = new File("src\\main\\resources\\static\\images");
        if (!uploadDir.exists()) uploadDir.mkdir();

        Part part = request.getPart("photouser");
        String fileName = part.getSubmittedFileName();
        u.setPhotouser(fileName);
        part.write(uploadDir.getAbsolutePath() + File.separator + fileName);
    }
}
