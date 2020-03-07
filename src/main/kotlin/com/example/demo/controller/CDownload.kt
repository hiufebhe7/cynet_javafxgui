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
            }
        }
        task.onUpdate = { total: Int, size: Int, len: Int ->
            println("onUpdate:${total / size.toDouble()}")
            Platform.runLater {
//                taskui.sizeLen += len
                taskui.progress = total / size.toDouble()
            }
        }
        task.onProgress = { total: Int, size: Int ->
            println("onProgress:${total / size.toDouble()}")
            Platform.runLater {
                taskui.sizeAll = size
                taskui.size = total
            }
        }
        task.onError = {
            println("onError:${it}")
        }
        task.onComplete = {
            Platform.runLater {
                taskui.playDing()
            }
        }
        task.onExit = {
            Platform.runLater {
                taskui.active(true)
            }
        }
        taskui.lateInit(task,pstage)
        taskui.stop()
        thread {
            taskui.decode()
        }
    }

}