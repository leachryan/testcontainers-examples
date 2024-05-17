package testcontainers.examples.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "books")
class BookEntity(
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    var id: UUID,

    @Column(name = "title")
    var title: String
)