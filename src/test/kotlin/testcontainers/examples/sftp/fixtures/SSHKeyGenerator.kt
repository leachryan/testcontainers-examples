package testcontainers.examples.sftp.fixtures

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class SSHKeyGenerator {

    data class SSHKeyPairPath(
        val publicKeyPath: Path,
        val privateKeyPath: Path
    )

    fun createSSHKeysDirectory(prefix: String = "ssh-keys"): Path = Files.createTempDirectory(prefix)

    fun createPublicKeyPath(
        directory: Path,
        filename: String = "id_rsa.pub"
    ): Path = directory.resolve(filename)

    fun createPrivateKeyPath(
        directory: Path,
        filename: String = "id_rsa"
    ): Path = directory.resolve(filename)

    fun createSSHKeyPairPath(
        directoryPrefix: String = "ssh-keys",
        publicKeyFilename: String = "id_rsa.pub",
        privateKeyFilename: String = "id_rsa"
    ): SSHKeyPairPath {
        val directory = createSSHKeysDirectory(directoryPrefix)
        val publicKeyPath = createPublicKeyPath(directory, publicKeyFilename)
        val privateKeyPath = createPrivateKeyPath(directory, privateKeyFilename)

        return SSHKeyPairPath(publicKeyPath, privateKeyPath)
    }

    fun generateSSHKeys(
        privateKeyPath: Path
    ) {
        val command = listOf(
            "ssh-keygen",
            "-t", "rsa",
            "-b", "4096",
            "-f", privateKeyPath.toString(),
            "-N", ""
        )

        val processBuilder = ProcessBuilder(command)
        processBuilder.directory(File(System.getProperty("user.dir")))
        processBuilder.inheritIO().start().waitFor()
    }
}