package com.example

import com.example.models.JobDTO
import com.example.models.Job
import com.example.models.JobUpdate
import com.example.models.JobUpdateDTO
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.flow.toList
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import org.bson.conversions.Bson

suspend fun createJob(jobs: List<Job>) {
    try{
        var mongoClient: MongoClient = connect();
        var collection = mongoClient.getDatabase("JobTracker").getCollection<JobDTO>("Jobs")
        var jobList =  mutableListOf<JobDTO>()
        for(job in jobs){
            jobList.add(JobDTO(job))
        }
        collection.insertMany(jobList)
    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }
}
suspend fun getJobs(): List<JobDTO> {
    var jobs =  ArrayList<JobDTO>();

    try{
        var mongoClient: MongoClient = connect();
        var collection = mongoClient.getDatabase("JobTracker").getCollection<JobDTO>("Jobs")
        jobs = collection.find().toList() as ArrayList<JobDTO>
    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }

    return jobs
}

suspend fun UpdateJob(Job: JobUpdate){

}

suspend fun UpdateJob(Jobs: List<JobUpdate>){
    try{
        var mongoClient: MongoClient = connect();
        var collection = mongoClient.getDatabase("JobTracker").getCollection<JobDTO>("Jobs")
        val jobsToUpdate = mutableListOf<Bson>()

        for(job in Jobs){
            val filter = Filters.eq(Job::id.name, job.id)
            job.companyName?.let { jobsToUpdate += Updates.set("CompanyName", it) }
            job.applied?.let { jobsToUpdate += Updates.set("Applied", it) }
            job.response?.let { jobsToUpdate += Updates.set("Response", it) }
            job.interview?.let { jobsToUpdate += Updates.set("Interview", it) }
            job.dateApplied?.let { jobsToUpdate += Updates.set("Date Applied", it) }
            job.position?.let { jobsToUpdate += Updates.set("Position", it) }
            job.notes?.let { jobsToUpdate += Updates.set("Notes", it) }
            job.learnForRole?.let { jobsToUpdate += Updates.set("LearnForRole", it) }

            collection.updateOne(filter, Updates.combine(jobsToUpdate))
        }

    }catch(e:Exception){
        println("Error: ${e.localizedMessage}")
    }
}

suspend fun DeleteJob(Job: JobDTO){}

suspend fun DeleteJob(Job: List<JobDTO>){}

fun connect(): MongoClient {
    val dbURI = "<insert connection string here>"

    val serverApi = ServerApi.builder().version(ServerApiVersion.V1).build()
    val mongoClientSettings = MongoClientSettings.builder().applyConnectionString(ConnectionString(dbURI)).serverApi(serverApi).build()

    return MongoClient.create(mongoClientSettings)
}
