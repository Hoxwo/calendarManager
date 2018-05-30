package calendarmanager

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        '/events'(resources: 'calendarEvent') {
            collection {
                '/day'(controller: 'calendarEvent', action: 'day')
                '/week'(controller: 'calendarEvent', action: 'week')
                '/month'(controller: 'calendarEvent', action: 'month')
            }
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
