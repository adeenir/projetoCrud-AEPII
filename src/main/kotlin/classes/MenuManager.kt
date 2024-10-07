package br.com.bittelkow.classes

import br.com.bittelkow.lib.Logger
import kotlin.system.exitProcess

class MenuManager {
    private val eventManager: EventManager
    private var menuOptionSelected: Int = 0
    private val _logger = Logger()
    
    constructor(eventManager: EventManager) {
        this._logger.registerLog("Iniciou o MenuManager")
        
        this.eventManager = eventManager
    }

    fun showMenu() {
        this.clearConsole()
        println("""
     _    ____  _____ _   _ ___ ____  
    / \  |  _ \| ____| \ | |_ _|  _ \ 
   / _ \ | | | |  _| |  \| || || |_) |
  / ___ \| |_| | |___| |\  || ||  _ < 
 /_/   \_\____/|_____|_| \_|___|_| \_\
        """.trimIndent())
        println("\t\tSeja bem-vindo ao sistema!")
        
        println("""
            1 - Listar eventos cadastrados
            2 - Criar novo evento
            3 - Editar evento
            4 - Excluir evento
            0 - Sair
        """.trimIndent())
        
        println("\nInforme uma opção:")
        this.menuOptionSelected = readLine()?.toIntOrNull() ?: 0
        
        while (this.menuOptionSelected != 0) {
            when (this.menuOptionSelected) {
                1 -> this.listEvents()
                2 -> this.createEvent()
                3 -> this.editEvent()
                4 -> this.deleteEvent()
                0 -> {
                    println("Saindo...")
                    exitProcess(0)
                }
                else -> println("Opção inválida")
            }
            
            println("\nPressione enter para continuar...")
            readLine()
            this.clearConsole()
            
            this.showMenu()
        }
    }

    private fun clearConsole() {
        val os: String = System.getProperty("os.name")

        if (System.getProperty("os.name").contains("Windows"))
            ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    }

    private fun listEvents() {
        println("\nEventos cadastrados:\n")
        val events = this.eventManager.getEvents()
        
        events?.forEachIndexed { i, v ->
            println("ID: ${i + 1}")
            println("Nome: ${events[i].asJsonObject.get("name").asString}")
            println("Descrição: ${events[i].asJsonObject.get("description").asString}")
            println("Data: ${events[i].asJsonObject.get("date").asString}")
            println()
        }
    }
    
    private fun createEvent() {
        println("\nInforme o nome do evento:")
        val name = readLine()
        
        println("Informe a descrição do evento:")
        val description = readLine()
        
        println("Informe a data do evento:")
        val date = readLine()
        
        this.eventManager.createEvent(name!!, description!!, date!!)
        
        println("Evento criado com sucesso!")
    }
    
    private fun editEvent() {
        println("\nInforme o ID do evento a editar:")
        val eventId: Int = readLine()!!.toInt()
        
        val event = this.eventManager.getEvents()!![eventId - 1].asJsonObject
        
        println("Deseja editar o nome do evento? (s/n)")
        if (readLine() == "s") {
            println("Informe o novo nome do evento:")
            event.addProperty("name", readLine())
        }
        
        println("Deseja editar a descrição do evento? (s/n)")
        if (readLine() == "s") {
            println("Informe a nova descrição do evento:")
            event.addProperty("description", readLine())
        }
        
        println("Deseja editar a data do evento? (s/n)")
        if (readLine() == "s") {
            println("Informe a nova data do evento:")
            event.addProperty("date", readLine())
        }
        
        this.eventManager.editEvent(eventId - 1, event)
        
        println("Evento editado com sucesso!") 
    }
    
    fun deleteEvent() {
        println("\nInforme o ID do evento a deletar:")
        var eventId: Int = readLine()!!.toInt()
        
        this.eventManager.deleteEvent(eventId - 1)
    }
}
