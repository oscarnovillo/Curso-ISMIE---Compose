package com.example.primerxmlmvvm.ui.suma

sealed class SumaEvent {
        class Sumar(val incremento:Int) : SumaEvent()
        class Restar(val incremento:Int) : SumaEvent()
        object ErrorMostrado: SumaEvent()
}