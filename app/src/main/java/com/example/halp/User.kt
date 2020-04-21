package com.example.halp

import java.util.*

class User {
    var id: String? = null
    var name: String? = null
    var mail: String? = null
    var phone: String? = null
    var password: String? = null
    var birthday: Date? = null
    var favourites: ArrayList<String> = ArrayList<String>()
    var orders: ArrayList<Order> = ArrayList<Order>()

}