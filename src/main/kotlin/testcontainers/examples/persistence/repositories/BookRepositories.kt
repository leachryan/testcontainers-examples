package testcontainers.examples.persistence.repositories

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import testcontainers.examples.persistence.entities.BookEntity
import java.util.UUID

interface BookPort {
    // Custom book queries
}

class BookPortImpl(
    @PersistenceContext
    private val entityManager: EntityManager
): BookPort {
    // Custom book queries impl
}

@Repository
interface BookRepository: JpaRepository<BookEntity, UUID>, BookPort