package com.example.spellbook5eapplication.app.viewmodel

class MediatorViewModel {
    private val observers = ArrayList<Observer>()


    private fun notifyChange(){
        observers.forEach {
            it.onChange()
        }
    }
    fun addObserver(observer : Observer){
        observers.add(observer)
    }
    fun removeObserver(observer : Observer) : Boolean{
        return observers.remove(observer)
    }

}