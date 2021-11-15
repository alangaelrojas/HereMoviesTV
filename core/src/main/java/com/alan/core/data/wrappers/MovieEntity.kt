package com.alan.core.data.wrappers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie")
data class MovieEntity(

        @ColumnInfo(name = "adult")
        @SerializedName("adult") val adult : Boolean?,

        @ColumnInfo(name = "image")
        @SerializedName("backdrop_path") val image : String?,

        @PrimaryKey
        @SerializedName("id") val id : Int?,

        @ColumnInfo(name = "originalLanguage")
        @SerializedName("original_language") val originalLanguage : String?,

        @ColumnInfo(name = "originalTitle")
        @SerializedName("original_title") val originalTitle : String?,

        @ColumnInfo(name = "overview")
        @SerializedName("overview") val overview : String?,

        @ColumnInfo(name = "popularity")
        @SerializedName("popularity") val popularity : Double?,

        @ColumnInfo(name = "posterPath")
        @SerializedName("poster_path") val posterPath : String?,

        @ColumnInfo(name = "releaseDate")
        @SerializedName("release_date") val releaseDate : String?,

        @ColumnInfo(name = "title")
        @SerializedName("title") val title : String?,

        @ColumnInfo(name = "video")
        @SerializedName("video") val video : Boolean?,

        @ColumnInfo(name = "voteAverage")
        @SerializedName("vote_average") val voteAverage : Double?,

        @ColumnInfo(name = "voteCount")
        @SerializedName("vote_count") val voteCount : Int?,

        @ColumnInfo(name = "type")
        val type : String?,

        @ColumnInfo(name = "recoId")
        val idRecomendation : Int?


): Serializable {
        override fun toString(): String {
                return "MovieEntity{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", video='" + video + '\'' +
                        ", posterPath='" + posterPath + '\'' +
                        ", image='" + image + '\'' +
                        '}'
        }

        companion object {
                internal const val serialVersionUID = 727566175075960653L
        }
}


