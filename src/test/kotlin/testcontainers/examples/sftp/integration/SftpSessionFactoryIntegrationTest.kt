package testcontainers.examples.sftp.integration

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.core.io.InputStreamResource
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory
import testcontainers.examples.sftp.fixtures.SSHKeyGenerator
import testcontainers.examples.sftp.fixtures.SSHServerContainer
import kotlin.io.path.inputStream

class SftpSessionFactoryIntegrationTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    // Set up the testcontainer and provide connection details to session factory
    val sshKeyGenerator = SSHKeyGenerator()
    val keyPairPath = sshKeyGenerator.createSSHKeyPairPath()

    sshKeyGenerator.generateSSHKeys(keyPairPath.privateKeyPath)

    Given("a running ssh server in a container") {
        val sshServerContainer = SSHServerContainer(keyPairPath.publicKeyPath)

        sshServerContainer.start()
        Thread.sleep(2000)

        When("the sftp session factory is configured") {
            val sftpSessionFactory = DefaultSftpSessionFactory()

            val privateKeyResource = InputStreamResource(keyPairPath.privateKeyPath.inputStream())

            sftpSessionFactory.setPrivateKey(privateKeyResource)
            sftpSessionFactory.setUser("testUser")
            sftpSessionFactory.setHost(sshServerContainer.host)
            sftpSessionFactory.setPort(sshServerContainer.getMappedPort(2222))
            sftpSessionFactory.setAllowUnknownKeys(true)

            Then("the sftp session can connect to the ssh server container") {
                val session = sftpSessionFactory.session

                session.list("/") shouldNotBe null
            }
        }
    }
})