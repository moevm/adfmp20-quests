package com.example.halp

import com.google.firebase.firestore.GeoPoint

class Quest
{
    var id = ""
    var name = ""
    var description = ""
    var duration: Number = 0
    var min_people: Number = 0
    var max_people: Number = 0
    var company = ""
    var complexity = ""
    var genre = ""
    var address: GeoPoint = GeoPoint(0.0,0.0)
    var cost: Number = 0
    var img_url = ""
}