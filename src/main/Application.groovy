package main

import main.controller.MainController

class Application {
    static void main(String[] args){
        MainController mainController = new MainController()
        mainController.startMainProcess()
    }
}
