package gov.tech.controller;

import gov.tech.exception.AppServiceException;
import gov.tech.model.UserForm;
import gov.tech.service.ParticipantService;
import gov.tech.service.SessionService;
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
    private final SessionService sessionService;
    private final ParticipantService participantService;

    @Autowired
    public AppController(SessionService sessionService, ParticipantService participantService) {
        this.sessionService = sessionService;
        this.participantService = participantService;
    }

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
        AppServiceDto dto = ControllerUtil.transform(userForm);
        String response = participantService.submitChoice(dto);
        model.addAttribute("appName", appName);
        model.addAttribute("response", response);
        model.addAttribute("userForm", new UserForm());


        return "home";
    }

    @PostMapping("/searchResults")
    public String searchResults(@RequestParam String inputSessionId, Model model){
        model.addAttribute("inputSessionId", inputSessionId);
        model.addAttribute("appName", appName);

        UserForm lunchSession  = sessionService.getSession(inputSessionId);
        List<UserForm> results = participantService.getDemographics(inputSessionId);

        if (lunchSession == null){
            model.addAttribute("errorResponse", "Session ID does not exist.");
            return "results";
        }
        if (lunchSession.isSessionActive()){
            model.addAttribute("response", "Session is still <b>OPEN</b>. Join now!");
        }else {
            model.addAttribute("response", "Session is <b>CLOSED</b>. <br>" +
                    "Result = <b>" + lunchSession.getResult() +"</b>");
        }
        model.addAttribute("results", results);

        return "results";
    }

    @PostMapping("/createSession")
    public String createSession(@RequestParam String inputSessionId, Model model){
        String response = sessionService.createSession(inputSessionId);
        model.addAttribute("appName", appName);
        model.addAttribute("response", response);
        model.addAttribute("inputSessionId", inputSessionId);

        return "admin";
    }

    @PostMapping("/closeSession")
    public String closeSession(@RequestParam String inputSessionId, Model model){
        try{
            List<UserForm> results = participantService.getDemographics(inputSessionId);
            model.addAttribute("results", results);

            String chosenRestaurant = sessionService.closeSession(inputSessionId);
            model.addAttribute("appName", appName);
            model.addAttribute("result", chosenRestaurant);
            model.addAttribute("inputSessionId", inputSessionId);


            UserForm userForm = new UserForm();
            model.addAttribute("userForm", userForm);
            if (results.isEmpty() && chosenRestaurant.isBlank()){
                model.addAttribute("response", "Looks like no one participated...");
            }else {
                model.addAttribute("response", "We are eating at... <b>" + chosenRestaurant + "</b>!!!");
            }
        } catch (AppServiceException e) {
            String response = e.getMessage();
            model.addAttribute("response", response);
            return "admin";

        }

        return "results";
    }
}
