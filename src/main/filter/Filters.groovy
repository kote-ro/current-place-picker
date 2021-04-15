package main.filter

import groovy.json.JsonSlurper

class Filters {
    private final Integer RADIUS_OF_POTENTIAL_VENUES = 20
    private Map<String, Integer> userPreferences = getUserPreferences()

    public Map<Object, Double> filterVenues(List<Object> venueList){
        // словарь, ключ - заведение, значение - индекс предпочтений
        final Map<Object, Double> venueAndVenueLikelihoods = new HashMap<>()

        for(venue in venueList){
            Integer venueDistance = venue?.get("location")?.get("distance") // растояние до заведения
            if(venueDistance <= RADIUS_OF_POTENTIAL_VENUES){
                Double likelihood = calculateLikelihood(venue, venueDistance) // вычисление вероятности
                venueAndVenueLikelihoods.put(venue, likelihood)               // запись заведения и вероятности в словарь
            }
        }

        venueAndVenueLikelihoods
    }
    // загрузка предпочтений пользователя из файла
    private Map<String, Integer> getUserPreferences(){
        Map<String, Integer> mapOfUserPreferences = new HashMap<>()

        def jsonSlurper = new JsonSlurper()
        List<Object> userPreferences = jsonSlurper.parse(new File("src/resources/data/user_preferences.json"))
        for(element in userPreferences){
            mapOfUserPreferences.put(element?.get("name"), element?.get("index"))
        }

        mapOfUserPreferences
    }


    // если дистанция больше -> вероятность несущественно меньше
    // index - основной параметр, влияющий на вероятность
    private Double calculateLikelihood(Object venue, Integer distance){
        Double index = 10

        for(element in userPreferences){
            if(venue?.get("categories")[0]?.get("name").contains(element.key)){
                index = element.value
                break
            }
        }

        Double likelihood = (0.9 * index) - (0.1 * distance)
        likelihood
    }

}
