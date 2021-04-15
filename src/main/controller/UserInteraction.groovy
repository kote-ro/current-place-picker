package main.controller

class UserInteraction {
    void menu(Map<Object, Double> venueAndVenueLikelihoods){
        Integer userChoose
        // здесь должна быть функция-фильтр для минимизации выводимых мест в радиусе
        showVenueAndLikelihoodsList(venueAndVenueLikelihoods)

        for(venue in venueAndVenueLikelihoods){
            println("Вы находитесь в ${venue.key?.get("name")}? (1 - да, 2 - нет)")
            userChoose = userMakesChoose()
            if(userChoose.equals(1)){
                showVenueInfo(venue.key)
                break
            }else if(userChoose.equals(2)){
                continue
            }
        }
    }

    private void showVenueInfo(Object venue){
        println("Вы находитесь в: ${venue?.get("name")}\n" +
                "Растояние: ${venue?.get("location")?.get("distance")}\n" +
                "Тип заведения: ${venue?.get("categories")[0]?.get("name")}\n")
    }

    private void showVenueAndLikelihoodsList(Map<Object, Double> venueAndVenueLikelihoods){
        for(venue in venueAndVenueLikelihoods){
            println("${venue.key?.get("name")}||${venue.key?.get("location")?.get("distance")}" +
                    "||${venue.key?.get("categories")[0]?.get("name")}||${venue.value}")
        }
        println("------------------------------------------")
    }

    private Integer userMakesChoose(){
        Integer userChoose
        Scanner input = new Scanner(System.in)

        do {
            try {
                userChoose = input.nextInt()
                return userChoose
            } catch (RuntimeException exception) {
                println("Неправильный ввод. Повторите попытку ещё раз.")
                userMakesChoose()
            }
        }while(input instanceof Integer && (input == 1 || input == 2))
    }

}
