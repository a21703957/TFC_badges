package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.dao.Student
import pt.ulusofona.badges.forms.BadgeForm
import pt.ulusofona.badges.forms.SendForm
import pt.ulusofona.badges.repositories.BadgeRepository
import pt.ulusofona.badges.repositories.StudentRepository
import pt.ulusofona.badges.repositories.TeacherRepository
import java.net.URL
import java.security.Principal
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import kotlin.collections.ArrayList

@Controller
@RequestMapping("/teacher")
class TeacherController(
        val badgeRepository: BadgeRepository,
        val teacherRepository: TeacherRepository,
        val studentRepository: StudentRepository

) {
    private var listaBadges: MutableList<Badge> = mutableListOf()
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
/*
    @GetMapping("/badgee")
    fun detail(model : ModelMap, principal: Principal):String{
        model["badge"] = Badge()

        println("user em sessão: ${principal.name}")

        return "badgeDetail"
    }*/

    @GetMapping(value = ["/detailBadge/{badge.id}"])
    fun detailPage(@PathVariable badgeId: Long, model:ModelMap, request: HttpServletRequest): String{
        val badge = badgeRepository.findById(badgeId)
        print("ESTE" + badge)

        return "sendBadge"
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
                avaliation = badgeForm.avaliation!!,
                category = badgeForm.category!!,
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

    @GetMapping("/sendBadge")
    fun sendBadge(model: ModelMap): String{
        model["sendBadgeForm"] = SendForm()
        return "sendBadge"
    }
    @PostMapping("/sendBadge")
    fun processSendForm(@Valid @ModelAttribute("sendBadgeForm")sendForm: SendForm,
                    bindingResult: BindingResult, principal:Principal) : String{



        if(bindingResult.hasErrors()){
            return "sendBadge"
        }


        var badge = sendForm.badge

        var badgeGanho = badgeRepository.findByName(badge!!)
        var alunos = sendForm.alunos?.split(",")

        for(a in alunos!!){
            var aluno = pt.ulusofona.badges.dao.Student(
                    name = a
            )
           listaBadges.add(badgeGanho!!)
            aluno.badges = listaBadges


            studentRepository.save(aluno)
        }

        return "sended"
    }



/*
    @GetMapping("/listofstudents")
    fun listStudentes():String{
        return "listofstudents"
    }
*/






}

