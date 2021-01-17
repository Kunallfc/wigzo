package com.wigzo.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wigzo.project.model.SearchResults;

@Controller
@RequestMapping
public class IndexController {

	@GetMapping("/")
	public String index(ModelMap model) {

		
		return "index.html";
	}

	@GetMapping("/searchResult")
	public String index2(@ModelAttribute("search") List<SearchResults> search, Model model) {

		model.addAttribute("searchResults", search);
		System.out.println("Hello");
		return "index_search";
	}

}
