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

@Service
@Transactional
@Controller

public class userManager {
    @Autowired 
    LoginRepository loginRepository;

    public userManager(LoginRepository loginRepository) {
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


    
}
