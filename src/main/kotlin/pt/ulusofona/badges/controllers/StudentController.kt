package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.repositories.BadgeRepository
import pt.ulusofona.badges.repositories.StudentRepository
import java.security.Principal
import org.springframework.web.bind.annotation.PathVariable
import java.util.*
import org.springframework.ui.Model
import kotlin.collections.HashSet
import kotlin.collections.ArrayList


@Controller
@RequestMapping("/student")
class StudentController(
        val studentRepository: StudentRepository,
        val badgeRepository: BadgeRepository

) {
    private var badgesDisponiveis = HashSet<Badge>()
    @GetMapping("/")
    fun index() : String{
        return "redirect:/student/allBadges";
    }

    @GetMapping("/allBadges")
    fun listofAllBadges(model : ModelMap, principal: Principal): String{
        var badges = badgeRepository.findAll()

        var student = studentRepository.findByName(principal.name)

        if(student==null) {
            student = pt.ulusofona.badges.dao.Student(
                    name = principal.name
            )
            studentRepository.save(student)
        }



        var myBadges = student.badges

        for(badge in badges){
            for(b in myBadges){
                if(badge.equals(b)){
                    badge.assign = true
                }
            }
        }

        for(badge in badges){
            for (mybadge in myBadges){
                if(!((badge.name).equals(mybadge.name))){
                    badgesDisponiveis.add(badge)
                }

            }
        }
        model["badges"] = badges
        model["mybadges"] = myBadges

        print("disonive " + badgesDisponiveis.size)

        model["badgesDisponiveis"] = badgesDisponiveis

        print("Badegs aluno " + student.badges.size)



        return "index"
    }

    @GetMapping("{tab}")
    fun tab(@PathVariable tab: String): String {
        return if (Arrays.asList("tab1", "tab2", "tab3")
                        .contains(tab)) {
            "_$tab"
        } else "empty"

    }


}