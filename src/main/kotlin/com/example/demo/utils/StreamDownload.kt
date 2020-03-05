import okhttp3.*
import java.io.IOException
import java.io.InputStream


class StreamDownload {

    private val okHttpClient = OkHttpClient()
    private val url: String

    var onStart = { contentLength: Int -> }
    var onReceive = { bytes: ByteArray -> }
    var onComplete = {}
    var onError = { e: String -> }


    var len = 0
    var first = true

    constructor(url: String) {
        this.url = url
    }

    fun start() {
        val request = Request.Builder()
                .url(url)
                .build()

        try {
            val buf = ByteArray(2048)
            val response = okHttpClient.newCall(request).execute()
            val body = response.body()!!
            val input = body.byteStream()
            while (input.read(buf).also({ len = it }) != -1) {
                if (first) {
                    first = false
                    onStart(body.contentLength().toInt())
                }
                onReceive(buf.copyOfRange(0, len))
            }
            onComplete()
        } catch (e: Exception) {
            onError(e.toString())
        }

//        okHttpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call?, e: IOException?) { // 下载失败
//                onError()
//            }
//
//            override fun onResponse(call: Call?, response: Response) {
//                val input: InputStream
//                val buf = ByteArray(2048)
//                var len = 0
//                var first = true
//
//                try {
//                    val body = response.body()!!
//                    input = body.byteStream()
//                    while (input.read(buf).also({ len = it }) != -1) {
//                        if (first) {
//                            first = false
//                            onStart(body.contentLength().toInt())
//                        }
//                        onReceive(buf.copyOfRange(0, len))
//                    }
//                    onComplete()
//                } catch (e: Exception) {
//                    onError()
//                }
//            }
//        })
    }
}