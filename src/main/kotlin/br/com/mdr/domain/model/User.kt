package br.com.mdr.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId val id: ObjectId = ObjectId(),
    val email: String,
    val passwordHash: String
)
