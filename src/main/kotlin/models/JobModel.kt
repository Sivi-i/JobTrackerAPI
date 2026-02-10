package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.util.Date
import java.text.SimpleDateFormat

val dateFormat = SimpleDateFormat("yyyy-MM-dd")

data class JobDTO(
    @BsonId val _id: ObjectId? = null,

    @BsonProperty("CompanyName")
    var companyName: String,

    @BsonProperty("Applied")
    var applied: Status,

    @BsonProperty("Response")
    var response: Status,

    @BsonProperty("Interview")
    var interview: Status,

    @BsonProperty("Date Applied")
    var dateApplied: Date,

    @BsonProperty("Position")
    var position: String,

    @BsonProperty("Notes")
    var notes: String,

    @BsonProperty("LearnForRole")
    var learnForRole: String,
){
    constructor(job: Job) : this(
        companyName = job.companyName,
        applied = job.applied,
        response = job.response,
        interview = job.interview,
        dateApplied = dateFormat.parse(job.dateApplied),
        position = job.position,
        notes = job.notes,
        learnForRole = job.learnForRole,
    )

    constructor(jobUpdateDTO: JobUpdateDTO) : this(
        companyName = jobUpdateDTO.companyName as String,
        applied = jobUpdateDTO.applied as Status,
        response = jobUpdateDTO.response as Status,
        interview = jobUpdateDTO.interview as Status,
        dateApplied = jobUpdateDTO.dateApplied as Date,
        position = jobUpdateDTO.position as String,
        notes = jobUpdateDTO.notes as String,
        learnForRole = jobUpdateDTO.learnForRole as String,
    )
}

@Serializable
data class Job(
    val id: String? = null,
    var companyName: String,
    var applied: Status,
    var response: Status,
    var interview: Status,
    var dateApplied: String,
    var position: String,
    var notes: String,
    var learnForRole: String,
)

enum class Status {
    Good, Neutral, Bad
}