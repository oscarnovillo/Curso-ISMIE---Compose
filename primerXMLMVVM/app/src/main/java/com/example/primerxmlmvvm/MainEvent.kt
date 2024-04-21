package com.example.primerxmlmvvm

sealed class MainEvent {
        object Sumar : MainEvent()
        object Restar: MainEvent()
        object ErrorMostrado: MainEvent()
}