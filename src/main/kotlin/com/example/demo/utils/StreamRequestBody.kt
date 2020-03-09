import com.example.demo.utils.Task
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import okio.Source


class StreamRequestBody : RequestBody {

    val buffer: ByteArray
    val mediaType: MediaType
    var onSend = { total: Int, size: Int, len: Int -> }
    val task: Task

    constructor(type: MediaType, bytes: ByteArray,task: Task) {
        this.buffer = bytes
        this.mediaType = type
        this.task = task
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
                if (!task.runing){
                    break
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