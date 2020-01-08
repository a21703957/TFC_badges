package pt.ulusofona.badges.forms

import pt.ulusofona.badges.dao.Validacao
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class BadgeForm (
        @field:NotEmpty(message = "Nome Obrigatório")
        var name : String? = null,
        @field:NotEmpty(message = "Descrição Obrigatória")
        var description : String? = null,
      /*  @field:NotEmpty(message = "Validação Obrigatória")
        var validacao : String? = null,*/
        @field:NotEmpty(message="Campo Obrigatório")
        var toWin : String?= null,
        @field:NotNull(message = "Error: Language must not be empty")
        var validacao: Validacao? = null

)

