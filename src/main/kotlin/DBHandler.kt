package com.example

import com.example.models.JobDTO
import com.example.models.Job
import com.example.models.PartialJobObject
import com.example.models.toBsonUpdate
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.flow.toList
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoCollection
import org.bson.conversions.Bson
import org.bson.types.ObjectId

data class DbConnection(
    val mongoClient: MongoClient,
    val collection: MongoCollection<JobDTO>
)

suspend fun createJob(jobs: List<Job>) {
    try{
        val mongoConnection = connect()
        var jobList =  mutableListOf<JobDTO>()
        for(job in jobs){
            jobList.add(JobDTO(job))
        }
        mongoConnection.collection.insertMany(jobList)
        mongoConnection.mongoClient.close()
    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }
}
suspend fun getJobs(): List<JobDTO> {
    var jobs =  ArrayList<JobDTO>()
    val mongoConnection = connect()
    try{

        jobs = mongoConnection.collection.find().toList() as ArrayList<JobDTO>

    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }
    mongoConnection.mongoClient.close()
    return jobs
}

suspend fun updateJob(jobs: List<PartialJobObject>){
    try{
        val mongoConnection = connect()
        var jobUpdateList =  mutableListOf<Bson>()
        for(job in jobs){
            val filter = Filters.eq("_id", ObjectId(job.id))
            val update = job.toBsonUpdate()?: continue
            jobUpdateList.add(update)
            mongoConnection.collection.updateOne(filter, update)
        }
        mongoConnection.mongoClient.close()
    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }
}

suspend fun deleteJob(jobs: List<PartialJobObject>){
    try{
        val mongoConnection = connect()
        val jobIds = jobs.map { ObjectId(it.id)}
        val filter = Filters.`in`("_id", jobIds)
        mongoConnection.collection.deleteMany(filter)
        mongoConnection.mongoClient.close()
    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }
}

fun connect(): DbConnection {
    val dbURI = "<insert connection string here>"

    val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    val mongoClientSettings = MongoClientSettings.builder().applyConnectionString(ConnectionString(dbURI)).serverApi(serverApi).build()

    var mongoClient = MongoClient.create(mongoClientSettings)
    var collection = mongoClient.getDatabase("JobTracker").getCollection<JobDTO>("Jobs")

    return DbConnection(mongoClient, collection)
}
