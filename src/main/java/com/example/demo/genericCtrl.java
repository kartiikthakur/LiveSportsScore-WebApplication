package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
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
import com.example.demo.LoginRepository;
import java.text.ParseException;
import java.util.Date;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Object;
import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Calendar;  
import java.lang.*;


@Controller
class genericCtrl{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginRepository loginRepositoryy;


	@GetMapping("/index")
	public ModelAndView renderIndex(HttpSession session)
	{
		String userID;
			try{
				userID= session.getAttribute("userID").toString();				
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
		ModelAndView m = new ModelAndView();
		m.setViewName("index");
		m.addObject("name");
		return m;
	}

	@GetMapping("/select")
	 	public ModelAndView renderSelect(HttpSession session) {
			String userID;
			try{
				userID= session.getAttribute("userID").toString();				
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
		ModelAndView select = new ModelAndView("select");
		ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);
			
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gamelogs = root.get("overallteamstandings").get("teamstandingsentry");
	        
	        if(gamelogs.isArray()) {	        	
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("team");
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("City", game.get("City").asText());
					gameDetail.put("Name", game.get("Name").asText());					
					gameDetail.put("Abbreviation", game.get("Abbreviation").asText());
	        		gameDetails.add(gameDetail);	        		
	        	});
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		select.addObject("gameDetails", gameDetails);       
		return select;		
	 }

	@GetMapping("/admin")
		public ModelAndView renderAdmin(HttpSession session) {
			String userID;
			String userName;
		try{
			userID= session.getAttribute("userID").toString();
			userName= session.getAttribute("userName").toString();

		} catch(Exception e){
			return new ModelAndView("redirect:/login");			
		}
			ModelAndView admin = new ModelAndView("admin");
			admin.addObject("name", userName);
				List<Login> users = new ArrayList<Login>();
				for(Login user : loginRepositoryy.findAll()) {
					users.add(user);
				}
			admin.addObject("users",users);
			return admin;				
	}

	@PostMapping("/index")
	public ModelAndView saveStuff (@RequestParam String name, HttpSession session)
	{
		String userID;
		String userName;
		try{
			userID= session.getAttribute("userID").toString();
			userName= session.getAttribute("userName").toString();
		} catch(Exception e){
			ModelAndView login = new ModelAndView("redirect:/login");
			return login;
		}
		ModelAndView index = new ModelAndView("index");	
		Map<String,String> TeamMap ;
		List<Map<String,String>> All = new ArrayList<>();
		String team[] = name.split(",");
		for(String string: team){
		String split[] = string.split("-");
		long count = userRepository.findTeamCount(userID,split[1]);
		User teams = null;
		if (count == 0){
		teams = new User();
		teams.setName(split[0]);
		teams.setAbr(split[1]);
		teams.setFbid(userID);		
		TeamMap = new TreeMap<>();
 		TeamMap.put("name",split[0]);	
	 	TeamMap.put("Abbreviation",split[1]);
		All.add(TeamMap);
		userRepository.save(teams);
		}
		}			
		index.addObject("AllSelections", All);
		return new ModelAndView("redirect:/index2");	
	}

	@GetMapping("/add")
	public ModelAndView addTeamInfo(HttpSession session,
			@RequestParam("name") String teamName, @RequestParam("abr") String abr
			) {
				String userID;
				String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
				System.out.println(teamName);
				System.out.println(abr);
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}	
		long count = userRepository.findTeamCount(userID,abr);
		User teams = null;
		if (count == 0){
		teams = new User();
		teams.setName(teamName);
		teams.setAbr(abr);
		teams.setFbid(userID);		
		userRepository.save(teams);
		}		
		return new ModelAndView("redirect:/index2");	


		}





	//Using PoJo Classes
	@GetMapping("/teams")
	//public ModelAndView getTeams() {
		public ModelAndView getTeams(HttpSession session) {
			String userID;
			String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}	
		ModelAndView showTeams = new ModelAndView("showTeams");
		showTeams.addObject("name", userName);

		//Endpoint to call
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		//Encode Username and Password
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
		// TOKEN:PASS
		//Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic " + encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NBATeamStanding> response = restTemplate.exchange(url, HttpMethod.GET, request, NBATeamStanding.class);
		NBATeamStanding ts = response.getBody();
		//Send the object to view
		showTeams.addObject("teamStandingEntries", ts.getOverallteamstandings().getTeamstandingsentries());
		return showTeams;
	}

	

	//Using PoJo Classes
	@GetMapping("/ranking")
	public ModelAndView getRanks(HttpSession session) {
		String userID;
		String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
				System.out.println(userID);
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
		ModelAndView ranks = new ModelAndView("ranking");
		ranks.addObject("name", userName);

		//Endpoint to call
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/overall_team_standings.json";
		//Encode Username and Password
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
		// TOKEN:PASS
		//Add headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic " + encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		//Make the call
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NBATeamStanding> response = restTemplate.exchange(url, HttpMethod.GET, request, NBATeamStanding.class);
		NBATeamStanding ts = response.getBody();
		//Send the object to view
		ranks.addObject("teamStandingEntries", ts.getOverallteamstandings().getTeamstandingsentries());
		return ranks;
	}

	//Using objectMapper
	@GetMapping("/scoreboard")
	public ModelAndView getScoreInfo(HttpSession session){
		String userID;
		String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
			
		ModelAndView scoreboard = new ModelAndView("scoreboard");
		scoreboard.addObject("name", userName);
		ArrayList<HashMap<String, String>> scoreDetails = new ArrayList<HashMap<String, String>>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);    
		String date = dateFormat.format(cal.getTime());
		String dates = date.replaceAll("/", "");
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/scoreboard.json?fordate="+ dates;
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gameScore = root.get("scoreboard").get("gameScore");
	        
	        if(gameScore.isArray()) {	        	
	        	gameScore.forEach(gamescores -> {
	        		//JsonNode game = gamescores.get("game");
					HashMap<String,String> scoreDetail = new HashMap<String, String>();
					scoreDetail.put("ID", gamescores.get("game").get("ID").asText());
	        		scoreDetail.put("awayScore", gamescores.get("awayScore").asText());
	        		scoreDetail.put("homeScore", gamescores.get("homeScore").asText());
	        		scoreDetail.put("awayTeam", gamescores.get("game").get("awayTeam").get("City").asText());
	        		scoreDetail.put("homeTeam", gamescores.get("game").get("homeTeam").get("City").asText());
	        		scoreDetails.add(scoreDetail);	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		scoreboard.addObject("scoreDetails", scoreDetails);       
		return scoreboard;
	}

	

	@GetMapping("/team")
	public ModelAndView getTeamInfo(HttpSession session,
			@RequestParam("id") String teamID
			) {
				String userID;
		String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
		ModelAndView teamInfo = new ModelAndView("teaminfo");
		teamInfo.addObject("name", userName);
		ArrayList<HashMap<String, String>> gameDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/team_gamelogs.json?team=" + teamID;
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());       
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);	
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();


		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gamelogs = root.get("teamgamelogs").get("gamelogs");	        
	        if(gamelogs.isArray()) {
	        	
	        	gamelogs.forEach(gamelog -> {
					JsonNode game = gamelog.get("game");
					JsonNode stats = gamelog.get("stats");
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("id", game.get("id").asText());
	        		gameDetail.put("date", game.get("date").asText());
					gameDetail.put("time", game.get("time").asText());
					gameDetail.put("location", game.get("location").asText());
					gameDetail.put("awayID", game.get("awayTeam").get("ID").asText());
					gameDetail.put("homeID", game.get("homeTeam").get("ID").asText());
					gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
					gameDetail.put("homeTeam", game.get("homeTeam").get("Abbreviation").asText());
					gameDetail.put("wins", stats.get("Wins").get("#text").asText());
					gameDetail.put("losses", stats.get("Losses").get("#text").asText());
					gameDetail.put("winpct", stats.get("WinPct").get("#text").asText());				
	        		gameDetails.add(gameDetail);	        		
	        	});
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		teamInfo.addObject("gameDetails", gameDetails);       
		return teamInfo;
	}



	@GetMapping("/schedule")
	public ModelAndView getSchedule(HttpSession session) { 
		String userID;
		String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
				System.out.println(userID);
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
		ModelAndView schedule = new ModelAndView("schedule");
		schedule.addObject("name", userName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();    
		String date = dateFormat.format(cal.getTime());
		String dates = date.replaceAll("/", "");
		ArrayList<HashMap<String, String>> scheduleDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/daily_game_schedule.json?fordate="+dates;
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gameentry = root.get("dailygameschedule").get("gameentry");
	        
	        if(gameentry.isArray()) {	        	
	        	gameentry.forEach(gameentryy -> {
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("id", gameentryy.get("id").asText());
	        		gameDetail.put("date", gameentryy.get("date").asText());
					gameDetail.put("time", gameentryy.get("time").asText());
					gameDetail.put("location", gameentryy.get("location").asText());
					gameDetail.put("awayTeam", gameentryy.get("awayTeam").get("City").asText());
					gameDetail.put("homeTeam", gameentryy.get("homeTeam").get("City").asText());
	        		scheduleDetails.add(gameDetail);
	        		
	        	});
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}	 
		schedule.addObject("scheduleDetails", scheduleDetails);       
		return schedule;
	}


	
    
	
	@GetMapping("/deleteuser")
	public ModelAndView deleteUser(
			@RequestParam("id") Integer id 
			) {
				ModelAndView admin = new ModelAndView("admin");
				admin.addObject("name", "Admin");
				loginRepositoryy.deleteById(id);
				List<Login> users = new ArrayList<Login>();
				for(Login user : loginRepositoryy.findAll()) {
					users.add(user);
				}
			admin.addObject("users",users);
			return admin;			
			}

			@GetMapping("/removeteam")
	public ModelAndView removeTeam(HttpSession session,
			@RequestParam("id") Integer id 
			) {
				String userID= session.getAttribute("userID").toString();
				ModelAndView index = new ModelAndView("index2");
				userRepository.deleteById(id);
				List<User> users = new ArrayList<User>();
				for(User user : userRepository.findByfbid(userID)){
					users.add(user);
				}

				ArrayList<HashMap<String, String>> scoreDetails = new ArrayList<HashMap<String, String>>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);    
		String date = dateFormat.format(cal.getTime());
		String dates = date.replaceAll("/", "");
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/scoreboard.json?fordate="+dates;
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gameScore = root.get("scoreboard").get("gameScore");
	        
	        if(gameScore.isArray()) {	        	
	        	gameScore.forEach(gamescores -> {
	        		//JsonNode game = gamescores.get("game");
					HashMap<String,String> scoreDetail = new HashMap<String, String>();
					scoreDetail.put("ID", gamescores.get("game").get("ID").asText());
	        		scoreDetail.put("awayScore", gamescores.get("awayScore").asText());
	        		scoreDetail.put("homeScore", gamescores.get("homeScore").asText());
	        		scoreDetail.put("awayTeam", gamescores.get("game").get("awayTeam").get("City").asText());
	        		scoreDetail.put("homeTeam", gamescores.get("game").get("homeTeam").get("City").asText());
	        		scoreDetails.add(scoreDetail);	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
			index.addObject("scoreDetails", scoreDetails); 				 
			index.addObject("users",users);
			
			return index;			
			}


			@GetMapping("/index2")
			public ModelAndView getFav(HttpSession session)
			 {
				String userID;
				try{
				  userID= session.getAttribute("userID").toString();
				}catch(Exception e){
					return new ModelAndView("redirect:/login");

				}
				ModelAndView index = new ModelAndView("index2");
				 List<User> users = new ArrayList<User>();
				 for(User user : userRepository.findByfbid(userID)){
					 users.add(user);
				 }
					 
		ArrayList<HashMap<String, String>> scoreDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/scoreboard.json?fordate=20181209";
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gameScore = root.get("scoreboard").get("gameScore");
	        
	        if(gameScore.isArray()) {	        	
	        	gameScore.forEach(gamescores -> {
	        		//JsonNode game = gamescores.get("game");
					HashMap<String,String> scoreDetail = new HashMap<String, String>();
					scoreDetail.put("ID", gamescores.get("game").get("ID").asText());
	        		scoreDetail.put("awayScore", gamescores.get("awayScore").asText());
	        		scoreDetail.put("homeScore", gamescores.get("homeScore").asText());
	        		scoreDetail.put("awayTeam", gamescores.get("game").get("awayTeam").get("City").asText());
	        		scoreDetail.put("homeTeam", gamescores.get("game").get("homeTeam").get("City").asText());
	        		scoreDetails.add(scoreDetail);	        		
	        	});
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
			index.addObject("scoreDetails", scoreDetails); 				 
			index.addObject("users",users);
			return index;			

			}

			@GetMapping("/schedules")
	public ModelAndView getSchedules(HttpSession session, @RequestParam("id") String teamID ) { 
		String userID;
		String userName;
			try{
				userID= session.getAttribute("userID").toString();
				userName= session.getAttribute("userName").toString();
			} catch(Exception e){
				return new ModelAndView("redirect:/login");			
			}
		ModelAndView schedule = new ModelAndView("schedules");
		schedule.addObject("name", userName);
		ArrayList<HashMap<String, String>> scheduleDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/full_game_schedule.json";
		String encoding = Base64.getEncoder().encodeToString("6ebea4ae-06ba-4b06-a5cd-d589f1:helloworld".getBytes());
        
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic "+encoding);
		HttpEntity<String> request = new HttpEntity<String>(headers);

		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		String str = response.getBody(); 
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(str);
	        JsonNode gameentry = root.get("fullgameschedule").get("gameentry");
	        
	        if(gameentry.isArray()) {	        	
	        	gameentry.forEach(gameentryy -> {
					String id1 = gameentryy.get("awayTeam").get("ID").asText();
					String id2 = gameentryy.get("homeTeam").get("ID").asText();
					if (teamID.equals(id1) || teamID.equals(id2) ){
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("id", gameentryy.get("id").asText());
	        		gameDetail.put("date", gameentryy.get("date").asText());
					gameDetail.put("time", gameentryy.get("time").asText());
					gameDetail.put("location", gameentryy.get("location").asText());
					gameDetail.put("awayTeam", gameentryy.get("awayTeam").get("Abbreviation").asText());
					gameDetail.put("homeTeam", gameentryy.get("homeTeam").get("Abbreviation").asText());
					scheduleDetails.add(gameDetail);
					}
	        		
	        	});
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}	 
		schedule.addObject("scheduleDetails", scheduleDetails);       
		return schedule;
	}

			

	
}
