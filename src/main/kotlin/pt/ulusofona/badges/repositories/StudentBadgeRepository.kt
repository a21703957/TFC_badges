package pt.ulusofona.badges.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.dao.Student
import pt.ulusofona.badges.dao.StudentBadge

interface StudentBadgeRepository: JpaRepository<StudentBadge, Long> {

    fun findByStudent(student: Student): List<Badge>?
    fun findByBadge(badge: Badge) : List<Student>?

}