package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap; 
import java.util.TreeMap;
import java.util.Map; 
import com.example.demo.User;
import com.example.demo.UserRepository;
import java.util.*;
import java.util.Arrays;
import java.util.List;



@Controller
class genericCtrl{

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/index")
	public ModelAndView renderIndex()
	{
		ModelAndView m = new ModelAndView();
		m.setViewName("index");
		m.addObject("name");
		return m;
	}

		@GetMapping("/select")
		public String renderSelect() {
			return "select";
		
	}

	

	@PostMapping("/index")
	public ModelAndView saveStuff (@RequestParam String name)
	{
		ModelAndView n = new ModelAndView("index");
		userRepository.deleteAll();		
		String team[] = name.split(",");			
		Map<String,String> TeamMap ;
		List<Map<String,String>> All = new ArrayList<>();
				
		for(String string: team){
			User teams = new User();
			teams.setName(string);	
				
				switch (string){
				case "Washington Wizards":
				teams.setAbr("WW");
				TeamMap = new TreeMap<>();
				TeamMap.put("name","Washington Wizards");	
				TeamMap.put("Abbreviation","WW");
				All.add(TeamMap);
				break;
				case "Miami Heat":
				teams.setAbr("MH");
				TeamMap = new TreeMap<>();
				TeamMap.put("name", "Miami Heat");	
				TeamMap.put("Abbreviation","MH");
				All.add(TeamMap);
				break;
				case "Los Angeles Clippers":
				teams.setAbr("LAC");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Los Angeles Clippers");	
				TeamMap.put("Abbreviation","LAC");
				All.add(TeamMap);
				break;
			}
		}					
		n.addObject("AllSelections", All);
		return n;
	}
}
