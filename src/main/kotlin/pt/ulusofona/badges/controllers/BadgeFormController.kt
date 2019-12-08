package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import pt.ulusofona.badges.Badge

@Controller
class BadgeFormController{

    //Mostra o formulário
    @GetMapping("/badgeform")
    fun sendForm(badge: Badge):String{
        return "badgeform"
    }

    @PostMapping("/badgeform")
    fun processForm(badge : Badge) : String{

        return "badgecreated"
    }






    //Badge form with error
   /* @RequestMapping("/badgeform", method = [RequestMethod.GET])
    fun formError(model : Model): String {
        model.addAttribute("formError", true);
        return "badgeform.html";
    }*/
}