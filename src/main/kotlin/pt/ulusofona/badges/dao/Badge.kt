package pt.ulusofona.badges.dao

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Badge (
        @Id @GeneratedValue
        var id : Long = 0,

        @Column(nullable = false)
        var name : String? = null,

        @Column(nullable = false)
        var description : String? = null/*,

        var validacao : String? = null,
        var toWin : String? = null*/
)