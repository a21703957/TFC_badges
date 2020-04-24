package pt.ulusofona.badges.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.badges.dao.Badge
import pt.ulusofona.badges.dao.Student
import pt.ulusofona.badges.dao.Teacher

interface BadgeRepository: JpaRepository<Badge, Long> {
    fun findByTeacher(teacher: Teacher): List<Badge>?
    fun findByName(name: String): Badge?
}