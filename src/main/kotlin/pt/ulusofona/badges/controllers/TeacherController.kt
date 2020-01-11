package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import pt.ulusofona.badges.forms.BadgeForm
import pt.ulusofona.badges.repositories.BadgeRepository
import javax.validation.Valid

@Controller
class TeacherController(
        val badgeRepository: BadgeRepository
) {
    @GetMapping("/teacherHome")
    fun index() : String{
        return "teacherHome";
    }
    //Mostra o formulário
    @GetMapping("/badgeform")
    fun sendForm(model : ModelMap):String{
        model["badgeForm"] = BadgeForm()
        return "badgeform"
    }

    @PostMapping("/badgeform")
    fun processForm(@Valid @ModelAttribute("badgeForm")badgeForm: BadgeForm,
                    bindingResult: BindingResult) : String{

        if(bindingResult.hasErrors()){
            return "badgeForm"
        }

        val badgeDao = pt.ulusofona.badges.dao.Badge(
                name = badgeForm.name,
                description = badgeForm.description,
                toWin = badgeForm.toWin,
                validacao = badgeForm.validacao!!)

        badgeRepository.save(badgeDao)

        return "badgecreated"
    }

    @GetMapping("/listofstudents")
    fun listStudentes():String{
        return "listofstudents"
    }




}

