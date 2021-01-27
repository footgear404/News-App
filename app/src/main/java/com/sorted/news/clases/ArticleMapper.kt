package com.sorted.news.clases

class ArticleMapper {
    fun returnArticleEntity(article: ArticleResponse): ArticleEntity {
        return ArticleEntity(
            title = article.title,
            author = article.author,
            source = article.source?.name ?: "Oops, have no source",
            urlToImage = article.urlToImage,
            description = article.description,
            publishedAt = article.publishedAt
        )
    }
}