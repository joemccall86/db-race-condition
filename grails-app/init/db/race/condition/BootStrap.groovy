package db.race.condition

class BootStrap {

    def init = { servletContext ->

        // Create at least one author
        Author.withNewTransaction {
            new Author(name: 'Dr. Seuss').save(failOnError: true)
        }
    }
    def destroy = {
    }
}
