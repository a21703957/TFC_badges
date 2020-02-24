package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class LoginController {

    @RequestMapping(value = ["/login"], method = [RequestMethod.GET])
    fun login(): String {
        return "login"
    }


}
