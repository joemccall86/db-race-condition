package db.race.condition

class Author {

    String name
    static hasMany = [books: Book]

    static constraints = {
    }
}
