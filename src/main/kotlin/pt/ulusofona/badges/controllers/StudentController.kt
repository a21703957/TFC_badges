package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.repositories.BadgeRepository
import pt.ulusofona.badges.repositories.StudentRepository
import java.security.Principal

@Controller
@RequestMapping("/student")
class StudentController(
        val studentRepository: StudentRepository,
        val badgeRepository: BadgeRepository
) {
    @GetMapping("/")
    fun index() : String{
        return "redirect:/student/allBadges";
    }

    @GetMapping("/allBadges")
    fun listofAllBadges(model : ModelMap, principal: Principal): String{
        model["badges"] = badgeRepository.findAll()
        var student = studentRepository.findByName(principal.name)
        if(student==null) {
            student = pt.ulusofona.badges.dao.Student(
                    name = principal.name
            )
            studentRepository.save(student)
        }

        return "listOfAllBadges"
    }


}