package fr.m2i.medical.controller;

import fr.m2i.medical.entities.PatientEntity;
import fr.m2i.medical.entities.VilleEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.TreeMap;

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
        return "list_patient";
    }

    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("villes" , vs.findAll() );
        return "add_edit";
    }

    @PostMapping(value = "/add")
    public String addPatient( HttpServletRequest request){

        PatientEntity p = new PatientEntity();

        populatePatient(p, creatArgsPatient(request));

        try{
            ps.savePatient( p );
        }catch( Exception e ){
            System.out.println( e.getMessage() );
        }
        return "redirect:/patient";
    }

    @GetMapping(value = "/edit")
    public String edit( Model model, HttpServletRequest request){
        model.addAttribute("patient", ps.findById(Integer.parseInt(request.getParameter("id"))));
        model.addAttribute("villes" , vs.findAll() );
        return "add_edit";
    }

    @PostMapping(value = "/edit")
    public String editPost( HttpServletRequest request){

        PatientEntity p = new PatientEntity();
        populatePatient(p, creatArgsPatient(request));

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

    private void populatePatient(PatientEntity p, TreeMap<String, String> args){
        PatientEntity rp = (p == null) ? new PatientEntity() : p;

        p.setNom(args.get("nom"));
        p.setPrenom(args.get("prenom"));
        p.setEmail(args.get("email"));
        p.setAdresse(args.get("adresse"));
        p.setTelephone(args.get("telephone"));
        p.setDatenaissance(Date.valueOf(args.get("naissance")));

        VilleEntity v = vs.findById(Integer.parseInt(args.get("ville")));

        p.setVilleId(v);
        p.setPaysCode(v.getPaysByPaysCode());
    }

    private TreeMap<String, String> creatArgsPatient(HttpServletRequest request){
        TreeMap<String, String> ret = new TreeMap<>();

        ret.put("nom", request.getParameter("nom"));
        ret.put("prenom", request.getParameter("prenom"));
        ret.put("naissance", request.getParameter("naissance"));
        ret.put("adresse", request.getParameter("adresse"));
        ret.put("email", request.getParameter("email"));
        ret.put("telephone", request.getParameter("telephone"));
        ret.put("ville", request.getParameter("ville"));

        return ret;
    }

    public PatientService getPs() {
        return ps;
    }

    public void setPs(PatientService ps) {
        this.ps = ps;
    }
}
