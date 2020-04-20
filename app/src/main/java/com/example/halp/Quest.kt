package com.example.halp

class Quest {
    var name = ""
    var description = ""
    var duration: Number = 0
    var min_p: Number = 0
    var max_p: Number = 0
    var company = ""
    var complexity = ""
    var genre = ""
    private var cost = 0f

    fun getCost(): Number {
        return cost
    }

    fun setCost(cost: Float) {
        this.cost = cost
    }
}