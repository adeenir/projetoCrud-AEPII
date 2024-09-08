package br.com.bittelkow.lib

import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType

class ToastManager {
    fun createToast(icon: String, title: String, message: String) {
        val tray = SystemTray.getSystemTray()
        val image = Toolkit.getDefaultToolkit().getImage(icon)
        val trayIcon = TrayIcon(image, title)
        trayIcon.isImageAutoSize = true
        trayIcon.toolTip = title
        tray.add(trayIcon)
        trayIcon.displayMessage(title, message, MessageType.INFO)
    }

    fun testSystemTray() {
        val tray = SystemTray.getSystemTray()
        val image = Toolkit.getDefaultToolkit().getImage("C:/Users/Niro/Desktop/Faculdade/crudAgenda/notification.png")
        val trayIcon = TrayIcon(image, "Tray Demo")
        trayIcon.isImageAutoSize = true
        trayIcon.toolTip = "System tray icon demo"
        tray.add(trayIcon)
        trayIcon.displayMessage("Hello, World", "notification demo", MessageType.INFO)
    }
}