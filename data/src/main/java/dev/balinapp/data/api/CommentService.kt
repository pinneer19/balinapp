package dev.balinapp.data.api

import dev.balinapp.data.model.comment.CommentActionResponse
import dev.balinapp.data.model.comment.CommentDtoIn
import dev.balinapp.data.model.comment.CommentListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentService {

    @GET("image/{imageId}/comment")
    suspend fun getComments(
        @Path("imageId") imageId: Int,
        @Query("page") page: Int
    ): Result<CommentListResponse>

    @POST("image/{imageId}/comment")
    suspend fun postComment(
        @Path("imageId") imageId: Int,
        @Body comment: CommentDtoIn
    ): Result<CommentActionResponse>

    @DELETE("image/{imageId}/comment/{commentId}")
    suspend fun deleteComment(
        @Path("imageId") imageId: Int,
        @Path("commentId") id: Int
    ): Result<CommentActionResponse>
}