package fr.m2i.medical.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @RequestMapping("/test")
    @ResponseBody
    public String testme(){
        return "<h1>Bonjour</h1>";
    }
}
