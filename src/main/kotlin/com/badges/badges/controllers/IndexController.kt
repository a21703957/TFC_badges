package com.badges.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class IndexController {

    @RequestMapping("/tt", method = [RequestMethod.GET])
    fun index() : String{
        return "index2";
    }

    @RequestMapping("/perfil", method = [RequestMethod.GET])
    fun perfil(model : ModelMap ) : String{
        // model.put("titulo", "A minha primeira pagina");
        return "perfil";
    }
}