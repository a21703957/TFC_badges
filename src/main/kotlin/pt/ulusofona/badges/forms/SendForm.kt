package pt.ulusofona.badges.forms

import pt.ulusofona.badges.dao.Badge
import javax.validation.constraints.NotEmpty

data class SendForm(
        @field:NotEmpty(message = "Alunos Obrigatório")
        var alunos : String? = null,
        @field:NotEmpty(message = "Nome Badge Origatório")
        var badge : Badge? = null
)