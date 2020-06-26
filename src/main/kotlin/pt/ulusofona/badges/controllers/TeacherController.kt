package pt.ulusofona.badges.controllers

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.dao.Student
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
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import kotlin.collections.HashSet


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
    fun detailPage(@PathVariable badgeId: Long, model:ModelMap, request: HttpServletRequest, principal:Principal): String{

        val badge = badgeRepository.findByIdOrNull(badgeId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        model["badge"] = badge
        print("ESTE" + badge)
        val estudantes = studentBadgeRepository.findByBadgeId(badge.id)?.map {
            val student = studentRepository.getOne(it.studentId)
            student.dateOfBadge = it.data
            student
        }

        var estudanteMapa = HashSet<Student>()
        if (estudantes != null) {
            for (estudante in estudantes!!){
                estudanteMapa.add(estudante)
            }
        }

        model["estudantes"] = estudanteMapa
        print("tamanho" + estudantes!!.size)

        //Mudar a variável quando tiver a tabela funcional
        // model["data"] = "05/05/2020"

        return "badgeDetail"
    }



    @PostMapping("/badgeform")
    fun processForm(@Valid @ModelAttribute("badgeForm")badgeForm: BadgeForm,
                    bindingResult: BindingResult, principal:Principal) : String{

        if(bindingResult.hasErrors()){
            return "badgeForm"
        }
        println("user em sessão: ${principal.name}")

        var badgeRepetido = badgeRepository.findByName(badgeForm.name)

        if(badgeRepetido == null){
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

        }else{
            bindingResult.rejectValue("name", "badges.duplicate", "Erro: Já existe um badge com este nome")
            return "badgeForm"

        }

    }

//    @GetMapping("/detailBadge")
//    @ResponseBody
//    fun detailBadge(id: Long, model : ModelMap): Optional<Badge> {
//
//         //   model["badges"] = badgeRepository.findById(badge.id)
//        /*model["badge"] = badgeRepository.findById(badge.id)
//        return "badgeDetail"*/
//
//
////        var badge = badgeRepository.findByIdOrNull(id)?:throw ResponseStatusException(HttpStatus.NOT_FOUND)
////        var  students = badge.studentBadges
////        model["estudantes"] = students
////       // model["data"] completar
////
////        print("tamanho" + badge.studentBadges.size)
////        return badgeRepository.findById(id)
//
//        return Optional.empty()
//    }

    @GetMapping("/badgesList")
    fun listOfBadges( model : ModelMap ,principal: Principal):String{

        var professor = teacherRepository.findByName(principal.name)


        if (professor != null) {
            val listOfBadges =  badgeRepository.findByTeacher(professor)
            model["badges"] = listOfBadges ?: emptyArray<Badge>()
            model["professor"] = teacherRepository.findByName(principal.name)
        }else{
            professor = pt.ulusofona.badges.dao.Teacher(name = principal.name)
            teacherRepository.save(professor)
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

        var dateTime = LocalDateTime.now()
        var currentDate = dateTime.format(DateTimeFormatter.ofPattern("d/M/y"))

        var badge = sendForm.badge

        var badgeGanho = badgeRepository.findByName(badge!!)
        var alunos = sendForm.alunos?.split(",")
        var h = HashSet<String>()
        for(aluno in alunos!!){
          h.add(aluno)
        }


        for(a in h){
            /*var aluno = pt.ulusofona.badges.dao.Student(
                    name = a
            )*/

            var nomeAluno = a.trim()

            var aluno = studentRepository.findByName(nomeAluno)



            if(aluno == null){
                aluno = pt.ulusofona.badges.dao.Student( name =  nomeAluno)
                studentRepository.save(aluno)
            }
            var alunoRepetido = studentBadgeRepository.findByStudentId(aluno.id)

            var studentBadge = StudentBadge()
            studentBadge.data = currentDate

            if (badgeGanho != null) {
                studentBadge.badgeId = badgeGanho.id

            }

               //

            print(alunoRepetido!!.size)
            if(alunoRepetido!!.size == 0){
                studentBadge.studentId = aluno.id
                listaStudentBadge.add(studentBadge)
                studentBadgeRepository.save(studentBadge)

            }



        }

        val studentBadges = studentBadgeRepository.findByBadgeId(badgeGanho!!.id)
        val badges = studentBadges?.map {
            badgeRepository.getOne(it.badgeId)
        }?.distinct()

        model["tabela_studentbadge"] = badges


        //model["data"] = currentDate

        return "redirect:/teacher/detailBadge/${badgeGanho.id}"

    }

/*
    @GetMapping("/listofstudents")
    fun listStudentes():String{
        return "listofstudents"
    }
*/

//    @GetMapping("/test")
//    fun test(model: ModelMap): String {

//        val student = studentRepository.findByName("student1")
//        val badge = badgeRepository.findByName("teste2")
//
//        val sb = StudentBadge(data = "bla")
//        sb.student = student!!
//        studentBadgeRepository.save(sb)
//
//
//        return "redirect:/"
//    }

}

