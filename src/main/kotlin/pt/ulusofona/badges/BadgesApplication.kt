package pt.ulusofona.badges

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:badges.properties")
class BadgesApplication: SpringBootServletInitializer() {

	override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
			application.sources(BadgesApplication::class.java).properties("spring.config.name: badges")

}

fun main(args: Array<String>) {
	System.setProperty("spring.config.name", "badges")
	runApplication<BadgesApplication>(*args)
}
