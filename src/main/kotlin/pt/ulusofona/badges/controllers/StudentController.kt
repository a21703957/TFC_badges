package pt.ulusofona.badges.controllers

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
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
import org.springframework.web.server.ResponseStatusException
import pt.ulusofona.badges.dao.Category
import pt.ulusofona.badges.dao.Student
import pt.ulusofona.badges.repositories.StudentBadgeRepository
import javax.servlet.http.HttpServletRequest
import kotlin.collections.HashSet
import kotlin.collections.ArrayList


@Controller
@RequestMapping("/student")
class StudentController(
        val studentRepository: StudentRepository,
        val badgeRepository: BadgeRepository,
        val studentBadgeRepository : StudentBadgeRepository

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




        var myBadges = studentBadgeRepository.findByStudentId(student.id)?.map {
            badgeRepository.getOne(it.badgeId)
        }

        for(badge in badges){
            if (myBadges != null) {
                for(b in myBadges){
                    if(badge.equals(b)){
                        badge.assign = true
                    }
                }
            }
        }

        for(badge in badges){
            if (myBadges != null) {
                for (mybadge in myBadges){
                    if(!((badge.name).equals(mybadge.name))){
                        badgesDisponiveis.add(badge)
                    }
                }
            }
        }

        var softSkillsBadge: MutableList<Badge> = mutableListOf()
        var hybridSkillsBadge: MutableList<Badge> = mutableListOf()
        var hardSkillsBadge: MutableList<Badge> = mutableListOf()
        //Soft, Hybrid, Hard
        for(badge in badges){
            if(badge.category == Category.Soft){
                softSkillsBadge.add(badge)
            }else if(badge.category == Category.Hybrid){
                hybridSkillsBadge.add(badge)
            }else if(badge.category == Category.Hard){
                hardSkillsBadge.add(badge)
            }
        }

        var mySoftBadges: MutableList<Badge> = mutableListOf()
        var myhybridBadges: MutableList<Badge> = mutableListOf()
        var myhardBadges: MutableList<Badge> = mutableListOf()

        if (myBadges != null) {
            for(badge in myBadges){
                if(softSkillsBadge.contains(badge)){
                    mySoftBadges.add(badge)

                }
                if(hybridSkillsBadge.contains(badge)){
                    myhybridBadges.add(badge)

                }
                if(hardSkillsBadge.contains(badge)){
                    myhardBadges.add(badge)
                    print("ESTE:" + myhardBadges)
                }

            }
        }


        //Badges Gerais
        model["badges"] = badges
        model["mybadges"] = myBadges
        print("disonive " + badgesDisponiveis.size)
        model["badgesDisponiveis"] = badgesDisponiveis

       // print("Badegs aluno " + student.studentBadges)

        //Models para as tabs filtradas
        model["softskills"] = softSkillsBadge
        model["hybridskills"] = hybridSkillsBadge
        model["hardskills"] = hardSkillsBadge

        //Models para mostrar o número de badges adquiridos por categoria
        model["mySoftSkills"] = mySoftBadges.size
        model["myHybridSkills"] = myhybridBadges.size
        print("hS:" + myhybridBadges.size)
        model["myHardSkills"] = myhardBadges.size

        return "index"
    }

    @GetMapping("{tab}")
    fun tab(@PathVariable tab: String): String {
        return if (Arrays.asList("tab1", "tab2", "tab3", "tab4")
                        .contains(tab)) {
            "_$tab"
        } else "empty"

    }



    @GetMapping(value = ["/badgeDetail/{badgeId}"])
    fun detailPage(@PathVariable badgeId: Long, model:ModelMap, request: HttpServletRequest, principal: Principal): String{

        val badge = badgeRepository.findByIdOrNull(badgeId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

        model["badge"] = badge


        return "studentBadgeDetail"
    }


}