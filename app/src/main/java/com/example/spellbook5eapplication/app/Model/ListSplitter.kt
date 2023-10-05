package com.dtu.uemad.birthdaycardtest.Model

class ListSplitter {

    fun splitListIntoSize(full : List<String>, size : Int) : List<List<String>>{
        val toReturn = mutableListOf<List<String>>()
        var currentList = mutableListOf<String>()
        var amount = 0
        for(element in full){
            if(amount >= size){
                toReturn.add(currentList.toList())
                currentList = mutableListOf<String>()
                amount = 0
            }
            currentList.add(element)
            amount++
        }
        return toReturn.toList()
    }
    fun printListList(listlist : List<List<String>>){
        var amount = 0
        for(list in listlist){
            println("Printing List Part ${amount++}    -   -   -   -   -   -   -   -   -")
            for(element in list){
                println(element)
            }

        }
    }
    fun collect(listlist : List<List<String?>>) : List<String>{
        val toReturn = mutableListOf<String>()
        for(list in listlist){
            if(list.isNotEmpty()){
                for(element in list){
                    if(element != null) toReturn.add(element)
                }
            }
        }
        return toReturn.toList()
    }
}