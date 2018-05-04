package blog

import com.samskivert.mustache.Mustache
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BlogApplication {

	@Bean
	fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
			Mustache.compiler().escapeHTML(false).withLoader(loader)

	@Bean
	fun databaseInitializer(userRepository: UserRepository, articleRepository: ArticleRepository) = CommandLineRunner {
		val smaldlini = User("smaldini", "Stéphane", "Maldini")
		userRepository.save(smaldlini)
		articleRepository.save(Article(
				"reactor-bismuth-is-out",
				"Reactor Bismuth is out",
				"Lorem ipsum",
				"dolor **sit** amet https://projectreactor.io/",
				smaldlini

		))
		articleRepository.save(Article(
				"reactor-aluminium-has-landed",
				"Reactor Aluminium has landed",
				"Lorem ipsum",
				"dolor **sit** amet https://projectreactor.io/",
				smaldlini

		))
	}

}

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
