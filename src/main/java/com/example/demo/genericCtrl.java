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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
class genericCtrl{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginRepository loginRepositoryy;


	@GetMapping("/index")
	public ModelAndView renderIndex()
	{
		ModelAndView m = new ModelAndView();
		m.setViewName("index");
		m.addObject("name");
		return m;
	}

	// 	@GetMapping("/select")
	// 	public String renderSelect() {
	// 		return "select";
		
	// }

	@GetMapping("/select")
	 	public ModelAndView renderSelect(HttpSession session) {
		ModelAndView select = new ModelAndView("select");
		ModelAndView teamInfo = new ModelAndView("teamInfo");
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
			ModelAndView admin = new ModelAndView("admin");
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
		try{
			userID= session.getAttribute("userID").toString();
		} catch(Exception e){
			ModelAndView login = new ModelAndView("redirect:/login");
			return login;
		}
		ModelAndView index = new ModelAndView("index");	
		Map<String,String> TeamMap ;
		List<Map<String,String>> All = new ArrayList<>();
        session.setAttribute("userID", userID);
		String team[] = name.split(",");
		for(String string: team){
		String split[] = string.split(":");
		User teams = new User();
		teams.setName(split[0]);
		teams.setAbr(split[1]);
		teams.setFbid(userID);
		TeamMap = new TreeMap<>();
 		TeamMap.put("name",split[0]);	
	 	TeamMap.put("Abbreviation",split[1]);
		All.add(TeamMap);
		userRepository.save(teams);
		}		
		index.addObject("AllSelections", All);
		return index;		
	}



	//Using PoJo Classes
	@GetMapping("/teams")
	public ModelAndView getTeams() {
		//public ModelAndView getTeams(HttpSession session) {

	
		ModelAndView showTeams = new ModelAndView("showTeams");
		showTeams.addObject("name", "Kartik");


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
		System.out.println(ts.toString());
		//Send the object to view
		showTeams.addObject("teamStandingEntries", ts.getOverallteamstandings().getTeamstandingsentries());

		return showTeams;
	}

	

	//Using PoJo Classes
	@GetMapping("/ranking")
	public ModelAndView getRanks(HttpSession session) {
		ModelAndView ranks = new ModelAndView("ranking");
		ranks.addObject("name", "Kartik");


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
		System.out.println(ts.toString());
		//Send the object to view
		ranks.addObject("teamStandingEntries", ts.getOverallteamstandings().getTeamstandingsentries());

		return ranks;
	}

	//Using objectMapper
	@GetMapping("/scoreboard")
	public ModelAndView getScoreInfo(HttpSession session){
			
		ModelAndView scoreboard = new ModelAndView("scoreboard");
		scoreboard.addObject("name", "Kartik");
		ArrayList<HashMap<String, String>> scoreDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/scoreboard.json?fordate=20181025";
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
			System.out.println(str);
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

	
	//Using objectMapper
	@GetMapping("/team")
	public ModelAndView getTeamInfo(
			@RequestParam("id") String teamID 
			) {
		ModelAndView teamInfo = new ModelAndView("teamInfo");
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
			System.out.println(str);
	        System.out.println(root.get("teamgamelogs").get("lastUpdatedOn").asText());
	        System.out.println(root.get("teamgamelogs").get("gamelogs").getNodeType());
	        JsonNode gamelogs = root.get("teamgamelogs").get("gamelogs");
	        
	        if(gamelogs.isArray()) {
	        	
	        	gamelogs.forEach(gamelog -> {
	        		JsonNode game = gamelog.get("game");
	        		HashMap<String,String> gameDetail = new HashMap<String, String>();
	        		gameDetail.put("id", game.get("id").asText());
	        		gameDetail.put("date", game.get("date").asText());
					gameDetail.put("time", game.get("time").asText());
					gameDetail.put("location", game.get("location").asText());
	        		gameDetail.put("awayTeam", game.get("awayTeam").get("Abbreviation").asText());
	        		gameDetails.add(gameDetail);
	        		
	        	});
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	 
		teamInfo.addObject("gameDetails", gameDetails);
		
        
		return teamInfo;
	}

	//Using objectMapper
	@GetMapping("/schedule")
	public ModelAndView getSchedule(HttpSession session) { 
		ModelAndView schedule = new ModelAndView("schedule");
		schedule.addObject("name", "Kartik");
		ArrayList<HashMap<String, String>> scheduleDetails = new ArrayList<HashMap<String, String>>();
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nba/2018-2019-regular/daily_game_schedule.json?fordate=20181114";
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
			System.out.println(str);
			//JsonNode jsonNode1 = actualObj.get("lastUpdatedOn");
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

	
@PostMapping("/fblogin")
public ModelAndView handleLogin(
    @RequestParam("userID") String userID,
    @RequestParam("userName") String userName, HttpSession session
    ) {
        session.setAttribute("userID", userID);
        Login user = new Login();
        user.setFbid(userID);
		user.setName(userName);
		// int count = loginRepositoryy.findCount(userID);
        // if(count==0){
		// 	loginRepositoryy.save(user);
		// 	System.out.println(1);
		// }
		
		if (userID.equals("1762276433882018f"))	{
			return new ModelAndView("redirect:/admin");

		}	else{
		return new ModelAndView("redirect:/index2");
		}
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

			@GetMapping("/index2")
			public ModelAndView getFav(HttpSession session)
			 {
				 String userID= session.getAttribute("userID").toString();
				System.out.println(userID);
				ModelAndView index = new ModelAndView("index2");
				 List<User> users = new ArrayList<User>();
				 for(User user : userRepository.findByfbid(userID)){
					 users.add(user);
				 }
				 index.addObject("users",users);
				 System.out.println(users);
			return index;			

			}

			

	
}
