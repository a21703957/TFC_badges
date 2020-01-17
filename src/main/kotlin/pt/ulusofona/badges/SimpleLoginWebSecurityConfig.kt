package pt.ulusofona.badges

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ResourceLoader
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import pt.ulusofona.badges.security.BadgesSecurityConfig
import java.util.logging.Logger


@Profile("!deisi")
@Configuration
@EnableWebSecurity
class SimpleLoginWebSecurityConfig: BadgesSecurityConfig() {

    val LOG = Logger.getLogger(this.javaClass.name)

    @Autowired
    lateinit var resourceLoader: ResourceLoader

    override fun configure(http: HttpSecurity) {

        super.configure(http)

        http
                .csrf().disable().httpBasic()
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .and().logout()
                .permitAll()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(inMemoryUserDetailsManager())
    }

    @Bean
    fun inMemoryUserDetailsManager(): InMemoryUserDetailsManager {

        LOG.info("Using inMemoryAuthentication")

        val usersList = mutableListOf<UserDetails>()

        if (resourceLoader.getResource("classpath:users.csv").exists()) {

            LOG.info("Found users.csv file. Will load user details from there.")

            val usersFile = resourceLoader.getResource("classpath:users.csv").file
            usersFile.readLines().forEach {
                if (it != "username;password;roles") {  // skip header
                    val (username, password, rolesStr) = it.split(";")
                    val roles = rolesStr.split(",").toTypedArray()
                    usersList.add(User.withUsername(username).password(password).roles(*roles).build())
                }
            }

            LOG.info("Loaded ${usersList.size} users")

        } else {

            LOG.info("Didn't find users.csv file. Will create default users.")

            usersList.add(User.withUsername("student1").password("{noop}123").roles("STUDENT").build())
            usersList.add(User.withUsername("teacher1").password("{noop}123").roles("TEACHER").build())
            usersList.add(User.withUsername("admin").password("{noop}123").roles("TEACHER", "BADGES_ADMIN").build())
        }

        return InMemoryUserDetailsManager(usersList)
    }
}