package pt.ulusofona.badges.controllers

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.dao.StudentBadge
import pt.ulusofona.badges.forms.BadgeForm
import pt.ulusofona.badges.forms.SendForm
import pt.ulusofona.badges.repositories.BadgeRepository
import pt.ulusofona.badges.repositories.StudentBadgeRepository
import pt.ulusofona.badges.repositories.StudentRepository
import pt.ulusofona.badges.repositories.TeacherRepository
import java.security.Principal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@Controller
@RequestMapping("/teacher")
class TeacherController(
        val badgeRepository: BadgeRepository,
        val teacherRepository: TeacherRepository,
        val studentRepository: StudentRepository,
        val studentBadgeRepository: StudentBadgeRepository

) {

    private var listaBadges  = HashSet<Badge>()
    private var listaStudentBadge = HashSet<StudentBadge>()
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

    @GetMapping(value = ["/detailBadge/{badgeId}"])
    fun detailPage(@PathVariable badgeId: Long, model:ModelMap, request: HttpServletRequest): String{
        val b = studentBadgeRepository.findByIdOrNull(badgeId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)


        model["badge"] = b.badge
        print("ESTE" + b.badge)
        val estudantes = studentBadgeRepository.findByBadge(b.badge)

        model["estudantes"] = estudantes
        print("tamanho" + estudantes!!.size)

        //Mudar a variável quando tiver a tabela funcional
        model["data"] = "05/05/2020"

        return "badgeDetail"
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

        return "redirect:/teacher/detailBadge/${badgeDao.id}"
    }

    @GetMapping("/detailBadge")
    @ResponseBody
    fun detailBadge(id: Long, model : ModelMap): Optional<Badge> {

         //   model["badges"] = badgeRepository.findById(badge.id)
        /*model["badge"] = badgeRepository.findById(badge.id)
        return "badgeDetail"*/


        var badge = badgeRepository.findByIdOrNull(id)?:throw ResponseStatusException(HttpStatus.NOT_FOUND)
        var  students = badge.studentBadges
        model["estudantes"] = students
       // model["data"] completar

        print("tamanho" + badge.studentBadges.size)
        return badgeRepository.findById(id)
    }

    @GetMapping("/badgesList")
    fun listOfBadges( model : ModelMap ,principal: Principal):String{

        val professor = teacherRepository.findByName(principal.name)


        if (professor != null) {
            val listOfBadges =  badgeRepository.findByTeacher(professor)
            model["badges"] = listOfBadges ?: emptyArray<Badge>()
            model["professor"] = teacherRepository.findByName(principal.name)
        }
        return "listOfBadges"
}

    @GetMapping("/badgeSend")
    fun sendBadge(model: ModelMap, principal: Principal): String{
        model["sendBadgeForm"] = SendForm()
        val professor = teacherRepository.findByName(principal.name)
        if(professor != null){
            val listOfBadges = badgeRepository.findByTeacher(professor)
            model["badges"] = listOfBadges ?: emptyArray<Badge>()
        }

        return "badgeSend"
    }
    @PostMapping("/badgeSend")
    fun processSendForm(@Valid @ModelAttribute("sendBadgeForm")sendForm: SendForm,
                    bindingResult: BindingResult, principal: Principal, model: ModelMap) : String{

        if(bindingResult.hasErrors()){
            val professor = teacherRepository.findByName(principal.name)
            if(professor != null) {
                val listOfBadges = badgeRepository.findByTeacher(professor)
                model["badges"] = listOfBadges ?: emptyArray<Badge>()
            }
            return "badgeSend"
        }

        var  dateTime = LocalDateTime.now()
        var currentDate = dateTime.format(DateTimeFormatter.ofPattern("d/M/y"))

        var badge = sendForm.badge

        var badgeGanho = badgeRepository.findByName(badge!!)
        var alunos = sendForm.alunos?.split(",")

        for(a in alunos!!){
            /*var aluno = pt.ulusofona.badges.dao.Student(
                    name = a
            )*/

            var nomeAluno = a.trim()

            var aluno = studentRepository.findByName(nomeAluno)
            if(aluno == null){
                aluno = pt.ulusofona.badges.dao.Student( name =  nomeAluno)
            }

            var studentBadge = StudentBadge()
             studentBadge.data = currentDate
            if (badgeGanho != null) {
                studentBadge.badge = badgeGanho
            }
            studentBadge.student = aluno
            if (badgeGanho != null) {
                listaBadges.add(badgeGanho)
            }

            listaStudentBadge.add(studentBadge)
            studentBadgeRepository.save(studentBadge)
            aluno!!.studentBadges = listaStudentBadge
            studentRepository.save(aluno)
        }

        model["tabela_studentbadge"] =HashSet(badgeGanho!!.studentBadges)


        //model["data"] = currentDate

        return "redirect:/teacher/detailBadge/${badgeGanho.id}"

    }

/*
    @GetMapping("/listofstudents")
    fun listStudentes():String{
        return "listofstudents"
    }
*/

}

