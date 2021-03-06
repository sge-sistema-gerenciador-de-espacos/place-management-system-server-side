package com.pms.placemanagementsystemserverside.util.json.parse.space

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.pms.placemanagementsystemserverside.models.space.SpaceModel
import java.io.IOException

class SpaceSerializer : StdSerializer<SpaceModel> {

    constructor(spaceModel: Class<SpaceModel>?) : super(spaceModel)
    constructor() : this(null)

    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: SpaceModel, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()

        gen.writeNumberField("id", value.id)
        gen.writeStringField("name", value.name)
        SpaceItemJsonParser().serialize(value, gen)
        gen.writeNumberField("frequency_count", value.frequencyCount)

        gen.writeEndObject()
    }
}