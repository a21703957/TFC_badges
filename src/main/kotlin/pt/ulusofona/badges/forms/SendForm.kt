package pt.ulusofona.badges.forms

import org.springframework.format.annotation.DateTimeFormat
import pt.ulusofona.badges.dao.Badge
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty

data class SendForm(
        @field:NotEmpty(message = "Alunos Obrigatório")
        var alunos : String? = null,
        @field:NotEmpty(message = "Nome Badge Origatório")
        var badge : String? = null

)