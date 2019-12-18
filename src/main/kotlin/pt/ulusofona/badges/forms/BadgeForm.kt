package pt.ulusofona.badges.forms

import javax.validation.constraints.NotEmpty

data class BadgeForm (
        @field:NotEmpty(message = "Nome Obrigatório")
        var name : String? = null,
        @field:NotEmpty(message = "Descrição Obrigatória")
        var description : String? = null

)

