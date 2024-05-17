package testcontainers.examples.cockroachdb.integration.jpa

import io.kotest.matchers.shouldBe
import testcontainers.examples.persistence.entities.BookEntity
import testcontainers.examples.persistence.repositories.BookRepository
import java.util.UUID

class BookRepositoryIntegrationTest(
    bookRepository: BookRepository
): JpaDatabaseSpec({

    Given("a book entity") {
        val id = UUID.randomUUID()
        val bookEntity = BookEntity(
            id = id,
            title = "The Shining"
        )

        When("the book entity is saved") {
            bookRepository.save(bookEntity)

            Then("it can be queried via the database") {
                val foundBookEntity = bookRepository.findById(id)

                foundBookEntity.isPresent shouldBe true
                foundBookEntity.get().id shouldBe id
                foundBookEntity.get().title shouldBe "The Shining"
            }
        }
    }
})