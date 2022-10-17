package ru.serg.composeweatherapp.data.remote.responses


import kotlinx.serialization.*
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

    @Serializable
    data class CityNameGeocodingResponseItem(
        val country: String?,
        val lat: Double?,
//        @Serializable(with = SortedMapSerializer::class)
        @SerialName("local_names")
        val localNames: Map<String, String>? = mapOf(),
        val lon: Double?,
        val name: String?,
        val state: String?
    ) {

    }



object SortedMapSerializer: KSerializer<Map<String, String>> {
    private val mapSerializer = MapSerializer(String.serializer(), String.serializer())

    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Map<String, String>) {
        mapSerializer.serialize(encoder, value.toSortedMap())
    }

    override fun deserialize(decoder: Decoder): Map<String, String> {
        return mapSerializer.deserialize(decoder)
    }
}