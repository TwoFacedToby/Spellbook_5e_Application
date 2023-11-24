package com.example.spellbook5eapplication.app.Model.Data_Model

class JSON(private var jsonString: String, private var receiveType: String) {
    fun getJSONString() : String{
        return jsonString
    }
    fun getType() : String{
        return receiveType
    }
}