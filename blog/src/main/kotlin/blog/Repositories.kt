package blog

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : CrudRepository<Article, String> {

	fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

@Repository
interface UserRepository : CrudRepository<User, String>