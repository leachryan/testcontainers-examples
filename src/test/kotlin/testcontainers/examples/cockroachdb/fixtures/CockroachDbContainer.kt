package testcontainers.examples.cockroachdb.fixtures

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class CockroachDbContainer : GenericContainer<CockroachDbContainer>(DockerImageName.parse("cockroachdb/cockroach:v23.2.0")) {

    init {
        withExposedPorts(26257)
        withCommand("start-single-node --insecure")
    }

    fun createDatabase(databaseName: String) {
        if (!isRunning) throw RuntimeException("container is not yet running")

        val result = execInContainer("/cockroach/cockroach", "sql", "--insecure", "-e", "create database if not exists $databaseName;")
        if (result.exitCode != 0) {
            throw RuntimeException("error creating database: $databaseName")
        }
    }

    fun jdbcUrl(databaseName: String): String = "jdbc::postgresql://$host:${getMappedPort(26257)}/$databaseName?sslmode=disable"
}