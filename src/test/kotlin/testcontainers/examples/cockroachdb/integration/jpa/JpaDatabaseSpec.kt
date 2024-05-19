package testcontainers.examples.cockroachdb.integration.jpa

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import testcontainers.examples.cockroachdb.configuration.TestDataSourceConfiguration

@ContextConfiguration(classes = [
    TestDataSourceConfiguration::class
])
@EnableJpaRepositories(basePackages = ["testcontainers.examples.persistence.repositories"])
@EntityScan(basePackages = ["testcontainers.examples.persistence.entities"])
abstract class JpaDatabaseSpec(body: BehaviorSpec.() -> Unit = {}) : BehaviorSpec(body) {
    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf
}