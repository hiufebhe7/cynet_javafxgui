package layout

import com.example.demo.utils.Const
import com.example.demo.utils.Download
import com.example.demo.utils.Task
import com.example.demo.utils.Upload
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.Modality
import javafx.stage.Stage
import tornadofx.*
import java.net.URL
import java.nio.file.Paths
import java.util.*

class CTask : Controller(), Initializable {

    @FXML
    private lateinit var root: Pane
    @FXML
    private lateinit var lbType: Label
    @FXML
    private lateinit var lbFile: Label
    @FXML
    private lateinit var lbSize: Label
    @FXML
    private lateinit var lbTime: Label
    @FXML
    private lateinit var lbSpeed: Label
    @FXML
    private lateinit var prgItor: ProgressIndicator
    @FXML
    private lateinit var tgbRun: ToggleButton
    @FXML
    private lateinit var btnSetting: Button

    private var task: Task? = null

    private var pstage: Stage? = null

    var progress: Double
        get() = prgItor.progress
        set(value) {
            prgItor.progress = value
        }

    var sizeAll: Int = 0
        set(value) {
            field = value
            var unity = "b"
            var vy = sizeAll.toDouble()
            var fmty = 0

            if (vy > Const.UNIT_1MB) {
                unity = "mb"
                vy /= Const.UNIT_1MB.toFloat()
                fmty = 2
            } else if (vy > Const.UNIT_1KB) {
                unity = "kb"
                vy /= Const.UNIT_1KB.toFloat()
            }
            val stry = String.format("%.${fmty}f", vy) + unity

            val str = "0/$stry"

            lbSize.text = str
        }

    var size: Int = 0
        set(value) {
            field = value
            var unitx = "b"
            var unity = "b"
            var vx = field.toDouble()
            var vy = sizeAll.toDouble()
            var fmtx = 0
            var fmty = 0

            if (vx > Const.UNIT_1MB) {
                unitx = "mb"
                vx /= Const.UNIT_1MB
                fmtx = 2
            } else if (vx > Const.UNIT_1KB) {
                unitx = "kb"
                vx /= Const.UNIT_1KB
            }
            val strx = String.format("%.${fmtx}f", vx) + unitx

            if (vy > Const.UNIT_1MB) {
                unity = "mb"
                vy /= Const.UNIT_1MB.toFloat()
                fmty = 2
            } else if (vy > Const.UNIT_1KB) {
                unity = "kb"
                vy /= Const.UNIT_1KB.toFloat()
            }
            val stry = String.format("%.${fmty}f", vy) + unity

            val str = "$strx/$stry"

            lbSize.text = str
        }

    var path: String
        get() = lbFile.text
        set(value) {
            lbFile.text = value
        }

    var type: String
        get() = lbType.text
        set(value) {
            lbType.text = value
        }

    var sizeLen: Int = 0

    private lateinit var mediaPlayer:MediaPlayer

    override fun initialize(location: URL?, resources: ResourceBundle?) {

//        sizeAll = 512000000
//        speed = 512
//        total = 512
//        progress = .5
//        path = "sadsad"
//        type = "u"

        tgbRun.setOnMouseClicked {
            if (!task!!.loop) {
                run()
            } else {
                stop()
            }
        }

        btnSetting.setOnMouseClicked {

            if (!task!!.runing) {
                val stage = Stage()
                val scene = Scene(stage.pane())

                val vBox = VBox()

                val btnDestroy = Button("destroy")
                btnDestroy.maxWidth = Double.MAX_VALUE
                vBox.add(btnDestroy)
                task?.let {
                    when (it.pipe) {
                        is Upload -> {
                            if (!it.pipe.cynet.equals("")) {
                                val txaCynet = TextArea()
                                txaCynet.text = it.pipe.cynet
                                txaCynet.isWrapText = true
                                txaCynet.maxWidth = Double.MAX_VALUE
                                vBox.add(txaCynet)
                            }
                        }
                    }
                }

                stage.initModality(Modality.APPLICATION_MODAL)
                scene.root = vBox
                stage.scene = scene
                stage.show()

                btnDestroy.setOnMouseClicked {
                    destroy()
                    stage.close()
                }
            }
        }


    }

    fun lateInit(task: Task, stage: Stage?) {
        this.task = task
        this.pstage = stage
        active(true)

        val pipe = task.pipe!!
        when (pipe) {
            is Upload -> type = "U"
            is Download -> type = "D"
        }
        path = Paths.get(pipe.path, pipe.filename).toFile().absolutePath
    }

    fun run() {
        tgbRun.text = "run"
        tgbRun.isSelected = true
        task?.run()
        active(true)
    }

    fun stop() {
        tgbRun.text = "stop"
        tgbRun.isSelected = false
        task?.stop()
    }

    fun stopUI() {
        tgbRun.text = "stop"
        tgbRun.isSelected = false
    }

    fun destroy() {
        root.removeFromParent()
        pstage?.close()
        task?.destroy()
    }

//    fun free() {
//        stop()
//        active(false)
//    }

    fun playDing() {
        val resource = javaClass.getResource("/audio/ding.mp3").toExternalForm()
        val media = Media(resource)
        mediaPlayer = MediaPlayer(media)
        mediaPlayer.play()
    }

    fun active(b: Boolean) {
        tgbRun.isDisable = !b
        btnSetting.isDisable = !b
    }
}