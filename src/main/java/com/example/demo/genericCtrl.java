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

		@GetMapping("/select")
		public String renderSelect() {
			return "select";
		
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
				case "Toronto Raptors":
				teams.setAbr("TOR");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Toronto Raptors");	
				TeamMap.put("Abbreviation","TOR");
				All.add(TeamMap);
				break;
				case "Milwaukee Bucks":
				teams.setAbr("MIL");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Milwaukee Bucks");	
				TeamMap.put("Abbreviation","MIL");
				All.add(TeamMap);
				break;
				case "Denver Nuggets":
				teams.setAbr("DEN");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Denver Nuggets");	
				TeamMap.put("Abbreviation","DEN");
				All.add(TeamMap);
				break;
				case "Oklahoma City Thunders":
				teams.setAbr("OKL");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Oklahoma City Thunders");	
				TeamMap.put("Abbreviation","OKL");
				All.add(TeamMap);
				break;
				case "Golden State Warriors":
				teams.setAbr("GSW");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Golden State Warriors");	
				TeamMap.put("Abbreviation","GSW");
				All.add(TeamMap);
				break;
				case "Detroit Pistons":
				teams.setAbr("DET");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Detroit Pistons");	
				TeamMap.put("Abbreviation","DET");
				All.add(TeamMap);
				break;
				case "Philadelphia 76ers":
				teams.setAbr("PHI");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Philadelphia 76ers");	
				TeamMap.put("Abbreviation","PHI");
				All.add(TeamMap);
				break;
				case "Memphis Grizzels":
				teams.setAbr("MEM");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Memphis Grizzels");	
				TeamMap.put("Abbreviation","MEM");
				All.add(TeamMap);
				break;
				case "Indiana Pacers":
				teams.setAbr("IND");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Indiana Pacers");	
				TeamMap.put("Abbreviation","IND");
				All.add(TeamMap);
				break;
				case "Portland Trail Blazers":
				teams.setAbr("POR");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Portland Trail Blazers");	
				TeamMap.put("Abbreviation","POR");
				All.add(TeamMap);
				break;
				case "Los Angeles Lakers":
				teams.setAbr("LAL");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Los Angeles Lakers");	
				TeamMap.put("Abbreviation","LAL");
				All.add(TeamMap);
				break;
				case "Boston Celtics":
				teams.setAbr("BOS");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Boston Celtics");	
				TeamMap.put("Abbreviation","BOS");
				All.add(TeamMap);
				break;
				case "New Orleans Pelicans":
				teams.setAbr("NOP");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "New Orleans Pelicans");	
				TeamMap.put("Abbreviation","NOP");
				All.add(TeamMap);
				break;
				case "Charlotte Hornets":
				teams.setAbr("CHA");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Charlotte Hornets");	
				TeamMap.put("Abbreviation","CHA");
				All.add(TeamMap);
				break;
				case "Dallas Mavericks":
				teams.setAbr("DAL");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Dallas Mavericks");	
				TeamMap.put("Abbreviation","DAL");
				All.add(TeamMap);
				break;
				case "Minnesota Timberwolves":
				teams.setAbr("MIN");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Minnesota Timberwolves");	
				TeamMap.put("Abbreviation","MIN");
				All.add(TeamMap);
				break;
				case "Orlando Magic":
				teams.setAbr("ORL");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Orlando Magic");	
				TeamMap.put("Abbreviation","ORL");
				All.add(TeamMap);
				break;
				case "Utah Jazz":
				teams.setAbr("UTA");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Utah Jazz");	
				TeamMap.put("Abbreviation","UTA");
				All.add(TeamMap);
				break;
				case "Houston Rockets":
				teams.setAbr("HOU");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Houston Rockets");	
				TeamMap.put("Abbreviation","HOU");
				All.add(TeamMap);
				case "Sacramento Kings":
				teams.setAbr("SAC");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Sacramento Kings");	
				TeamMap.put("Abbreviation","SAC");
				All.add(TeamMap);
				case "San Antonio Spurs":
				teams.setAbr("SAS");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "San Antonio Spurs");	
				TeamMap.put("Abbreviation","SAS");
				All.add(TeamMap);
				case "Brooklyn Nets":
				teams.setAbr("BRO");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Brooklyn Nets");	
				TeamMap.put("Abbreviation","BRO");
				All.add(TeamMap);
				case "New York Knicks":
				teams.setAbr("NYK");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "New York Knicks");	
				TeamMap.put("Abbreviation","NYK");
				All.add(TeamMap);
				case "Chicago Bulls":
				teams.setAbr("CHI");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Chicago Bulls");	
				TeamMap.put("Abbreviation","CHI");
				All.add(TeamMap);
				break;
				case "Atlanta Hawks":
				teams.setAbr("ATL");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Atlanta Hawks");	
				TeamMap.put("Abbreviation","ATL");
				All.add(TeamMap);
				break;
				case "Cleveland Cavaliers":
				teams.setAbr("CLE");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Cleveland Cavaliers");	
				TeamMap.put("Abbreviation","CLE");
				All.add(TeamMap);
				break;
				case "Phoenix Suns":
				teams.setAbr("PHX");	
				TeamMap = new TreeMap<>();			
				TeamMap.put("name", "Phoenix Suns");	
				TeamMap.put("Abbreviation","PHX");
				All.add(TeamMap);
				break;
			}
			userRepository.save(teams);
		}					
		n.addObject("AllSelections", All);
		return n;
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
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nhl/2018-2019-regular/overall_team_standings.json";
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
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nhl/2018-2019-regular/scoreboard.json?fordate=20181025";
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
		String url = "https://api.mysportsfeeds.com/v1.2/pull/nhl/2018-2019-regular/daily_game_schedule.json?fordate=20181114";
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
        System.out.println(userID + userName);
        session.setAttribute("userID", userID);
        Login user = new Login();
        user.setFbid(userID);
		user.setName(userName);
		// int count = loginRepositoryy.findCount(userID);
        // if(count==0){
			loginRepositoryy.save(user);
		// 	System.out.println(1);
		// }
		
		if (userID.equals("1762276433882018"))	{
			return new ModelAndView("redirect:/admin");

		}	else{
		return new ModelAndView("redirect:/index");
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

	
}
