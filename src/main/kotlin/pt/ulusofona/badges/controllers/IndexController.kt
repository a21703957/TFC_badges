package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

@Controller
class IndexController {

    @GetMapping("/layout")
    fun index() : String{
        return "layout";
    }

    @GetMapping("/")
    fun home(request: HttpServletRequest) : String {
        if (request.isUserInRole("STUDENT")) {
            return "redirect:/student/"
        } else if (request.isUserInRole("TEACHER")) {
            return "redirect:/teacher/"
        }

        // don't know what do here... for now, show  the "home" page
        return "layout";
    }


    @RequestMapping("/perfil", method = [RequestMethod.GET])
    fun perfil(model : ModelMap ) : String{
        // model.put("titulo", "A minha primeira pagina");
        return "badgecreated";
    }

}