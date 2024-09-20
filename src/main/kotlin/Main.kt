import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication(scanBasePackages = ["org.lib"])
@EnableMongoRepositories(basePackages = ["org.lib"])

class LibraryBackend

fun main(args: Array<String>) {
    runApplication<LibraryBackend>(*args)
}