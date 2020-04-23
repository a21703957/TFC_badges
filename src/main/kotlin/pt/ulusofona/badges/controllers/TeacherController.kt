package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.forms.BadgeForm
import pt.ulusofona.badges.repositories.BadgeRepository
import pt.ulusofona.badges.repositories.TeacherRepository
import java.net.URL
import java.security.Principal
import java.util.*
import javax.imageio.ImageIO
import javax.validation.Valid

@Controller
@RequestMapping("/teacher")
class TeacherController(
        val badgeRepository: BadgeRepository,
        val teacherRepository: TeacherRepository
) {
    @GetMapping("/")
    fun index() : String{
        return "redirect:/teacher/badgesList";
    }
    //Mostra o formulário
    @GetMapping("/badgeform")
    fun sendForm(model : ModelMap, principal: Principal):String{
        model["badgeForm"] = BadgeForm()

        println("user em sessão: ${principal.name}")

        return "badgeform"
    }

    @PostMapping("/badgeform")
    fun processForm(@Valid @ModelAttribute("badgeForm")badgeForm: BadgeForm,
                    bindingResult: BindingResult, principal:Principal) : String{

        if(bindingResult.hasErrors()){
            return "badgeForm"
        }
        println("user em sessão: ${principal.name}")

        val badgeDao = pt.ulusofona.badges.dao.Badge(
                name = badgeForm.name,
//                subject = badgeForm.subject!!,
                year = badgeForm.year!!,
                LEI = badgeForm.LEI,
                LEIRT =  badgeForm.LEIRT,
                LIG = badgeForm.LIG,
                description = badgeForm.description,
                toWin = badgeForm.toWin,
                validacao = badgeForm.validacao!!)
        var teacher = teacherRepository.findByName(principal.name)
        if(teacher==null) {
            teacher = pt.ulusofona.badges.dao.Teacher(
                    name = principal.name
            )
            teacherRepository.save(teacher)
        }

        badgeDao.teacher = teacher
        badgeRepository.save(badgeDao)

        return "badgecreated"
    }

    @GetMapping("/detailBadge")
    @ResponseBody
    fun detailBadge(id: Long): Optional<Badge> {

         //   model["badges"] = badgeRepository.findById(badge.id)
        /*model["badge"] = badgeRepository.findById(badge.id)
        return "badgeDetail"*/
        return badgeRepository.findById(id)
    }

    @GetMapping("/badgesList")
    fun listOfBadges( model : ModelMap ,principal: Principal):String{

        val professor = teacherRepository.findByName(principal.name)
        if (professor != null) {
            val listOfBadges =  badgeRepository.findByTeacher(professor)
            model["badges"] = listOfBadges ?: emptyArray<Badge>()

        }
        return "listOfBadges"
    }

    @GetMapping("/badgeDetail")
    fun badgeDetail(model : ModelMap, badge: Badge): String{


        return "badgeDetail"
    }

    @GetMapping("/listofstudents")
    fun listStudentes():String{
        return "listofstudents"
    }






}

