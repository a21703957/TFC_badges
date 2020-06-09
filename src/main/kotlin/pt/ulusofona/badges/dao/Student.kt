package pt.ulusofona.badges.dao

import javax.persistence.*

@Entity
data class Student(
        @Id
        @GeneratedValue
        var id : Long = 0,

        @Column(nullable = false)
        var name : String? = null,

        @Transient
        var dateOfBadge: String? = null

) {

}