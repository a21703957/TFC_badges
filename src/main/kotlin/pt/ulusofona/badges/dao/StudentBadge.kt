package pt.ulusofona.badges.dao

import java.util.*
import javax.persistence.*

@Entity
data class StudentBadge (
        @Id
        @GeneratedValue
        var id : Long = 0,
        @Column
        var data : String ? = null,

        var studentId: Long = 0,
        var badgeId: Long = 0
)