package pt.ulusofona.badges.repositories;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.badges.dao.Badge

import pt.ulusofona.badges.dao.Teacher

 interface TeacherRepository: JpaRepository<Teacher, Long> {
   fun findByName(name: String): Teacher?

 }