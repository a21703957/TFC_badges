package pt.ulusofona.badges.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.badges.dao.Student
import pt.ulusofona.badges.dao.StudentBadge

interface StudentBadgeRepository: JpaRepository<StudentBadge, Long> {
    fun findByStudentIdAndBadgeId(studentId: Long, badgeId: Long): List<StudentBadge>?
    fun findByStudentId(studentId: Long): List<StudentBadge>?
    fun findByBadgeId(badgeId: Long) : List<StudentBadge>?



}