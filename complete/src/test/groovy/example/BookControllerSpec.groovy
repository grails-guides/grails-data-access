package example

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class BookControllerSpec extends Specification implements ControllerUnitTest<BookController>, DataTest {

    Class<?>[] getDomainClassesToMock() {
        [Author, Book, Tag] as Class[]
    }

    void setup() {
        controller.bookService = new BookService()
    }

    void 'index lists books'() {
        given:
        def author = new Author(name: 'Ada').save(flush: true)
        new Book(title: 'Sample', price: 15.00, author: author).save(flush: true)

        when:
        controller.index()

        then:
        model.bookList
        model.bookCount == 1
    }

    void 'byAuthor returns 404 when author missing'() {
        when:
        controller.byAuthor(99L)

        then:
        response.status == 404
    }
}
