package fr.m2i.medical.controller;

import fr.m2i.medical.entities.RdvEntity;
import fr.m2i.medical.service.PatientService;
import fr.m2i.medical.service.RdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/rdv")
@Secured("ROLE_ADMIN")
public class RdvController {

    @Autowired
    private PatientService ps;

    @Autowired
    private RdvService rs;

    @GetMapping("")
    public String listPatient(Model model, HttpServletRequest request ){
        String search = (request.getParameter("search") != "") ? request.getParameter("search") : null;
        String datesearch = (request.getParameter("datesearch") != "") ? request.getParameter("datesearch") : null;;
        String page = (request.getParameter("page") == null) ? "1" : request.getParameter("page");
        model.addAttribute( "error" , request.getParameter("error") );
        model.addAttribute( "success" , request.getParameter("success") );
        model.addAttribute( "search" , search );
        model.addAttribute( "datesearch" , datesearch );

        Page<RdvEntity> pages = rs.findAllByPage(Integer.parseInt(page), search, datesearch);

        System.out.println("pages : " + pages.getNumberOfElements());
        model.addAttribute("nombreRdvs", pages.getNumberOfElements());
        model.addAttribute("nombrePages", pages.getTotalPages());
        model.addAttribute("rdvs", pages.getContent());
        model.addAttribute( "page" , page );

        return "rdv/list_rdv";
    }

    @GetMapping(value = "/add")
    public String add( Model model ){
        model.addAttribute("patients" , ps.findAll() );
        return "rdv/add_edit";
    }

    @PostMapping(value = "/add")
    public String addPatient( HttpServletRequest request, Model model) {

        RdvEntity r = new RdvEntity();

        try{
            populateRdv(r, request);
            rs.saveRdv( r );
        }catch( Exception e ){
            model.addAttribute("rdv" , r );
            model.addAttribute("error" , e.getMessage() );
            model.addAttribute("patients", ps.findAll());
            System.out.println(e);
            return "rdv/add_edit";
        }
        return "redirect:/rdv?success=true";
    }

    @GetMapping(value = "/edit")
    public String edit( Model model, HttpServletRequest request){
        try {
            model.addAttribute("rdv", rs.findById(Integer.parseInt(request.getParameter("id"))));
            model.addAttribute("patients", ps.findAll());
        } catch (Exception e){
            return "redirect:/rdv?error=Rendez-vous%20introuvalble";
        }
        return "rdv/add_edit";
    }

    @PostMapping(value = "/edit")
    public String editPost( Model model, HttpServletRequest request) {

        RdvEntity r = new RdvEntity();

        try{
            populateRdv(r, request);
            rs.updateRdv(Integer.parseInt(request.getParameter("id")), r );
        }catch( Exception e ){
            r.setId(  -1 ); // hack
            model.addAttribute("rdv" , r );
            model.addAttribute("error" , e.getMessage() );
            model.addAttribute("patients", ps.findAll());
            return "rdv/add_edit";
        }
        return "redirect:/rdv?success=true";
    }

    @GetMapping(value = "/delete")
    public String delete( Model model, HttpServletRequest request){
        try {
            ps.deleteById(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            return "redirect:/rdv?error=Rendez-vous%20introuvalble";
        }
        return "redirect:/rdv";
    }

    private void populateRdv(RdvEntity r, HttpServletRequest request) throws ParseException {

        r.setPatient(ps.findById(Integer.parseInt(request.getParameter("patient"))));

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date parsedDate = inputFormat.parse(request.getParameter("datetime"));

        r.setDateheure(new Timestamp(parsedDate.getTime()));

        r.setNote(request.getParameter("note"));
        r.setType(request.getParameter("type"));
        r.setDuree(Integer.valueOf(request.getParameter("duree")));
    }
}
