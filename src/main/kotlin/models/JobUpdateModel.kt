package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.util.Date
import java.text.SimpleDateFormat
import com.example.models.Job


data class JobUpdateDTO(
    @BsonId val _id: ObjectId? = null,

    @BsonProperty("CompanyName")
    var companyName: String?,

    @BsonProperty("Applied")
    var applied: Status?,

    @BsonProperty("Response")
    var response: Status?,

    @BsonProperty("Interview")
    var interview: Status?,

    @BsonProperty("Date Applied")
    var dateApplied: Date?,

    @BsonProperty("Position")
    var position: String?,

    @BsonProperty("Notes")
    var notes: String?,

    @BsonProperty("LearnForRole")
    var learnForRole: String?,
){
    constructor(jobUpdate: JobUpdate ): this(
        _id = ObjectId(jobUpdate.id),
        companyName = jobUpdate.companyName,
        applied = jobUpdate.applied,
        response = jobUpdate.response,
        interview = jobUpdate.interview,
        dateApplied = dateFormat.parse(jobUpdate.dateApplied),
        position = jobUpdate.position,
        notes = jobUpdate.notes,
        learnForRole = jobUpdate.learnForRole,
    )
}

@Serializable
data class JobUpdate(
    val id: String? = null,
    val companyName: String?,
    val applied: Status?,
    val response: Status?,
    val interview: Status?,
    val dateApplied: String?,
    val position: String?,
    val notes: String?,
    var learnForRole: String?,
)