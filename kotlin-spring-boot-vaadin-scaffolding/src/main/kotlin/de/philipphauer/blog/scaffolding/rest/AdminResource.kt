package de.philipphauer.blog.scaffolding.rest

import com.google.common.io.Resources
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.Properties


private const val LAST_MONGO_CHECK_TOLERANCE_MS = 120000L
private const val INITIAL_MONGO_CHECK_DELAY_MS = 2000L
private const val MONGO_CHECK_INTERVAL_MS = 20000L

@Controller
open class AdminResource(val mongo: MongoTemplate) {

    val versionDTO: VersionDTO by lazy { createVersionDTO() }

    @Volatile private var lastSuccessfulStatusCheck: Instant = Instant.now()

    @Scheduled(initialDelay = INITIAL_MONGO_CHECK_DELAY_MS, fixedDelay = MONGO_CHECK_INTERVAL_MS)
    fun checkMongoStatus() {
        val commandResult = mongo.executeCommand("{ping:1}")
        if (commandResult.ok()) {
            lastSuccessfulStatusCheck = Instant.now()
        }
    }

    @GetMapping("/status", produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    fun getStatus(): ResponseEntity<String> =
            if (lastSuccessfulStatusCheck.isAfter(Instant.now().minusMillis(LAST_MONGO_CHECK_TOLERANCE_MS)))
                ResponseEntity<String>("OK", HttpStatus.OK)
            else
                ResponseEntity<String>("MongoDB is not available since $lastSuccessfulStatusCheck", HttpStatus.INTERNAL_SERVER_ERROR)

    @GetMapping("/version", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    @ResponseBody
    fun getVersion() = versionDTO

    @GetMapping("favicon.ico")
    fun favicon() = "forward:/VAADIN/themes/mytheme/favicon.ico"

    //the values won't be set when you start the application in the IDE.
    private fun createVersionDTO() = readGitProperties().let {
        VersionDTO(name = "myapp",
                version = it.getProperty("api.version"),
                buildTime = it.getProperty("git.build.time"),
                commit = CommitVersionDTO(
                        revision = it.getProperty("git.commit.id"),
                        message = it.getProperty("git.commit.message.full"),
                        author = it.getProperty("git.commit.user.name"),
                        time = it.getProperty("git.commit.time"),
                        branch = it.getProperty("git.branch"))
        )
    }

    private fun readGitProperties(): Properties {
        val gitPropertyUrl = Resources.getResource("application.properties")
        val charSource = Resources.asCharSource(gitPropertyUrl, StandardCharsets.UTF_8)
        return Properties().apply { load(charSource.openBufferedStream()) }
    }
}

data class VersionDTO(
        val name: String,
        val version: String,
        val buildTime: String,
        val commit: CommitVersionDTO
)

data class CommitVersionDTO(
        val revision: String,
        val message: String,
        val author: String,
        val time: String,
        val branch: String
)
