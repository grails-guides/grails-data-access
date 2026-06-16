package example

import grails.testing.gorm.DataTest
import spock.lang.Specification

class AuthorSpec extends Specification implements DataTest {

    Class[] getDomainClassesToMock() {
        [Author, Book] as Class[]
    }

    void "a valid author passes validation"() {
        when:
        Author author = new Author(name: 'Ada Lovelace', email: 'ada@example.com')

        then:
        author.validate()
    }

    void "email must be unique"() {
        given:
        new Author(name: 'First', email: 'same@example.com').save(flush: true)

        when:
        Author duplicate = new Author(name: 'Second', email: 'same@example.com')

        then:
        !duplicate.validate()
        duplicate.errors['email'].code == 'unique'
    }

    void "name cannot be blank"() {
        when:
        Author author = new Author(name: '', email: 'x@example.com')

        then:
        !author.validate()
        author.errors['name'].code in ['blank', 'nullable']
    }
}
