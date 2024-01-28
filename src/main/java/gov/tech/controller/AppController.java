package gov.tech.controller;

import gov.tech.model.UserForm;
import gov.tech.service.AppService;
import gov.tech.service.AppServiceDto;
import gov.tech.util.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AppController {
    @Value("${spring.application.name}")
    String appName;
    @Autowired
    AppService appService;

    @GetMapping("/")
    public String homePage(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("appName", appName);
        model.addAttribute("userForm", userForm);
        return "home";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("appName", appName);
        model.addAttribute("response", "");

        return "admin";
    }

    @GetMapping("/results")
    public String resultsPage(Model model){
        model.addAttribute("appName", appName);
        model.addAttribute("response", "");

        return "results";
    }

    @PostMapping("/submitChoice")
    public String submitChoice(@ModelAttribute UserForm userForm, Model model){
        System.out.println("/submitChoice controller reached!");
        AppServiceDto dto = ControllerUtil.transform(userForm);
        appService.submitChoice(dto);
        model.addAttribute("appName", appName);
        model.addAttribute("response", "");

        return "home";
    }

    @PostMapping("/searchResults")
    public String searchResults(@RequestParam String inputSessionId, Model model){
        System.out.println("/searchResults controller reached!");
        model.addAttribute("inputSessionId", inputSessionId);
        model.addAttribute("appName", appName);
        model.addAttribute("response", "");
        List<UserForm> results = appService.getDemographics(inputSessionId);
        model.addAttribute("results", results);

        return "results";
    }

    @PostMapping("/createSession")
    public String createSession(@RequestParam String inputSessionId, Model model){
        System.out.println("/createSession controller reached!");
        appService.createSession(inputSessionId);
        model.addAttribute("appName", appName);
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        model.addAttribute("response", "");

        return "home";
    }

    @PostMapping("/closeSession")
    public String closeSession(@RequestParam String inputSessionId, Model model){
        System.out.println("/closeSession controller reached!");
        List<UserForm> results = appService.getDemographics(inputSessionId);
        model.addAttribute("results", results);

        String chosenRestaurant = appService.closeSession(inputSessionId);
        model.addAttribute("appName", appName);
        model.addAttribute("result", chosenRestaurant);
        model.addAttribute("inputSessionId", inputSessionId);


        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        model.addAttribute("response", "We are eating at..... " + chosenRestaurant + "!!!");

        return "results";
    }
}
