package pt.ulusofona.badges.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.repositories.BadgeRepository
import pt.ulusofona.badges.repositories.StudentRepository

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
    fun listofAllBadges(model : ModelMap): String{
            model["badges"] = badgeRepository.findAll()

        return "listOfAllBadges"
    }


}