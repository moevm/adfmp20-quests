package com.example.halp

import kotlinx.coroutines.runBlocking
import org.litote.kmongo.Data
import org.litote.kmongo.reactivestreams.*  //NEEDED! import KMongo reactivestreams extensions
import org.litote.kmongo.coroutine.* //NEEDED! import KMongo coroutine extensions

data class DatabaseCollectionItem(val name: String, val age: Int) //TODO Вставить правильный формат коллекции!!!!

class Database
{
    private var client: CoroutineClient = KMongo.createClient().coroutine
    private var database: CoroutineDatabase
    private var collection: CoroutineCollection<DatabaseCollectionItem>

    constructor(databaseName: String){
        this.database = this.client.getDatabase(databaseName) //normal java driver usage
        this.collection = database.getCollection() //KMongo extension method
    }

    public fun findOne(shellQueryString: String): DatabaseCollectionItem?{
        var string: DatabaseCollectionItem? = null
        runBlocking {
            string = collection.findOne(shellQueryString)
        }
        return string
    }

    private fun getCollection(): CoroutineCollection<DatabaseCollectionItem>{
        return this.collection
    }

}







