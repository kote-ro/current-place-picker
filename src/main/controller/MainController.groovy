package main.controller

import groovy.json.JsonSlurper
import main.filter.Filters
import main.util.JsonHandler
import main.util.RequestSender

class MainController {
    private JsonHandler jsonHandler
    private RequestSender requestSender
    private Filters filters
    private UserInteraction userInteraction
    private final Integer RADIUS_OF_SEARCH = 100

    MainController(){
        this.jsonHandler = new JsonHandler()
        this.requestSender = new RequestSender()
        this.filters = new Filters()
        this.userInteraction = new UserInteraction()
    }

    void startMainProcess(){
        List<Object> coordinates = getCoordinatesFromFile()

        for(element in coordinates) {
            Double latitude = element?.get("latitude")
            Double longitude = element?.get("longitude")

            String jsonAsText = requestSender
                    .sendRequest(latitude, longitude, RADIUS_OF_SEARCH)
                    .get()

            List<Object> venueList = jsonHandler.generateVenuesList(jsonAsText)
            if(venueList.isEmpty()){
                println("Ближайших мест не найдено")
            }else {
                Map<Object, Double> venueAndVenueLikelihoods = filters.filterVenues(venueList)
                userInteraction.menu(venueAndVenueLikelihoods)
            }
        }
    }

    private List<Object> getCoordinatesFromFile(){
        def jsonSlurper = new JsonSlurper()
        List<Object> coordinates = jsonSlurper.parse(new File("src/resources/data/coordinates.json"))
        coordinates
    }
}
