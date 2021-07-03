package com.computime.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {
	@GetMapping(path = "/")
	public String Acceuil() {
		return "page.jsp" ; 
	}
}
