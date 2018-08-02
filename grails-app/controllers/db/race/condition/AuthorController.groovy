package db.race.condition

import grails.converters.JSON
import grails.transaction.Transactional

class AuthorController {

    def bookService

    def addToBooks(String name, String title) {
        if (!name || !title) {
            response.sendError 422, 'Must include both name and title'
            return
        }

        // Pessimistic locking here, so we should be able to handle concurrency, right?
        // according to http://gorm.grails.org/6.1.x/hibernate/manual/#locking I would think this should make our test pass
        def existingAuthor = Author.findByName(name, [lock: true])
        if (!existingAuthor) {
            response.sendError 422, "Author not found with name $name"
            return
        }

        def foundBook = bookService.findOrCreateBook(existingAuthor, title)

        render text: (foundBook as JSON).toString(true), contentType: 'application/json'
    }

    @Transactional
    def clear() {
        Author.all.each {
            it.books.clear()
        }
        Book.deleteAll(Book.all)

        render status: 204
    }
}
