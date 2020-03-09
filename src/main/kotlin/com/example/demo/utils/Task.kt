package com.example.demo.utils

import StreamRequestBody
import com.example.demo.net.API
import com.example.demo.net.Media
import com.google.gson.Gson
import okhttp3.*
import okio.Buffer
import okio.GzipSink
import okio.GzipSource
import okio.Okio
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.nio.file.Paths
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

interface Pipe {
    var filename: String
    val path: String
    var sizeAll: Int
    var size: Int
    var seek: Int
    val urls: ArrayList<String>
    var cynet: String
}

data class Upload(
        val connect: Connect,
        override var filename: String,
        override val path: String,
        val chunk: Int,
        override var sizeAll: Int = 0,
        override var size: Int = 0,
        override var seek: Int = -1,
        override val urls: ArrayList<String> = ArrayList(),
        override var cynet: String = ""
) : Pipe

data class Download(
        override var filename: String = "",
        override var path: String = "",
        override var sizeAll: Int = 0,
        override var size: Int = 0,
        override var seek: Int = -1,
        override var urls: ArrayList<String> = ArrayList(),
        override var cynet: String = ""
) : Pipe

data class CyPack(
        val filename: String,
        val sizeAll: Int,
        val urls: ArrayList<String>
)

class Task {

    val pipe: Pipe?
    val api: API?
    val maxRetry: Int

    var runing = false
        get() = field
        set

    private var thread: Thread? = null
    private var bytesNull: ByteArray

    var onReadyStart = { }
    var onReadyUpdate = { total: Int, size: Int, len: Int -> }
    var onReadyProgress = { total: Int, size: Int -> }
    var onReadyComplete = { msg: String? -> }
    var onReadyMessage = { msg: String -> }

    var onStart = { }
    var onUpdate = { total: Int, size: Int, len: Int -> }
    var onProgress = { total: Int, size: Int -> }
    var onComplete = { msg: String? -> }
    var onMessage = { msg: String -> }

    //    var onRun = { }
//    var onStop = { }
//    var onDestroy = { }
    var onExit = { code: Int -> }

    private var fileTmp: File? = null

    private var endPoint = true
    private var complete = false

    private var streamRequest: StreamRequestBody? = null

    var cyPack: CyPack? = null
        set
        get() = field

    init {
        val resourceNull = javaClass.getResource(Const.FILE_NULL)
        bytesNull = resourceNull.readBytes()
        this.maxRetry = 9
    }

    constructor(pipe: Pipe, api: API?) {
        this.pipe = pipe
        this.api = api
    }

    constructor(pipe: Pipe) {
        this.pipe = pipe
        this.api = null
    }

    fun run() {
        if (!runing && endPoint) {
            runing = true
            endPoint = false
            thread = thread {
                when (pipe) {
                    is Upload -> upload()
                    is Download -> download()
                }
            }
            println("task run")
        }
    }

    fun stop() {
        runing = false
        println("task stop")
        if (endPoint) {
            callExit(0)
        }
    }

    fun destroy() {
        fileTmp?.delete()
        println("task destroy")
    }

    private fun upload() {

        val datau = pipe as Upload

        val filePath = Paths.get(datau.path, datau.filename).toFile().path
        val strName = datau.connect.toString() + filePath
        val base64Name = Base64.getEncoder().encode(strName.toByteArray())
        val strFileName = base64Name.toString(Charset.forName("ascii"))

        fileTmp = File(Paths.get(Const.PATH_DIR_CACHE, "u.$strFileName.json").toFile().path)
        fileTmp!!.let {
            if (it.exists()) {
                val input = it.inputStream()
                val bytesGson = input.readBytes()
                val gu = Gson().fromJson(bytesGson.toString(Charset.forName("ascii")), Upload::class.java)
                gu?.let {
                    pipe.sizeAll = gu.sizeAll
                    pipe.size = gu.size
                    pipe.seek = gu.seek
                    pipe.urls.clear()
                    pipe.urls.addAll(gu.urls)
                    pipe.cynet = gu.cynet
                }
            }
        }

        val fileSource = File(filePath)
        datau.sizeAll = fileSource.length().toInt()
        val inputSource = fileSource.inputStream()
        val bytesSource = ByteArray(datau.chunk)
        var seekRead = -1
        var sizeRead = 0

        onReadyStart()
        while (runing) {

            sizeRead = inputSource.read(bytesSource)
            if (sizeRead == -1) {
                break
            }

            seekRead++
            if (datau.seek >= seekRead) {
                continue
            }

            val bytesPart = bytesNull + bytesSource.copyOfRange(0, sizeRead)
            streamRequest = StreamRequestBody(MediaType.parse(Const.PACK_MEDIATYPE)!!, bytesPart, this)
            streamRequest!!.onSend = onReadyUpdate
            val multipart = MultipartBody.Part.createFormData(Const.PACK_NAME, Const.PACK_FILENAME, streamRequest!!)

            var retry = 0
            while (true) {

                if (retry > maxRetry) {
                    runing = false
                    break
                }
                retry++

                try {
                    val response = api!!.mastodon
                            .uploadMedia(multipart)
                            .execute()

                    val media = response.body()!!

                    datau.seek++
                    datau.urls.add(media.url)
                    datau.size += sizeRead

                    onReadyProgress(datau.size, datau.sizeAll)
                    break
                } catch (e: Exception) {
                    if (runing) {
                        onReadyMessage("upload file error,retry $retry")
                        continue
                    } else {
                        break
                    }
                }
            }

        }

        val gson = Gson().toJson(datau)
        val outTmp = fileTmp!!.outputStream()
        outTmp.write(gson.toByteArray())
        outTmp.flush()
        outTmp.close()

        if (runing) {
            onReadyComplete(null)
            encodeCynet()
        } else {
            callExit(0x01)
        }
    }

    private fun encodeCynet() {

        val datau = pipe as Upload

        if (!datau.cynet.equals("")) {
            onComplete(datau.cynet)
            callExit(0)
            return
        }

        onStart()

        val dataPack = CyPack(datau.filename, datau.size, datau.urls)
        val gsondata = Gson().toJson(dataPack)
        val buffer = Buffer()
        val input = buffer.inputStream()
        val output = buffer.outputStream()
        val sink = Okio.sink(output)
        val gzipSink = Okio.buffer(GzipSink(sink))
        gzipSink.writeUtf8(gsondata)
        gzipSink.flush()
        gzipSink.close()

        val bytesPart = input.readBytes()
        val bytesNullPart = bytesNull + bytesPart

//        val requestBody = RequestBody.create(MediaType.parse(Const.PACK_MEDIATYPE), bytesNullPart)
        streamRequest = StreamRequestBody(MediaType.parse(Const.PACK_MEDIATYPE)!!, bytesNullPart, this)
        streamRequest!!.onSend = onUpdate
        val multipart = MultipartBody.Part.createFormData(Const.PACK_NAME, Const.PACK_FILENAME, streamRequest!!)

        var media: Media? = null
        var retry = 0
        while (runing) {

            if (retry > maxRetry) {
                runing = false
                break
            }
            retry++

            try {
                val request = api!!.mastodon
                        .uploadMedia(multipart)
                        .execute()
                media = request.body()!!
                onProgress(bytesPart.size, bytesPart.size)
                break
            } catch (e: Exception) {
                onReadyMessage("upload pack error,retry $retry")
                continue
            }
        }

        if (!runing) {
            fileTmp!!.outputStream().close()
            callExit(0x02)
        }

        val strBase64 = Base64.getEncoder().encode(media!!.url.toByteArray())
                .toString(Charset.forName("ascii"))

        val str = "${Const.APP_NAME}:?v=${Const.VERSION}:i=${datau.connect.instance}:u=${strBase64}"
        datau.cynet = str

        val gson = Gson().toJson(datau)
        val outTmp = fileTmp!!.outputStream()
        outTmp.write(gson.toByteArray())
        outTmp.flush()
        outTmp.close()

        onComplete(str)
        runing = false
        callExit(0)
    }

    fun decodeCynet() {

        onReadyStart()

        val pipe = pipe as Download

        val cynet = pipe.cynet
        val str = cynet.split(":u=")
        val params = str[0]
        val base64Url = str[1]
        val url = Base64.getUrlDecoder().decode(base64Url).toString(Charset.forName("ascii"))
        val builder = Request.Builder()
        val request = builder
                .url(url)
                .get()
                .build()
        val http = OkHttpClient.Builder().build()

        var dataPack: ByteArray? = null

        var retrey = 0
        while (maxRetry > retrey) {
            retrey++
            try {
                val response = http.newCall(request)
                        .execute()
                val body = response.body()!!
                val buffer = ByteArray(2048)
                val input = body.byteStream()
                var first = true
                var len = 0
                var total = 0
                var contentLength = 0
                val bufferNullPack = Buffer()
                while (input.read(buffer).also({ len = it }) != -1) {
                    if (first) {
                        first = false
                        contentLength = body.contentLength().toInt()
                    }
                    total += len
                    bufferNullPack.write(buffer.copyOfRange(0, len))
                    onReadyUpdate(total, contentLength, len)
                }
                val dataNullPack = bufferNullPack.readByteArray()
                dataPack = dataNullPack.copyOfRange(bytesNull.size, dataNullPack.size)
                onReadyProgress(dataPack.size, dataPack.size)
                break
            } catch (e: Exception) {
                onReadyMessage(e.toString())
            }
        }

        if (dataPack!!.size <= 0) {
            callExit(0x03)
            return
        }

        val buffer = Buffer()
        val input = buffer.inputStream()
        val output = buffer.outputStream()
        output.write(dataPack)
        output.flush()
        output.close()
        val source = Okio.source(input)
        val gzipSource = Okio.buffer(GzipSource(source))
        val bytes = gzipSource.readByteArray()
        val strCypack = bytes.toString(Charset.forName("ascii"))
        gzipSource.close()
        cyPack = Gson().fromJson(strCypack, CyPack::class.java)
        cyPack!!.let {
            if (pipe.filename.equals("")) {
                pipe.filename = it.filename
            }
            pipe.sizeAll = it.sizeAll
            pipe.urls = it.urls
        }

        onReadyComplete(null)
    }

    fun download() {

        val pd = pipe as Download
        val filePath = Paths.get(pd.path, pd.filename).toFile().path

        val base64Path = Base64.getEncoder().encode(filePath.toByteArray())
        val strFileName = base64Path.toString(Charset.forName("ascii"))
        fileTmp = File(Paths.get(Const.PATH_DIR_CACHE, "d.$strFileName.json").toFile().path)
        fileTmp!!.let {
            if (it.exists()) {
                val input = it.inputStream()
                val bytesGson = input.readBytes()
                val od = Gson().fromJson(bytesGson.toString(Charset.forName("ascii")), Download::class.java)
                od?.let {
                    pipe.filename = od.filename
                    pipe.path = od.path
                    pipe.sizeAll = od.sizeAll
                    pipe.size = od.size
                    pipe.seek = od.seek
                    pipe.urls.clear()
                    pipe.urls.addAll(od.urls)
                }
            }
        }

        val fileDownload = Paths.get(pd.path, pd.filename).toFile()
        val outputDownload = FileOutputStream(fileDownload, true)

        onStart()
        var retry = 0
        while (runing) {

            if (retry > maxRetry) {
                runing = false
                break
            }
            retry++

            if (++pd.seek >= pd.urls.size) {
                break
            }

            val url = pd.urls[pd.seek]

            val builder = Request.Builder()
            val request = builder
                    .url(url)
                    .get()
                    .build()

            val http = OkHttpClient.Builder().build()
            var dataPack: ByteArray? = null
            try {
                val response = http.newCall(request)
                        .execute()
                val body = response.body()!!
                val buffer = ByteArray(2048)
                val input = body.byteStream()
                var first = true
                var len = 0
                var size = 0
                var sizeAll = 0
                val bufferNullPack = Buffer()
                while (input.read(buffer).also({ len = it }) != -1) {
                    if (!runing) {
                        break
                    }
                    if (first) {
                        first = false
                        sizeAll = body.contentLength().toInt()
                    }
                    size += len
                    bufferNullPack.write(buffer.copyOfRange(0, len))
                    onUpdate(size, sizeAll, len)
                }
                if (size == sizeAll) {
                    val dataNullPack = bufferNullPack.readByteArray()
                    dataPack = dataNullPack.copyOfRange(bytesNull.size, dataNullPack.size)
                    outputDownload.write(dataPack)
                    outputDownload.flush()
                    pd.size += dataPack.size
                    onProgress(pd.size, pd.sizeAll)
                } else {
                    pd.seek--
                }
            } catch (e: Exception) {
                if (runing) {
                    onMessage(e.toString())
                } else {
                    break
                }
            }
        }
//        println("download over")
        val gson = Gson().toJson(pd)
        val outputTmp = fileTmp!!.outputStream()
        outputTmp.write(gson.toByteArray())
        outputTmp.flush()
        outputTmp.close()
        outputDownload.close()
        if (runing) {
            callExit(0)

            runing = false
            onComplete(null)
        } else {
            callExit(0x04)
        }
    }

    fun callExit(code: Int) {
        endPoint = true
        onExit(code)
//        println(code)
    }

    fun tWrite(bytes: ByteArray, seek: Int) {
        val file = File("D:\\m\\pjs\\media\\part_${seek}.gif")
        val out = file.outputStream()
        out.write(bytes)
        out.flush()
        out.close()
    }
}