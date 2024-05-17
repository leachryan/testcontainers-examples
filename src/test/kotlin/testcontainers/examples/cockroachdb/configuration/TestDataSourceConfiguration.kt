package testcontainers.examples.cockroachdb.configuration

import org.flywaydb.core.Flyway
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import testcontainers.examples.cockroachdb.fixtures.CockroachDbContainer
import javax.sql.DataSource

@TestConfiguration
class TestDataSourceConfiguration {

    @Primary
    @Bean(initMethod = "start")
    fun cockroachDbContainer(): CockroachDbContainer {
        val container = CockroachDbContainer()
        val databaseName = "test_db"

        // Let the container start up
        container.start()
        Thread.sleep(4000)

        container.createDatabase(databaseName)

        return container
    }

    @Primary
    @Bean
    @DependsOn("cockroachDbContainer")
    fun dataSource(cockroachDbContainer: CockroachDbContainer): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.url = cockroachDbContainer.jdbcUrl("test_db")
        dataSource.username = "root"

        return dataSource
    }

    @Primary
    @Bean
    @DependsOn("cockroachDbContainer")
    fun entityManagerFactory(cockroachDbContainer: CockroachDbContainer): LocalContainerEntityManagerFactoryBean {
        val entityManager = LocalContainerEntityManagerFactoryBean()
        entityManager.dataSource = dataSource(cockroachDbContainer)
        entityManager.setPackagesToScan("testcontainers.examples.persistence.entities")

        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        entityManager.jpaVendorAdapter = vendorAdapter

        return entityManager
    }

    @Primary
    @Bean
    @DependsOn("cockroachDbContainer")
    fun transactionManager(cockroachDbContainer: CockroachDbContainer): JpaTransactionManager {
        val transactionManager = JpaTransactionManager()

        transactionManager.entityManagerFactory = entityManagerFactory(cockroachDbContainer).`object`

        return transactionManager
    }

    @Primary
    @Bean
    @DependsOn("cockroachDbContainer")
    fun flyway(cockroachDbContainer: CockroachDbContainer): Flyway {
        val flyway = Flyway.configure()
            .dataSource(dataSource(cockroachDbContainer))
            .locations("classpath:db/migrations")
            .load()

        flyway.migrate()

        return flyway
    }
}