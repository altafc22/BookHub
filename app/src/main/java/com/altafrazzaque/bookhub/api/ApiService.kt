package com.altafrazzaque.bookhub.api

import com.altafrazzaque.bookhub.model.api.Books
import com.altafrazzaque.bookhub.model.api.Item
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/books/v1/volumes")
    fun getVolumeBooks(@Query(value = "q") searchText: String?): Single<Books>

    @GET("/books/v1/volumes/{id}")
    fun getBookItem(@Path("id") id: String?): Single<Item>

    @GET("/books/v1/volumes?q=categories:young+fiction&maxResults=30")
    fun getNewReleaseBooks(): Single<Books>

    @GET("/books/v1/volumes")
    fun getCategories(
        @Query(value = "q") searchText: String?,
        @Query("filter") filter: String?,
        @Query("orderBy") orderBy: String?,
        @Query("maxResults") maxResults: Int?
    ): Single<Books>

    @GET("/books/v1/volumes")
    fun getCategories(
        @Query(value = "q") searchText: String?,
        @Query("orderBy") orderBy: String?,
        @Query("maxResults") maxResults: Int?
    ): Single<Books>


    @GET("/books/v1/volumes")
    fun getSearchResults(
        @Query(value = "q") searchText: String?,
        @Query("startIndex") startIndex: Int,
        @Query("orderBy") orderBy: String?,
        @Query("maxResults") maxResults: Int
    ): Single<Books>

}