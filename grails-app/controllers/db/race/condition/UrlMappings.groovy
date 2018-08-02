package db.race.condition

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/author/addToBooks"(controller: 'author', action: 'addToBooks', method: 'post')
        "/author/clear"(controller: 'author', action: 'clear', method: 'post')

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
