package br.com.bittelkow.classes

import br.com.bittelkow.lib.Logger
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File

class EventManager {
    private val eventsFile = File("src/main/resources/events.json")
    private val _logger: Logger = Logger()
    private val eventsArray: JsonArray
    
    init {
        if (!eventsFile.exists()) {
            eventsFile.createNewFile()
            eventsFile.printWriter().use { out -> out.println("[]") }
        }
        this._logger.registerLog("Iniciou o EventManager")
        eventsArray = JsonParser.parseString(eventsFile.readText()).asJsonArray
    }

    suspend fun saveFile() = coroutineScope {
        val deferred: Deferred<Unit> = async {
            eventsFile.createNewFile()
            eventsFile.printWriter().use { out -> out.println(Gson().toJson(eventsArray)) }
        }
        deferred.start()
    }
    
    fun getEvents(): JsonArray? {
        eventsArray.sortedByDescending { it.asJsonObject.get("date").asString }
        this._logger.registerLog("Listou todos os eventos existentes")
        
        return eventsArray
    }
    
    fun createEvent(name: String, description: String, date: String) {
        val event = JsonObject()
        event.addProperty("name", name)
        event.addProperty("description", description)
        event.addProperty("date", date.toString())
        eventsArray.add(event)
        
        runBlocking { saveFile() }

        this._logger.registerLog("Criou o evento '${name}'")
    }
    
    fun editEvent(id: Int, event: JsonObject) {
        eventsArray.asJsonArray[id] = event
        runBlocking { saveFile() }
        
        this._logger.registerLog("Editou o evento '${event.get("name").asString}'")
    }
    
    fun deleteEvent(id: Int): Int {
        if (eventsArray[id] !== null) {
            val eventName: String = eventsArray[id].asJsonObject.get("name").asString
            
            try {
                eventsArray.remove(id)
                runBlocking { saveFile() }
                println("Evento deletado com sucesso!")
                this._logger.registerLog("Deletou o evento '${eventName}'")
            } catch (e: IndexOutOfBoundsException) {
                println("Erro ao deletar o evento")
                this._logger.registerLog("Erro ao deletar o evento '${id}': ${e.message}")
            }
            
            return 1;
        } else {
            println("Evento não encontrado")
            this._logger.registerLog("Erro ao deletar o evento '${id}': Evento não encontrado")
            return 0;
        }
    }
}