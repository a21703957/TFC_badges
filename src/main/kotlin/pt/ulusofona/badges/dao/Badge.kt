package pt.ulusofona.badges.dao

import javax.persistence.*

enum class Validacao {
        Externa, Interna
}

enum class Year {
    Todos,Primeiro, Segundo, Terceiro
}

enum class Curso{
    LEI, LEIRT, LIG
}

enum class Category{
        Soft, Hybrid, Hard
}

enum class Avaliation{
        Sim, Nao
}

@Entity
data class Badge (
        @Id @GeneratedValue
        var id : Long = 0,

        @Column(nullable = false)
        var name : String? = null,

        @Column(nullable = false)
        var LEI : Boolean = false,
        @Column(nullable = false)
        var LEIRT : Boolean = false,
        @Column(nullable = false)
        var LIG : Boolean = false,

       /* @Column(columnDefinition = "varchar(255) default 'Universal'")
        var subject : String? = null,*/
        @Column(nullable = false)
        var year : Year = Year.Todos,

        @Column(nullable=false)
        var avaliation: Avaliation = Avaliation.Sim,

        @Column(nullable = false)
        var category: Category = Category.Soft,

        @Column(nullable = false)
        var description : String? = null,

        @Column(nullable=false)
        var toWin : String? = null,

        @Column(nullable = false)
        var validacao: Validacao = Validacao.Externa


){

        @ManyToOne
        lateinit var teacher: Teacher

        @ManyToMany(mappedBy = "badges")
        var students: List<Student> = mutableListOf<Student>()

}




