package main.util

import groovy.json.JsonSlurper

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RequestSender {
    private String currentDate = getCurrentDateString()
    private Tuple2<String, String> clientAuthorization = getAuthorizationInfo()

    Optional<String> sendRequest(Double latitude, Double longitude, Integer radius){
        def get = new URL("https://api.foursquare.com/v2/venues/search?" +
                "ll=${latitude},${longitude}" + // широта и долгота
                "&client_id=${clientAuthorization.get(0)}" +
                "&client_secret=${clientAuthorization.get(1)}" +
                "&radius=${radius}" + // радиус поиска
                "&v=${currentDate}") // дата (YYYYMMDD - формат строки)
                .openConnection()

        if(get.getResponseCode().equals(200)) {
            Optional.of(get.getInputStream().getText())
        }else {
            Optional.empty()
        }
    }

    private String getCurrentDateString(){
        LocalDate date = LocalDate.now()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        date.format(formatter)
    }

    private Tuple2<String, String> getAuthorizationInfo(){
        def jsonSlurper = new JsonSlurper()
        def clientAuthorization = jsonSlurper.parse(new File("src/resources/properties/client_authorization.json"))
        new Tuple2(clientAuthorization?.get("client_id"), clientAuthorization?.get("client_secret"))
    }
}
