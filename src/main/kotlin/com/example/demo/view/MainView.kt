package com.example.demo.view

import com.example.demo.net.API
import com.example.demo.utils.*
import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import tornadofx.*
import java.io.File


class MainView : View() {

    override val root = layout()

    init {
        primaryStage.isResizable = false
    }

    private fun layout(): Pane {

//        val url = javaClass.getResource("/layout/layout_main.fxml")
//        val url = javaClass.getResource("/layout/layout_download.fxml")
//        val url = javaClass.getResource("/layout/upload.fxml")
//        val url = javaClass.getResource("/layout/task.fxml")
//        val url = javaClass.getResource("/layout/simple_main.fxml"

        val url = javaClass.getResource("/layout/simple_main.fxml")
        val loader = FXMLLoader()
        loader.location = url
        val content = loader.load<Pane>()
        return content
    }
}