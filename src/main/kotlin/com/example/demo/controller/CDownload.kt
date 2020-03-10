package layout

import com.example.demo.utils.Const
import com.example.demo.utils.Download
import com.example.demo.utils.Task
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import tornadofx.*
import java.io.File
import java.net.URL
import java.nio.file.Paths
import java.util.*
import kotlin.concurrent.thread

class CDownload : Controller(), Initializable {

    @FXML
    private lateinit var root: Pane
    @FXML
    private lateinit var textaCynet: TextArea
    @FXML
    private lateinit var textfPath: TextField
    @FXML
    private lateinit var textfFilename: TextField
    @FXML
    private lateinit var btnChange: Button
    @FXML
    private lateinit var btnDownload: Button
    @FXML
    private lateinit var btnCancle: Button

    private var pstage: Stage?=null

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        val current = File(Const.PATH_DIR_DOWNLOAD).absolutePath
        textfPath.text = current

        btnChange.setOnMouseClicked {
            val directoryChooser = DirectoryChooser()
            val dir = directoryChooser.showDialog(primaryStage)
            dir?.let {
                textfPath.text = dir.path
            }
        }

        btnCancle.setOnMouseClicked {
            pstage?.close()
        }

        btnDownload.setOnMouseClicked {

            val input = textaCynet.text.equals("") || textfPath.text.equals("")
            if (!input) {
                download()
            }
        }
    }

    fun lateinit(stage: Stage) {
        this.pstage = stage
    }

    fun download() {
        val download = Download(cynet = textaCynet.text, filename = textfFilename.text, path = textfPath.text)
        val task = Task(download)

        val resource = javaClass.getResource("/layout/task.fxml")
        val loader = FXMLLoader()
        loader.location = resource
        val content = loader.load<Pane>()
        val taskui = loader.getController<CTask>()

        pstage!!.let {
            val scene = Scene(it.pane())
            scene.root = content
            it.scene = scene
            it.show()
        }

        task.onReadyStart = {
            println("onReadyStart")
        }
        task.onReadyUpdate = { size: Int, sizeAll: Int, len: Int ->
            println("onReadyUpdate:${size / sizeAll.toDouble()}")
            Platform.runLater {
                taskui.progress = size/sizeAll.toDouble()
            }
        }
        task.onReadyProgress = { size: Int, sizeAll: Int ->
            println("onReadyProgress:${size / sizeAll.toDouble()}")
        }
        task.onReadyMessage = {
            println("onReadyError:${it}")
        }
        task.onReadyComplete = {
            Platform.runLater {
                taskui.run()
            }
        }
        task.onStart = {
            println("onStart")
            val p = task.pipe!!
            Platform.runLater {
                taskui.sizeAll = p.sizeAll
                taskui.size = p.size
                taskui.path = Paths.get(p.path , p.filename).toFile().absolutePath
            }
        }
        task.onUpdate = { size: Int, sizeAll: Int, len: Int ->
            println("onUpdate:${size / sizeAll.toDouble()}")
            Platform.runLater {
                taskui.progress = size / sizeAll.toDouble()
            }
        }
        task.onProgress = { size: Int, sizeAll: Int ->
            println("onProgress:${size / sizeAll.toDouble()}")
            Platform.runLater {
                taskui.sizeAll = sizeAll
                taskui.size = size
            }
        }
        task.onMessage = {
            println("onMessage:${it}")
        }
        task.onComplete = {
            Platform.runLater {
                taskui.playDing()
            }
        }
        task.onExit = {
            Platform.runLater {
                if (it > 0x0f) {
                    taskui.stopUI()
                }
                if (it ==0) {
                    taskui.stop()
                }
                taskui.active(true)
            }
            println("exit code $it")
        }
        taskui.lateInit(task,pstage)
        taskui.active(false)
        taskui.run()
    }

}