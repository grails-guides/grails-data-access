package example

import grails.gorm.transactions.Transactional

class AuthorController {

    static responseFormats = ['json']
    static allowedMethods = [index: 'GET', show: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Author.list(params), model: [authorCount: Author.count()]
    }

    def show(Long id) {
        respond Author.get(id)
    }

    @Transactional
    def save() {
        def author = new Author(request.JSON as Map)
        if (!author.validate()) {
            respond author.errors, status: 422
            return
        }
        author.save(failOnError: true, flush: true)
        respond author, status: 201
    }

    @Transactional
    def update(Long id) {
        def author = Author.get(id)
        if (!author) {
            render status: 404
            return
        }
        author.properties = request.JSON
        if (!author.validate()) {
            respond author.errors, status: 422
            return
        }
        author.save(failOnError: true, flush: true)
        respond author
    }

    @Transactional
    def delete(Long id) {
        def author = Author.get(id)
        if (!author) {
            render status: 404
            return
        }
        author.delete(flush: true)
        render status: 204
    }
}
