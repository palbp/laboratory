package pt.isel.pdm.quote.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import pt.isel.pdm.quote.hypermedia.MediaType

class MediaTypeTypeAdapter : TypeAdapter<MediaType>() {

    override fun write(out: JsonWriter, value: MediaType?) {
        if (value != null) out.jsonValue("\"$value\"")
        else out.nullValue()
    }

    override fun read(input: JsonReader): MediaType? {
        return if (input.peek() == JsonToken.NULL) null
        else MediaType.fromString(input.nextString())
    }
}