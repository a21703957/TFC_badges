package pt.ulusofona.badges.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import java.security.Principal
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class GlobalControllerAdvice {

    @Autowired
    private val userDetailsManager: UserDetailsManager? = null

    @ModelAttribute("username")
    fun getUsername(principal: Principal?) : String {
        if (principal != null) {
            return principal.name
        } else {
            return ""
        }
    }

    @ModelAttribute("isTeacher")
    fun isTeacher(request: HttpServletRequest) : Boolean {
        return request.isUserInRole("TEACHER")
    }

    @ModelAttribute("demoMode")
    fun isDemoMode() : Boolean {
        if (userDetailsManager != null && userDetailsManager is InMemoryUserDetailsManager) {
            return userDetailsManager.userExists("student1") ||
                    userDetailsManager.userExists("teacher1") ||
                    userDetailsManager.userExists("admin")
        } else {
            return false
        }
    }
}