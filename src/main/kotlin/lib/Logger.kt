package br.com.bittelkow.lib

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Logger {
    private val logsFile = File("src/main/resources/logs-${SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(
        Date())}.txt")
    private var oldLogs: String = ""
    private fun date(): String {
        return SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())
    }
    
    fun registerLog(message: String) {
        if (logsFile.exists()) oldLogs = logsFile.readText()
        else logsFile.createNewFile()
        
        logsFile.writeText("[${this.date()}] - $message\n$oldLogs")
    }
}