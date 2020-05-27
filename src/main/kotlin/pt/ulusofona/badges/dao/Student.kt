package pt.ulusofona.badges.dao

import javax.persistence.*

@Entity
data class Student(
        @Id
        @GeneratedValue
        var id : Long = 0,
        @Column(nullable = false)
        var name : String? = null

) {
    @ManyToMany(cascade = arrayOf(CascadeType.MERGE ))
        @JoinTable(name = "student_badge",
                joinColumns = arrayOf(JoinColumn(name = "student_id", referencedColumnName = "id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "badge_id", referencedColumnName = "id")))

    var badges: Set<Badge> = HashSet<Badge>()


}