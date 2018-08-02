package db.race.condition

import grails.transaction.Transactional

@Transactional
class BookService {

    Book findOrCreateBook(Author author, String title) {

        log.debug "Called findOrCreateBook"

        // Pause here to simulate latency
        sleep 3_000

        def book = new Book(title: title).save()

        if (book) {
            author.addToBooks(book)
            author.save()
        }

        log.debug "Done"

        return book
    }
}
