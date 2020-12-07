package com.tw.certmaster.controllers;

import com.tw.certmaster.results.CertificationResult;
import com.tw.certmaster.services.CertificationsServices;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class CertificationsController {
    @CrossOrigin("*")
    @GetMapping("/certifications")
    public ArrayList<CertificationResult> getCertifications() {
        return CertificationsServices.getCertifications();
    }
}
