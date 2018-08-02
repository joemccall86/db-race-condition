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

        def existingAuthor = Author.findByName(name)
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
