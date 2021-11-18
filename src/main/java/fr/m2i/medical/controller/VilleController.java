package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PaysEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PaysService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ville")
public class VilleController {

    @Autowired
    private VilleService vs;

    @Autowired
    private PaysService ps;

    @GetMapping("")
    public String listPatient(Model model, HttpServletRequest request ){
        String search = request.getParameter("search");
        String page = (request.getParameter("page") == null) ? "1" : request.getParameter("page");
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );
        model.addAttribute( "search" , search );

        Page<VilleEntity> pages = vs.findVilleByPage(Integer.parseInt(page) , search);

        model.addAttribute("nombreVilles", pages.getNumberOfElements());
        model.addAttribute("nombrePages", pages.getTotalPages());
        model.addAttribute("villes", pages.getContent());
        model.addAttribute( "page" , page );

        return "ville/list_ville";
    }

    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("pays" , ps.findAll() );
        return "ville/add_edit";
    }

    @PostMapping(value = "/add")
    public String addVille( HttpServletRequest request , Model model){

        VilleEntity v = new VilleEntity();
        populateVille(v, request);

        try{
            vs.saveVille( v );
        }catch( Exception e ){
            model.addAttribute("ville" , v );
            model.addAttribute("error" , e.getMessage() );
            model.addAttribute("pays" , ps.findAll() );
            return "ville/add_edit";
        }
        return "redirect:/ville?success=true";
    }

    @GetMapping(value = "/edit")
    public String edit( Model model, HttpServletRequest request){
        try {
            model.addAttribute("ville", vs.findById(Integer.parseInt(request.getParameter("id"))));
            model.addAttribute("pays", ps.findAll());
        } catch ( NoSuchElementException e ){
            return "redirect:/ville?error=Ville%20introuvalble";
        }
        return "ville/add_edit";
    }

    @PostMapping(value = "/edit")
    public String editPost( Model model,  HttpServletRequest request){

        VilleEntity v = new VilleEntity();
        populateVille(v, request);

        try{
            vs.updateVille( Integer.parseInt(request.getParameter("id")), v );
        }catch( Exception e ){
            v.setId(  -1 ); // hack
            model.addAttribute("ville" , v );
            model.addAttribute("error" , e.getMessage() );
            model.addAttribute("pays", ps.findAll());
            return "ville/add_edit";
        }
        return "redirect:/ville?success=true";
    }

    @GetMapping(value = "/delete")
    public String delete( Model model, HttpServletRequest request){
        try {
            vs.deleteById(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            return "redirect:/ville?error=Ville%20introuvalble";
        }
        return "redirect:/ville?success=true";
    }

    private void populateVille(VilleEntity v, HttpServletRequest request){
        v.setNom(request.getParameter("nom"));
        v.setCodePostal(request.getParameter("code_postal"));

        PaysEntity p = ps.findByCode(request.getParameter("pays"));

        v.setPaysByPaysCode(p);
    }
}
