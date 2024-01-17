package com.example.spellbook5eapplication.app.Model.Data_Model

class Settings{
    var saveSpellData: Boolean = true //TODO set to false before hand in, this makes it so you have to change the setting before it downloads to your device.
    var useInternet: Boolean = true
    var darkmode: Boolean = true


    fun resetToDefault(){
        saveSpellData = true
        useInternet = true
        darkmode = true
    }
    fun copy() : Settings{
        val newSettings = Settings()
        newSettings.saveSpellData = saveSpellData
        newSettings.useInternet = useInternet
        newSettings.darkmode = darkmode
        return newSettings
    }
    override fun toString(): String {
        var string = "Settings:"
        string += "\nSaveSpellLocally: $saveSpellData"
        string += "\nUseInternet: $useInternet"
        string += "\nTheme: $darkmode"
        return string
    }
}