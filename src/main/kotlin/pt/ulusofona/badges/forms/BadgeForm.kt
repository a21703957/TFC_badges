package pt.ulusofona.badges.forms

import pt.ulusofona.badges.dao.Category
import pt.ulusofona.badges.dao.Teacher
import pt.ulusofona.badges.dao.Validacao
import pt.ulusofona.badges.dao.Year
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class BadgeForm (
        @field:NotEmpty(message = "Nome Obrigatório")
        var name : String? = null,
        /*@field:NotEmpty(message = "Disciplina Obrigatória")
        var subject : String? = "Universal",*/
        @field:NotNull(message = "Error: Language must not be empty")
        var year : Year? = null,
        @field:NotNull(message = "Erro:Subject must not be empty")
        var LEI : Boolean = false,
        @field:NotNull(message = "Erro:Subject must not be empty")
        var LEIRT : Boolean = false,
        @field:NotNull(message = "Erro:Subject must not be empty")
        var LIG : Boolean = false,
        @field:NotNull(message = "Error: Category must not be empty")
        var category: Category? = null,
        @field:NotEmpty(message = "Descrição Obrigatória")
        var description : String? = null,
        @field:NotEmpty(message="Campo Obrigatório")
        var toWin : String?= null,
        @field:NotNull(message = "Error: Language must not be empty")
        var validacao: Validacao? = null,
        var teacherName : String? = null
)

