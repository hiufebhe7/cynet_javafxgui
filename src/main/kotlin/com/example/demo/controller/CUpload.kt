package controller

import com.example.demo.net.API
import com.example.demo.utils.*
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TextField
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import layout.CTask
import tornadofx.*
import java.io.File
import java.net.URL
import java.util.*


class CUpload : Controller(), Initializable {

    @FXML
    private lateinit var root: Pane
    @FXML
    private lateinit var ckboxConnects: ChoiceBox<Connect>
    @FXML
    private lateinit var tefInstance: TextField
    @FXML
    private lateinit var tefUsername: TextField
    @FXML
    private lateinit var tefPassword: TextField
    @FXML
    private lateinit var prgload: ProgressIndicator
    @FXML
    private lateinit var textfPath: TextField
    @FXML
    private lateinit var btnOpen: Button
    @FXML
    private lateinit var btnRemove: Button
    @FXML
    private lateinit var btnUpload: Button

    private var stage: Stage? = null

    init {
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        prgload.isVisible = false

        val connects = Config.single.managerConnect.connects
        if (connects.size > 0) {
            ckboxConnects.items.clear()
            ckboxConnects.items.addAll(connects)
            ckboxConnects.value = connects[0]
            syncInput(connects[0])
        }

        ckboxConnects.setOnAction {
            syncValueToInput()
        }

        btnOpen.setOnMouseClicked {
            val fileChooser = FileChooser()
            val path = fileChooser.showOpenDialog(primaryStage)
            path?.let {
                textfPath.text = it.path
            }
        }

        btnRemove.setOnMouseClicked {
            Config.single.managerConnect.remove(ckboxConnects.value)

            syncConfigToData()

            if (ckboxConnects.items.size > 0) {
                ckboxConnects.value = ckboxConnects.items[0]
                syncInput(ckboxConnects.items[0])
            } else {
                val cnull = Connect("", "", "")
                ckboxConnects.value = cnull
                syncInput(cnull)
            }
            Config.single.save()
        }

        btnUpload.setOnMouseClicked {
            lock(true)
            val connect = Connect(tefInstance.text, tefUsername.text, tefPassword.text)
            val input = tefInstance.text != "" && tefUsername.text != "" && tefPassword.text != ""
            val file = File(textfPath.text)
            if (input && file.isFile) {
                stage!!.close()
                Config.single.managerConnect.add(connect)
                Config.single.save()
                val u = Upload(connect, file.name, file.parentFile.path, Const.UNIT_1MB * 3)
                upload(u)
            } else {
                lock(false)
            }
        }
    }

    fun lateinit(stage: Stage) {
        this.stage = stage
    }

    fun syncInput(c: Connect) {
        tefInstance.text = c.instance
        tefUsername.text = c.username
        tefPassword.text = c.password
    }

    fun syncValueToInput() {
        ckboxConnects.value?.let {
            tefInstance.text = it.instance
            tefUsername.text = it.username
            tefPassword.text = it.password
        }
    }


    fun syncConfigToData() {
        val connects = Config.single.managerConnect.connects
        ckboxConnects.items.clear()
        ckboxConnects.items.addAll(connects)
        if (connects.size > 0) {
            ckboxConnects.value = connects[0]
        }
    }

    fun syncInputToConfig() {
        val c = Connect(tefInstance.text, tefUsername.text, tefPassword.text)
        Config.single.managerConnect.add(c)
        Config.single.save()
    }

    fun lock(d: Boolean) {
        ckboxConnects.isDisable = d
        tefInstance.isDisable = d
        tefUsername.isDisable = d
        tefPassword.isDisable = d
        textfPath.isDisable = d
        btnOpen.isDisable = d
        btnRemove.isDisable = d
        btnUpload.isDisable = d
        prgload.isVisible = d
    }

    fun upload(u: Upload) {
        val resource = javaClass.getResource("/layout/task.fxml")
        val loader = FXMLLoader()
        loader.location = resource
        val content = loader.load<Pane>()

        val taskui = loader.getController<CTask>()
        val api = API(u.connect)
        val task = Task(u, api)

        task.onReadyStart = {
            println("onReadyStart")
            val p = task.pipe!!
            Platform.runLater {
                taskui.sizeAll = p.sizeAll
                taskui.size = p.size
            }
        }
        task.onReadyUpdate = { total: Int, size: Int, len: Int ->
            println("onReadySend:${total / size.toDouble()}")
            Platform.runLater {
//                taskui.sizeLen += len
                taskui.progress = total / size.toDouble()
            }
        }
        task.onReadyProgress = { total: Int, size: Int ->
            println("onReadyProgress:${total / size.toDouble()}")
            Platform.runLater {
                taskui.sizeAll = size
                taskui.size = total
            }
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
            Platform.runLater {
                taskui.playDing()
            }
            println(it)
        }
        task.onExit = {
            Platform.runLater {
                taskui.active(true)
            }
        }
        api.onToken = {
            //            println(it)
            Platform.runLater {
                taskui.run()
            }
        }
        api.onError = {

        }

        val stage = Stage()
        val scene = Scene(stage.pane())
        scene.root = content
        stage.scene = scene
        stage.initModality(Modality.WINDOW_MODAL)
        stage.show()

        taskui.lateInit(task,stage)
        taskui.stop()
    }

}
