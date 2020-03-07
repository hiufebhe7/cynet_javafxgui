package layout

import com.example.demo.net.API
import com.example.demo.utils.*
import controller.CUpload
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import tornadofx.*
import java.io.File
import java.net.URL
import java.util.*

class CSimpleMain : Controller(), Initializable {

    @FXML
    private lateinit var root: Pane
    @FXML
    private lateinit var btnDownload: Button
    @FXML
    private lateinit var btnUpload: Button

    init {
        Config.single.load()
//        Config.single.managerConnect
//        println()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        btnUpload.setOnMouseClicked {
            val resource = javaClass.getResource("/layout/upload.fxml")
            val loader = FXMLLoader()
            loader.location = resource
            val content = loader.load<VBox>()
            val controller = loader.getController<CUpload>()

            val stage = Stage()
            controller.lateinit(stage)

            stage.initModality(Modality.APPLICATION_MODAL)
            stage.isResizable = false
            val scene = Scene(stage.pane())
            scene.root = content
            stage.scene = scene
            stage.show()
        }

        btnDownload.setOnMouseClicked {
            val resource = javaClass.getResource("/layout/layout_download.fxml")
            val loader = FXMLLoader()
            loader.location = resource
            val content = loader.load<VBox>()
            val controller = loader.getController<CDownload>()

            val stage = Stage()
            controller.lateinit(stage)

            stage.initModality(Modality.APPLICATION_MODAL)
            val scene = Scene(stage.pane())
            scene.root = content
            stage.scene = scene
            stage.show()
        }

//        testUoload()
//        testDownload()
//        testTaskUpload()
//        testTaskDownload()
//        val file = File("D:\\m\\pjs\\media\\t.mp4")
//        val out = file.outputStream()
//        val stream = StreamDownload("http://vfx.mtime.cn/Video/2019/03/21/mp4/190321153853126488.mp4")
//        stream.onStart = { contentLength: Int ->
//            println(contentLength)
//        }
//        stream.onReceive = { bytes: ByteArray ->
//            println(bytes.size)
//            out.write(bytes)
//        }
//        stream.onComplete = {
//            out.close()
//            println("onComplete")
//        }
//        stream.onError = {
//            out.close()
//            println(it)
//        }
//        stream.start()
    }

    fun testUoload() {
        val connect = Connect("panic.social", "hiufebhe7@outlook.com", "adsaf4234")
        val api = API(connect)
        api.onToken = {
            //            val file = File("D:\\m\\pjs\\media\\lg_20mb.mp4")
            val file = File("D:\\m\\pjs\\media\\xz.zip")
            val upload = Upload(connect, file.name, file.parentFile.path, Const.UNIT_1MB * 10)
            val task = Task(upload, api)
            task.onReadyStart = {
                println("onReadyStart")
            }
            task.onReadyUpdate = { total: Int, size: Int, len: Int ->
                println("onReadySend:${total / size.toDouble()}")
            }
            task.onReadyProgress = { total: Int, size: Int ->
                println("onReadyProgress:${total / size.toDouble()}")
            }
            task.onReadyError = {
                println("onReadyError:${it}")
            }
            task.onReadyComplete = {
                println("onReadyComplete")
            }
            task.onStart = {
                println("onStart")
            }
            task.onUpdate = { total: Int, size: Int, len: Int ->
                println("onSend:${total / size.toDouble()}")
            }
            task.onProgress = { total: Int, size: Int ->
                println("onProgress:${total / size.toDouble()}")
            }
            task.onError = {
                println("onError:${it}")
            }
            task.onComplete = {
                it?.let {
                    println(it)
                }
            }
            task.run()
        }
    }

    fun testDownload() {
        val url = "cynet:?p=https:v=1:i=panic.social:u=aHR0cHM6Ly91c2VyLWNvbnRlbnRzLnBhbmljLnNvY2lhbC9tZWRpYV9hdHRhY2htZW50cy9maWxlcy8wMDAvMDAxLzQ2NC9vcmlnaW5hbC9lNTYyNjU2MGQ3YmQ4OTE2LmdpZg=="
        val download = Download(cynet = url, path = "D:\\m\\pjs\\media")
        val task = Task(download, null)
        task.onReadyStart = {
            println("onReadyStart")
        }
        task.onReadyUpdate = { total: Int, size: Int, len: Int ->
            println("onReadyUpdate:${total / size.toDouble()}")
        }
        task.onReadyProgress = { total: Int, size: Int ->
            println("onReadyProgress:${total / size.toDouble()}")
        }
        task.onReadyError = {
            println("onReadyError:${it}")
        }
        task.onReadyComplete = {
            println("onReadyComplete")
        }
        task.onStart = {
            println("onStart")
        }
        task.onUpdate = { total: Int, size: Int, len: Int ->
            println("onUpdate:${total / size.toDouble()}")
        }
        task.onProgress = { total: Int, size: Int ->
            println("onProgress:${total / size.toDouble()}")
        }
        task.onError = {
            println("onError:${it}")
        }
        task.onComplete = {
            println("onComplete")
        }
        task.run()
    }

    fun testTaskUpload() {

//        val resource = javaClass.getResource("/layout/task.fxml")
//        val loader = FXMLLoader()
//        loader.location = resource
//        val content = loader.load<Pane>()
//
//        val taskui = loader.getController<CTask>()
////        val file = File("D:\\m\\pjs\\media\\lg_20mb.mp4")
//        val file = File("D:\\m\\pjs\\media\\xz.zip")
////        val connect = Connect("cmx-im.work", "hiufebhe7@outlook.com", "adsaf4234")
//        val connect = Connect("panic.social", "hiufebhe7@outlook.com", "adsaf4234")
//        val upload = Upload(connect, file.name, file.parentFile.path, Const.UNIT_1KB * 300)
//        val api = API(connect)
//        val task = Task(upload, api)
//
//        task.onReadyStart = {
//            println("onReadyStart")
//            val p = task.pipe!!
//            Platform.runLater {
//                taskui.sizeAll = p.sizeAll
//                taskui.total = p.size
//            }
//        }
//        task.onReadyUpdate = { total: Int, size: Int, len: Int ->
//            println("onReadySend:${total / size.toDouble()}")
//            Platform.runLater {
//                taskui.sizeLen += len
//                taskui.progress = total / size.toDouble()
//            }
//        }
//        task.onReadyProgress = { total: Int, size: Int ->
//            println("onReadyProgress:${total / size.toDouble()}")
//            Platform.runLater {
//                taskui.sizeAll = size
//                taskui.total = total
//            }
//        }
//        task.onReadyError = {
//            println("onReadyError:${it}")
//        }
//        task.onReadyComplete = {
//            println("onReadyComplete")
//        }
//        task.onStart = {
//            println("onStart")
//        }
//        task.onUpdate = { total: Int, size: Int, len: Int ->
//            println("onSend:${total / size.toDouble()}")
//        }
//        task.onProgress = { total: Int, size: Int ->
//            println("onProgress:${total / size.toDouble()}")
//        }
//        task.onError = {
//            println("onError:${it}")
//        }
//        task.onComplete = {
//            Platform.runLater {
//                taskui.playDing()
//            }
//            println(it)
//        }
//        task.onExit = {
//            Platform.runLater {
//                taskui.active(true)
//            }
//        }
//        api.onToken = {
//            //            println(it)
//            Platform.runLater {
//                taskui.start()
//            }
//        }
//        api.onError = {
//
//        }
//        taskui.lateInit( task,)
//        taskui.free()
//
//        val stage = Stage()
//        val scene = Scene(stage.pane())
//        scene.root = content
//        stage.scene = scene
//        stage.initModality(Modality.WINDOW_MODAL)
//        stage.show()
    }

    fun testTaskDownload() {

//        val resource = javaClass.getResource("/layout/task.fxml")
//        val loader = FXMLLoader()
//        loader.location = resource
//        val content = loader.load<Pane>()
//
//        val taskui = loader.getController<CTask>()
////        val file = File("D:\\m\\pjs\\media\\xz.zip")
////        val connect = Connect("mstdn.jp", "hiufebhe7@outlook.com", "adsaf4234")
//        val url = "cynet:?p=https:v=1:i=panic.social:f=xz.zip:u=aHR0cHM6Ly91c2VyLWNvbnRlbnRzLnBhbmljLnNvY2lhbC9tZWRpYV9hdHRhY2htZW50cy9maWxlcy8wMDAvMDAxLzcxMy9vcmlnaW5hbC8yODJmZDRmMTQ0YTRkM2NlLmdpZg=="
//        val download = Download(cynet = url, path = "D:\\m\\pjs\\media")
//        val task = Task(download)
//
//        task.onReadyStart = {
//            println("onReadyStart")
//        }
//        task.onReadyUpdate = { total: Int, size: Int, len: Int ->
//            println("onReadyUpdate:${total / size.toDouble()}")
//        }
//        task.onReadyProgress = { total: Int, size: Int ->
//            println("onReadyProgress:${total / size.toDouble()}")
//        }
//        task.onReadyError = {
//            println("onReadyError:${it}")
//        }
//        task.onReadyComplete = {
//            task.run()
//        }
//        task.onStart = {
//            println("onStart")
//            val p = task.pipe!!
//            Platform.runLater {
//                taskui.sizeAll = p.sizeAll
//                taskui.total = p.size
//            }
//        }
//        task.onUpdate = { total: Int, size: Int, len: Int ->
//            println("onUpdate:${total / size.toDouble()}")
//            Platform.runLater {
//                taskui.sizeLen += len
//                taskui.progress = total / size.toDouble()
//            }
//        }
//        task.onProgress = { total: Int, size: Int ->
//            println("onProgress:${total / size.toDouble()}")
//            Platform.runLater {
//                taskui.sizeAll = size
//                taskui.total = total
//            }
//        }
//        task.onError = {
//            println("onError:${it}")
//        }
//        task.onComplete = {
//            Platform.runLater {
//                taskui.playDing()
//            }
//        }
//        taskui.lateInit( task)
//        taskui.free()
//        taskui.decode()
//
//        val stage = Stage()
//        val scene = Scene(stage.pane())
//        scene.root = content
//        stage.scene = scene
//        stage.show()
    }
}