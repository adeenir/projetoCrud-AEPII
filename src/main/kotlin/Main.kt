package br.com.bittelkow

import br.com.bittelkow.classes.EventManager
import br.com.bittelkow.classes.MenuManager
import br.com.bittelkow.lib.ToastManager
import java.awt.SystemTray
import kotlin.concurrent.thread

fun main() {
    if (!SystemTray.isSupported()) throw Exception("The SystemTray is not supported")
    val eventManager = EventManager()
    val menuManager = MenuManager()
    val toastManager = ToastManager()
    
    thread(start = true) {
        menuManager.showMenu()
    }
}