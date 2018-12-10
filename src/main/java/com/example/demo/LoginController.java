package com.example.demo;

import javax.servlet.http.HttpSession;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Login;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.LoginRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.concurrent.*;
@Service
@Transactional
@Controller

public class LoginController {
    @Autowired 
    LoginRepository loginRepository;

    public LoginController(LoginRepository loginRepository) {
		this.loginRepository=loginRepository;
    }
	
	public void deleteMyUser(int id) {
		loginRepository.deleteById(id);
	}

@GetMapping("/login")
public ModelAndView getTeam() {
    ModelAndView demo = new ModelAndView("loginIndex");
    return demo;
}

@PostMapping("/fblogin")
public ModelAndView handleLogin(
    @RequestParam("userID") String userID,
    @RequestParam("userName") String userName, HttpSession session
    ) {
		session.setAttribute("userID", userID);
		session.setAttribute("userName", userName);
        Login user = new Login();
        user.setFbid(userID);
		user.setName(userName);
		
		 long count = loginRepository.countByfbid(userID);
         if(count==0){
			loginRepository.save(user);
		 }

		if (userID.equals("105857927119951"))	{
			return new ModelAndView("redirect:/admin");

		}	else{
		return new ModelAndView("redirect:/index2");
		}
    }
    
    @GetMapping("/logout")
	public ModelAndView renderLogout(HttpSession session)
	{
		
				String userID= session.getAttribute("userID").toString();
				String userName= session.getAttribute("userName").toString();
				ModelAndView logout = new ModelAndView("logoutIndex");
				session.removeAttribute("userID");
				session.removeAttribute("userName");
				try{
					System.out.println(session.getAttribute("userID").toString());
				} catch(Exception e){
					System.out.println("UserID gone");
				}
				System.out.println(1);
				return logout;	
	}


    
}
