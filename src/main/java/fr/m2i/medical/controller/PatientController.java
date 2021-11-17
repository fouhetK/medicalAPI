package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService ps;

    @Autowired
    private VilleService vs;

    @GetMapping("")
    public String listPatient(Model model){
        model.addAttribute("patients", ps.findAll());
        return "patient/list_patient";
    }

    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("villes" , vs.findAll() );
        return "patient/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPatient( HttpServletRequest request){

        PatientEntity p = new PatientEntity();
        populatePatient(p, request);

        try{
            ps.savePatient( p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/patient";
    }

    @GetMapping(value = "/edit")
    public String edit( Model model, HttpServletRequest request){
        try {
            model.addAttribute("patient", ps.findById(Integer.parseInt(request.getParameter("id"))));
            model.addAttribute("villes", vs.findAll());
        } catch (Exception e){
            System.out.println( e.getMessage() );
        }
        return "patient/add_edit";
    }

    @PostMapping(value = "/edit")
    public String editPost( HttpServletRequest request){

        PatientEntity p = new PatientEntity();
        populatePatient(p, request);

        try{
            ps.updatePatient( Integer.parseInt(request.getParameter("id")), p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/patient";
    }

    @GetMapping(value = "/delete")
    public String delete( Model model, HttpServletRequest request){
        try {
            ps.deleteById(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            System.out.println( e.getMessage() );
        }
        return "redirect:/patient";
    }

    private void populatePatient(PatientEntity p, HttpServletRequest request){
        p.setNom(request.getParameter("nom"));
        p.setPrenom(request.getParameter("prenom"));
        p.setEmail(request.getParameter("email"));
        p.setAdresse(request.getParameter("adresse"));
        p.setTelephone(request.getParameter("telephone"));
        p.setDatenaissance(Date.valueOf(request.getParameter("naissance")));

        VilleEntity v = vs.findById(Integer.parseInt(request.getParameter("ville")));

        p.setVilleId(v);
        p.setPaysCode(v.getPaysByPaysCode());
    }

    public PatientService getPs() {
        return ps;
    }

    public void setPs(PatientService ps) {
        this.ps = ps;
    }
}
