package pt.ulusofona.badges.repositories

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.badges.dao.Student

interface StudentRepository: JpaRepository<Student, Long> {

}