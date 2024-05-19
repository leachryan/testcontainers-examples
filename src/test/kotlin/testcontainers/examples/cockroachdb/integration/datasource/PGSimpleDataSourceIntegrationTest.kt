package testcontainers.examples.cockroachdb.integration.datasource

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.postgresql.ds.PGSimpleDataSource
import testcontainers.examples.cockroachdb.fixtures.CockroachDbContainer

class PGSimpleDataSourceIntegrationTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf
    Given("a cockroach database testcontainer") {
        val databaseName = "test_db"
        val cockroachDbContainer = CockroachDbContainer()
        cockroachDbContainer.start()
        Thread.sleep(2000)

        cockroachDbContainer.createDatabase(databaseName)

        When("a simple data source is configured") {
            val dataSource = PGSimpleDataSource()

            dataSource.serverNames = listOf(cockroachDbContainer.host).toTypedArray()
            dataSource.portNumbers = listOf(cockroachDbContainer.getMappedPort(26257)).toIntArray()
            dataSource.databaseName = databaseName
            dataSource.user = "root"

            Then("the data source connection is valid") {
                val connection = dataSource.connection

                connection.isValid(1000) shouldBe true
            }
        }
    }
})