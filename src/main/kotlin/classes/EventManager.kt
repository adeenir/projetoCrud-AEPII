package br.com.bittelkow.classes

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File

class EventManager {
    private val eventsFile = File("src/main/resources/events.json")
    
    fun getEvents(): JsonArray? {
        val events = JsonParser.parseString(eventsFile.readText()).asJsonArray
        events.sortedByDescending { it.asJsonObject.get("date").asString }
        
        return events
    }
    
    fun createEvent(name: String, description: String, date: String) {
        val events = JsonParser.parseString(eventsFile.readText()).asJsonArray
        
        val event = JsonObject()
        event.addProperty("name", name)
        event.addProperty("description", description)
        event.addProperty("date", date.toString())
        events.asJsonArray.add(event)
        
        eventsFile.createNewFile()
        eventsFile.printWriter().use { out -> out.println(Gson().toJson(events)) }
        
    }
}