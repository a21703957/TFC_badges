package pt.ulusofona.badges.dao

import java.util.*
import javax.persistence.*

@Entity
data class StudentBadge (
        @Id
        @GeneratedValue
        var id : Long = 0,
        @Column
        var data : String ? = null
){

    @ManyToOne
    @JoinColumn(name="student_id", insertable = false, updatable = false)
    lateinit var student: Student

    @ManyToOne
    @JoinColumn(name="badge_id", insertable = false, updatable = false)
    lateinit var badge : Badge


}