package blog

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository, private val markdownConverter: MarkdownConverter) {

	@GetMapping("/")
	fun findAll() = repository.findAllByOrderByAddedAtDesc()

	@GetMapping("/{slug}")
	fun findOne(@PathVariable slug: String, @RequestParam converter: String?) = when (converter) {
		"markdown" -> repository.findById(slug).map { it.copy(
				headline = markdownConverter.invoke(it.headline),
				content = markdownConverter.invoke(it.content)) }
		null -> repository.findById(slug)
		else -> throw IllegalArgumentException("Only markdown converter is supported")
	}
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAll()

	@GetMapping("/{login}")
	fun findOne(@PathVariable login: String) = repository.findById(login)
}