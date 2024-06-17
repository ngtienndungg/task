package vn.dungnt.nothing.data.mappers

import vn.dungnt.nothing.data.base.Mapper
import vn.dungnt.nothing.data.models.BookModel
import vn.dungnt.nothing.domain.entities.BookEntity

class BookMapper : Mapper<BookModel, BookEntity> {
    override fun toEntity(model: BookModel): BookEntity {
        return BookEntity(
            model.id,
            model.title,
            model.subtitle,
            model.category,
            model.author,
            model.publishedYear,
            model.isbn,
            model.summary,
            model.avatar
        )
    }

    override fun toModel(entity: BookEntity): BookModel {
        return BookModel().also {
            it.id = entity.id
            it.title = entity.title
            it.subtitle = entity.subtitle
            it.category = entity.category
            it.author = entity.author
            it.publishedYear = entity.publishedYear
            it.isbn = entity.isbn
            it.summary = entity.summary
            it.avatar = entity.avatar
        }
    }
}

class BookListMapper : Mapper<List<BookModel>, List<BookEntity>> {

    private val _bookMapper = BookMapper()

    override fun toEntity(model: List<BookModel>): List<BookEntity> {
        return model.map { _bookMapper.toEntity(it) }
    }

    override fun toModel(entity: List<BookEntity>): List<BookModel> {
        return entity.map { _bookMapper.toModel(it) }
    }

}