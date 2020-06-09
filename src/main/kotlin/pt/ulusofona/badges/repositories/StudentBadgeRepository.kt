package pt.ulusofona.badges.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.badges.dao.StudentBadge

interface StudentBadgeRepository: JpaRepository<StudentBadge, Long> {

    fun findByStudentId(studentId: Long): List<StudentBadge>?
    fun findByBadgeId(badgeId: Long) : List<StudentBadge>?


}