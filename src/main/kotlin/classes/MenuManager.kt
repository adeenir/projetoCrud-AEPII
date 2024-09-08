package br.com.bittelkow.classes

import kotlin.system.exitProcess

class MenuManager {
    private val eventManager = EventManager()
    private var menuOptionSelected: Int = 0

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
        this.menuOptionSelected = readLine()?.toIntOrNull()!!
        
        while (this.menuOptionSelected != 0) {
            when (this.menuOptionSelected) {
                1 -> this.listEvents()
                2 -> this.createEvent()
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
        this.clearConsole()
        println("Eventos cadastrados:")
        val events = this.eventManager.getEvents()
        
        events?.forEach {
            println("Nome: ${it.asJsonObject.get("name")}")
            println("Descrição: ${it.asJsonObject.get("description")}")
            println("Data: ${it.asJsonObject.get("date")}")
            println()
        }
    }
    
    private fun createEvent() {
        this.clearConsole()
        println("Informe o nome do evento:")
        val name = readLine()
        
        println("Informe a descrição do evento:")
        val description = readLine()
        
        println("Informe a data do evento:")
        val date = readLine()
        
        this.eventManager.createEvent(name!!, description!!, date!!)
    }
}
