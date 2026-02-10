package com.example


import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.JobDTO
import com.example.models.Job
import com.example.models.JobUpdate
import com.example.models.JobUpdateDTO
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import com.example.getJobs
import com.example.createJob
import com.example.UpdateJob

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
                val task = call.receive<List<JobUpdate>>()
                UpdateJob(task)
                call.respondText("Job(s) Updated!")
            }catch(e: Exception){
                call.respondText("Job Update Failed!: ${e.message}", status = HttpStatusCode.BadRequest)
            }
        }

        delete("deleteJobs"){
            call.respondText("Delete Jobs Endpoint")
        }

    }
}

suspend fun getCurrentJobs(): List<JobDTO>  {
    return getJobs();
}
