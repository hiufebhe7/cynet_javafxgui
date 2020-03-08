import javafx.application.Platform
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import okio.Source


class StreamRequestBody : RequestBody {

    var buffer: ByteArray
    var mediaType: MediaType?
    var onSend = { total: Int, size: Int, len: Int -> }
    var runing = true

    constructor(type: MediaType?, bytes: ByteArray) {
        this.buffer = bytes
        this.mediaType = type
    }

    override fun contentLength(): Long {
        return buffer.size.toLong()
    }

    override fun contentType(): MediaType? {
        return mediaType
    }

    override fun writeTo(sink: BufferedSink) {
        var source: Source? = null
        try {
            source = Okio.source(buffer.inputStream())
            var total = 0L
            var read = 0L
            while (source.read(sink.buffer(), 1024).also({ read = it }) != -1L) {
                if (!runing){
                    return
                }
                total += read
                sink.flush()
                onSend(total.toInt(), buffer.size, read.toInt())
            }
        } finally {
            source?.close()
        }
    }
}