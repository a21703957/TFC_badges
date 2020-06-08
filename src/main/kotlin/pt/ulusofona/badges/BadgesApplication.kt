package pt.ulusofona.badges

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import javax.imageio.ImageIO

@SpringBootApplication
class BadgesApplication

fun main(args: Array<String>) {

	runApplication<BadgesApplication>(*args)



}
