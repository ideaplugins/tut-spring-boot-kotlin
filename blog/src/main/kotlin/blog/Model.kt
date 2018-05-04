package blog

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Article(
		@Id val slug: String,
		val title: String,
		val headline: String,
		val content: String,
		@ManyToOne @JoinColumn val author: User,
		val addedAt: LocalDateTime = LocalDateTime.now())

@Entity
data class User(
		@Id val login: String,
		val firstname: String,
		val lastname: String,
		val description: String? = null)