package blog

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ExtendWith(SpringExtension::class)
@WebMvcTest
class HttpApiTests(@Autowired val mockMvc: MockMvc) {

	@MockBean
	private lateinit var userRepository: UserRepository

	@MockBean
	private lateinit var articleRepository: ArticleRepository

	@MockBean
	private lateinit var markdownConverter: MarkdownConverter

	@Test
	fun `List articles`() {
		val juergen = User("springjuergen", "Juergen", "Hoeller")
		val spring5Article = Article("spring-5-ga", "Spring Framework 5.0 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
		val spring43Article = Article("spring-4.3-ga", "Spring Framework 4.3 goes GA", "Dear Spring community ...", "Lorem ipsum", juergen)
		`when`(articleRepository.findAllByOrderByAddedAtDesc()).thenReturn(listOf(spring5Article, spring43Article))
		`when`(markdownConverter.invoke(any())).thenAnswer { it.arguments[0] }
		mockMvc.perform(get("/api/article/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("\$.[0].author.login").value(juergen.login))
				.andExpect(jsonPath("\$.[0].slug").value(spring5Article.slug))
				.andExpect(jsonPath("\$.[1].author.login").value(juergen.login))
				.andExpect(jsonPath("\$.[1].slug").value(spring43Article.slug))
	}

	@Test
	fun `List users`() {
		val juergen = User("springjuergen", "Juergen", "Hoeller")
		val smaldlini = User("smaldini", "Stéphane", "Maldini")
		`when`(userRepository.findAll()).thenReturn(listOf(juergen, smaldlini))
		mockMvc.perform(get("/api/user/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("\$.[0].login").value(juergen.login))
				.andExpect(jsonPath("\$.[1].login").value(smaldlini.login))
	}

}