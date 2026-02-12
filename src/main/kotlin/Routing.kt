package com.example


import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.JobDTO
import com.example.models.Job
import com.example.models.PartialJobObject
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/createJobs"){
            try{
                val task = call.receive<List<Job>>()
                createJob(task)
                call.respondText("Job created!")
            }catch(e: Exception){
                call.respondText("Job creation failed!: ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }

        get("/getJobs"){
              val currentJobs = getCurrentJobs();
              call.respond("Current Jobs: $currentJobs")
        }

        put("/updateJobs"){
            try{
                val task = call.receive<List<PartialJobObject>>()
                updateJob(task)
                call.respondText("Job(s) Updated!")
            }catch(e: Exception){
                call.respondText("Job Update Failed!: ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }

        delete("deleteJobs"){
            try{
                val task = call.receive<List<PartialJobObject>>()
                deleteJob(task)
                call.respond("Job(s) deleted!")
            }catch(e: Exception){
                call.respondText("Job Deletion Failed!: ${e.message}", status = HttpStatusCode.BadRequest)
            }
            call.respondText("Delete Jobs Endpoint")
        }

    }
}

suspend fun getCurrentJobs(): List<JobDTO>  {
    return getJobs();
}
