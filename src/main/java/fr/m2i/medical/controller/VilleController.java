package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.PaysEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.PaysService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Controller
@RequestMapping("/ville")
public class VilleController {

    @Autowired
    private VilleService vs;

    @Autowired
    private PaysService ps;

    @GetMapping("")
    public String listPatient(Model model){
        model.addAttribute("villes", vs.findAll());
        return "ville/list_ville";
    }

    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("pays" , ps.findAll() );
        return "ville/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPatient( HttpServletRequest request){

        VilleEntity v = new VilleEntity();
        populateVille(v, request);

        try{
            vs.saveVille( v );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/ville";
    }

    @GetMapping(value = "/edit")
    public String edit( Model model, HttpServletRequest request){
        try {
            model.addAttribute("ville", vs.findById(Integer.parseInt(request.getParameter("id"))));
            model.addAttribute("pays", ps.findAll());
        } catch (Exception e){
            System.out.println( e.getMessage() );
        }
        return "ville/add_edit";
    }

    @PostMapping(value = "/edit")
    public String editPost( HttpServletRequest request){

        VilleEntity v = new VilleEntity();
        populateVille(v, request);

        try{
            vs.updateVille( Integer.parseInt(request.getParameter("id")), v );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/ville";
    }

    @GetMapping(value = "/delete")
    public String delete( Model model, HttpServletRequest request){
        try {
            vs.deleteById(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return "redirect:/ville";
    }

    private void populateVille(VilleEntity v, HttpServletRequest request){
        v.setNom(request.getParameter("nom"));
        v.setCodePostal(request.getParameter("code_postal"));

        PaysEntity p = ps.findByCode(request.getParameter("pays"));

        v.setPaysByPaysCode(p);
    }
}
