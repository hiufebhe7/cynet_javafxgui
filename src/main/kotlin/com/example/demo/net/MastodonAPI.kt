package com.example.demo.utils

import com.example.demo.net.AccessToken
import com.example.demo.net.Account
import com.example.demo.net.Media
import com.example.demo.net.OAuth
import okhttp3.MultipartBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.*


interface MastodonAPI {

    @FormUrlEncoded
    @POST("api/v1/apps")
    fun apps(
            @Field("client_name") clientName: String?,
            @Field("redirect_uris") redirectUris: String,
            @Field("scopes") scopes: String
//            @Field("website") website: String?
    ): Call<OAuth?>

    @FormUrlEncoded
    @POST("oauth/token")
    fun token(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("scope") scope: String,
            @Field("grant_type") grantType: String,
            @Field("username") uaername: String,
            @Field("password") password: String
//            @Field("redirect_uri") redirectUri: String?,
//            @Field("code") code: String?
    ): Call<AccessToken?>

    @GET("api/v1/accounts/verify_credentials")
    fun accountVerifyCredentials(): Call<Account?>

    @Multipart
    @POST("api/v1/media")
    fun uploadMedia(@Part file: MultipartBody.Part?): Call<Media>

//    @GET("api/v1/timelines/home")
//    fun home(
//        @Query("local") local: Boolean?,
//        @Query("only_media") media: Boolean?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?

//    @GET("api/v1/timelines/home")
//    fun home(
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?
//
//    @GET("api/v1/timelines/public")
//    fun public(
//        @Query("local") local: Boolean?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?
//
//    @GET("api/v1/timelines/tag/{hashtag}")
//    fun hashtag(
//        @Path("hashtag") hashtag: String?,
//        @Query("local") local: Boolean?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?
//
//    @GET("api/v1/notifications/{id}")
//    fun notification(@Path("id") notificationId: String?): Call<Notification?>?
//
//    @GET("api/v1/notifications")
//    fun notifications(
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Notification?>?>?
//
//    @POST("api/v1/notifications/clear")
//    fun clearNotifications(): Call<ResponseBody?>?


//    @FormUrlEncoded
//    @POST("api/v1/statuses")
//    fun postStatus(
//        @Field("status") text: String?,
//        @Field("in_reply_to_id") inReplyToId: String?,
//        @Field("spoiler_text") spoilerText: String?,
//        @Field("visibility") visibility: String?,
//        @Field("sensitive") sensitive: Boolean?,
//        @Field("media_ids[]") mediaIds: List<String?>?
//    ): Call<Status?>?
//
//    @GET("api/v1/statuses/{id}")
//    fun status(@Path("id") statusId: String?): Call<Status?>?
//
//    @GET("api/v1/statuses/{id}/context")
//    fun statusContext(@Path("id") statusId: String?): Call<StatusContext?>?
//
//    @GET("api/v1/statuses/{id}/reblogged_by")
//    fun statusRebloggedBy(
//        @Path("id") statusId: String?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?

//    @GET("api/v1/statuses/{id}/favourited_by")
//    fun statusFavouritedBy(
//        @Path("id") statusId: String?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @DELETE("api/v1/statuses/{id}")
//    fun deleteStatus(@Path("id") statusId: String?): Call<ResponseBody?>?
//
//    @POST("api/v1/statuses/{id}/reblog")
//    fun reblogStatus(@Path("id") statusId: String?): Call<Status?>?
//
//    @POST("api/v1/statuses/{id}/unreblog")
//    fun unreblogStatus(@Path("id") statusId: String?): Call<Status?>?
//
//    @POST("api/v1/statuses/{id}/favourite")
//    fun favouriteStatus(@Path("id") statusId: String?): Call<Status?>?
//
//    @POST("api/v1/statuses/{id}/unfavourite")
//    fun unfavouriteStatus(@Path("id") statusId: String?): Call<Status?>?
//

//
//    @GET("api/v1/accounts/search")
//    fun searchAccounts(
//        @Query("q") q: String?,
//        @Query("resolve") resolve: Boolean?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @GET("api/v1/accounts/{id}")
//    fun account(@Path("id") accountId: String?): Call<Account?>?
//
//    @GET("api/v1/accounts/{id}/statuses")
//    fun accountStatuses(
//        @Path("id") accountId: String?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?
//
//    @GET("api/v1/accounts/{id}/statuses")
//    fun accountStatuses(
//        @Path("id") accountId: String?,
//        @Query("only_media") onlyMedia: Boolean,
//        @Query("pinned") pinned: Boolean,
//        @Query("exclude_replies") excludeReplies: Boolean,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?
//
//    @GET("api/v1/accounts/{id}/followers")
//    fun accountFollowers(
//        @Path("id") accountId: String?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @GET("api/v1/accounts/{id}/following")
//    fun accountFollowing(
//        @Path("id") accountId: String?,
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @POST("api/v1/accounts/{id}/follow")
//    fun followAccount(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @POST("api/v1/accounts/{id}/unfollow")
//    fun unfollowAccount(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @POST("api/v1/accounts/{id}/block")
//    fun blockAccount(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @POST("api/v1/accounts/{id}/unblock")
//    fun unblockAccount(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @POST("api/v1/accounts/{id}/mute")
//    fun muteAccount(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @POST("api/v1/accounts/{id}/unmute")
//    fun unmuteAccount(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @GET("api/v1/accounts/relationships")
//    fun relationships(@Query("id[]") accountIds: List<String?>?): Call<List<Relationship?>?>?
//
//    @GET("api/v1/blocks")
//    fun blocks(
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @GET("api/v1/mutes")
//    fun mutes(
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @GET("api/v1/favourites")
//    fun favourites(
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Status?>?>?
//
//    @GET("api/v1/follow_requests")
//    fun followRequests(
//        @Query("max_id") maxId: String?,
//        @Query("since_id") sinceId: String?,
//        @Query("limit") limit: Int?
//    ): Call<List<Account?>?>?
//
//    @GET("api/v1/suggestions")
//    fun followSuggestions(): Call<List<Account?>?>?
//
//    @POST("api/v1/follow_requests/{id}/authorize")
//    fun authorizeFollowRequest(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @POST("api/v1/follow_requests/{id}/reject")
//    fun rejectFollowRequest(@Path("id") accountId: String?): Call<Relationship?>?
//
//    @FormUrlEncoded
//    @POST("api/v1/reports")
//    fun report(
//        @Field("account_id") accountId: String?, @Field("status_ids[]") statusIds: List<String?>?, @Field(
//            "comment"
//        ) comment: String?
//    ): Call<ResponseBody?>?
//
//    companion object {
//        const val ENDPOINT_AUTHORIZE = "/oauth/authorize"
//    }
}

interface ServerAPI {

}