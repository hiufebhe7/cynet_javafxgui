package com.example.demo.net

import com.example.demo.utils.Connect
import com.example.demo.utils.Const
import com.example.demo.utils.MastodonAPI
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

class API {

    private lateinit var _connect: Connect

    val url by lazy {
        "https://${connect.instance}"
    }

    private lateinit var clientId: String
    private lateinit var clientSecret: String
    private lateinit var _mastodon: MastodonAPI
    private var _token = ""

    val connect get() = _connect

    val token get() = _token

    val mastodon get() = _mastodon

    var onToken = { e: String -> }
    var onError = { e: String -> }

    constructor(connect: Connect) {
        this._connect = connect
        initAPI()
        initApp()
    }

    constructor(connect: Connect, token: String) {
        this._token = token
        this._connect = connect
        initAPI()
    }

    private fun initAPI() {

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                        val originalRequest: Request = chain.request()
                        val builder = originalRequest.newBuilder()
                        builder.header("Authorization", String.format("Bearer %s", _token))
                        val newRequest = builder.build()
                        return chain.proceed(newRequest)
                    }
                })
                .dispatcher(Dispatcher())
                .build()

        val gson = GsonBuilder()
                .create()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        _mastodon = retrofit.create(MastodonAPI::class.java)
    }

    private fun initApp() {

        val pattern = Pattern.compile("^[A-Za-z]+\\.[A-Za-z]+$")

        if (!pattern.matcher(connect.instance).find()) {
            return
        }

        _mastodon.apps(Const.APP_NAME, Const.OAUTH_REDIRECT_HOST, Const.SCOPES)
                .enqueue(object : Callback<OAuth?> {
                    override fun onFailure(p0: Call<OAuth?>, p1: Throwable) {
                        onToken(p1.toString())
                    }

                    override fun onResponse(p0: Call<OAuth?>, p1: Response<OAuth?>) {
                        val body = p1.body()!!
                        clientId = body.clientId!!
                        clientSecret = body.clientSecret!!
                        initToken()
                    }
                })
    }

    private fun initToken() {
        _mastodon.token(clientId, clientSecret, Const.SCOPES, Const.GRANT_TYPE, connect.username, connect.password)
                .enqueue(object : Callback<AccessToken?> {
                    override fun onFailure(p0: Call<AccessToken?>, p1: Throwable) {
                        onToken(p1.toString())
                    }

                    override fun onResponse(p0: Call<AccessToken?>, p1: Response<AccessToken?>) {
                        when (p1.code()) {
                            200 -> {
                                _token = p1.body()!!.accessToken!!
                                initAPI()
                                onToken(token)
                            }
                            else -> {
                                onError(p1.errorBody()!!.byteStream().toString())
                            }
                        }

                    }
                })
    }

//    fun setOnToken(func: (e: String?) -> Unit) {
//        _onCallBack = func
//    }
}