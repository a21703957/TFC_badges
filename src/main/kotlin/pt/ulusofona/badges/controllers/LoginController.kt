package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class LoginController {

    @RequestMapping("/login", method = [RequestMethod.GET])
    fun loginPage():String{
        return "badgecreated";
    }
}