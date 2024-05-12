package testcontainers.examples.sftp.fixtures

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile
import java.nio.file.Path

class SSHServerContainer(
    publicKeyPath: Path
): GenericContainer<SSHServerContainer>(DockerImageName.parse("lscr.io/linuxserver/openssh-server:latest")) {
    init {
        withEnv("PUID", "1000")
        withEnv("PGID", "1000")
        withEnv("TZ", "Etc/UTC")
        withEnv("PASSWORD_ACCESS", "TRUE")
        withEnv("PUBLIC_KEY_FILE", "/ssh-keys/id_rsa.pub")
        withEnv("USER_NAME", "testUser")
        withEnv("LOG_STDOUT", "")
        withExposedPorts(2222)
        withCopyFileToContainer(MountableFile.forHostPath(publicKeyPath), "/ssh-keys/id_rsa.pub")
    }
}