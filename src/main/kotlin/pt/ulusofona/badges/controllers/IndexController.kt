package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class IndexController {

    @GetMapping("/layout")
    fun index() : String{
        return "layout";
    }

    @RequestMapping("/perfil", method = [RequestMethod.GET])
    fun perfil(model : ModelMap ) : String{
        // model.put("titulo", "A minha primeira pagina");
        return "badgecriado";
    }


}