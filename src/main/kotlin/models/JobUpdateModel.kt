package com.example.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.util.Date
import com.mongodb.client.model.Updates
import org.bson.conversions.Bson


data class JobUpdateDTO(
    @BsonId val _id: ObjectId,

    @BsonProperty("CompanyName")
    var companyName: String? = null,

    @BsonProperty("Applied")
    var applied: Status? = null,

    @BsonProperty("Response")
    var response: Status? = null,

    @BsonProperty("Interview")
    var interview: Status? = null,

    @BsonProperty("Date Applied")
    var dateApplied: Date? = null,

    @BsonProperty("Position")
    var position: String? = null,

    @BsonProperty("Notes")
    var notes: String? = null,

    @BsonProperty("LearnForRole")
    var learnForRole: String? = null,
)

@Serializable
data class PartialJobObject(
    val id: String,
    val companyName: String? = null,
    val applied: Status? = null,
    val response: Status? = null,
    val interview: Status? = null,
    val dateApplied: String? = null,
    val position: String? = null,
    val notes: String? = null,
    var learnForRole: String? = null,
)

fun PartialJobObject.toBsonUpdate(): Bson? {
    val updates = mutableListOf<Bson>()

    companyName?.let { updates += Updates.set("CompanyName", it) }
    applied?.let { updates += Updates.set("Applied", it) }
    response?.let { updates += Updates.set("Response", it) }
    interview?.let { updates += Updates.set("Interview", it) }
    dateApplied?.let { updates += Updates.set("Date Applied", dateFormat.parse(it)) }
    position?.let { updates += Updates.set("Position", it) }
    notes?.let { updates += Updates.set("Notes", it) }
    learnForRole?.let { updates += Updates.set("LearnForRole", it) }

    return if (updates.isEmpty()) null else Updates.combine(updates)
}