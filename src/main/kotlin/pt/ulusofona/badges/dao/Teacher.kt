package pt.ulusofona.badges.dao

import javax.persistence.*

@Entity
data class Teacher(
        @Id
        @GeneratedValue
        var id : Long = 0,

        @Column(nullable = false)
        var name : String? = null
){
    @OneToMany(mappedBy = "teacher")
    val badges : MutableSet<Badge> = HashSet()
}


