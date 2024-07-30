package com.example.mediconnect.domain.entities

class TopicRapport {
    var id: Int = 0
    var title: String? = ""
    var dateWork: String? = ""
    var workedUser: String? = ""
    var idUsers: MutableList<String>? = mutableListOf()
    var nbHours: MutableList<Int>? = mutableListOf()

    override fun toString(): String {
        return "TopicRapport(id=$id, title=$title, dateWork=$dateWork, idUsers=$idUsers, nbHours=$nbHours)"
    }

}