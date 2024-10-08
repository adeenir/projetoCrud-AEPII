package br.com.bittelkow

import br.com.bittelkow.classes.EventManager
import br.com.bittelkow.classes.MenuManager
import br.com.bittelkow.lib.Logger
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main() {
    val logger: Logger = Logger()
    val eventManager = EventManager()
    val menuManager = MenuManager(eventManager)
    
    thread(start = true) {
        logger.registerLog("Abriu o sistema")
        menuManager.showMenu()
    }       
    
    Runtime.getRuntime().addShutdownHook(Thread {
        runBlocking() { eventManager.saveFile() }
        logger.registerLog("Fechou o sistema")
    })
}