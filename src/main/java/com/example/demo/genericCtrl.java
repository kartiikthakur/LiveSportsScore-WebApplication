package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap; 
import java.util.Map; 


@Controller
class genericCtrl{

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
		System.out.println(name);
		String[] team = name.split(",");
		
		 Map<String, String> map = new HashMap<String, String>();
    	map.put("Washington Wizards", "WW");
    	map.put("Miami Heat", "MH");
    	map.put("Los Angeles Clippers", "LAC");
		for (int i=0;i<team.length;i++)
		{
		User teams = new User();
		teams.setName(team[i]);
		String Abb = map.get(team[i]);
		teams.setEmail(Abb);
		}
		
		n.addObject("name", name);
		String abbr = map.get(name);
		n.addObject("abbr",abbr);		
		return n;
	}

}