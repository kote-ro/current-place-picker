package main.util

import groovy.json.JsonSlurper

class JsonHandler {
    List<Object> generateVenuesList(String jsonAsText){
        def jsonSlurper = new JsonSlurper()
        List<Object> venuesList = jsonSlurper.parseText(jsonAsText)?.get("response")?.get("venues") // список ближайших мест
        venuesList
    }
}
