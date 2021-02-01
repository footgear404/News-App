package com.sorted.news.clases

class ArticleMapper {

    fun returnArticleListEntity(articleList: List<ArticleResponse>): List<ArticleEntity> {
        return articleList.map { item ->
            ArticleEntity(
                title = item.title,
                author = item.author,
                source = item.source?.name ?: "Oops, have no source",
                urlToImage = item.urlToImage,
                description = item.description,
                publishedAt = item.publishedAt
            )
        }

    }

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