package controller

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import layout.CTask
import tornadofx.*
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class LayoutMain : Controller(), Initializable {

    @FXML
    private lateinit var root: Pane

    @FXML
    private lateinit var listView: ListView<Pane>

    private val tasks = ArrayList<CTask>()

    override fun initialize(location: URL?, resources: ResourceBundle?) {

//        val connect = Connect("panic.social", "hiufebhe7@outlook.com", "adsaf4234")
//        val connect = Connect("mstdn.jp", "hiufebhe7@outlook.com", "adsaf4234")
//        val api = API(connect, "ynXkSvAvMwVu6fQDt_kx9HspCA1qVp5MP4g-LH983EA")
//        val upload = Upload(connect, "xajh.zip","D:\\m\\pjs\\media\\")
//        val task = Task(upload, api)
//
//        task.run()
//        task.setOnProgress {
//            println("task1:${it}")
//        }
//        task.setOnComplete {
//            println(it)
//        }
//        task.setOnError {
//            println(it)
//        }

//        val url = "cynet:?p=https:v=1:i=mstdn.jp:u=aHR0cHM6Ly9tZWRpYS5tc3Rkbi5qcC9tZWRpYV9hdHRhY2htZW50cy9maWxlcy8wMjcvOTgxLzg5Ni9vcmlnaW5hbC9hMzYyYzg0NjQ4NzE1NjFlLmdpZg=="
//        val download = Download(cynet = url, path = "D:\\m\\pjs\\media\\",filename = "xz.zip")
//        val task = Task(download)
//        task.run()
//
//        task.setOnProgress {
//            println("task1:${it}")
//        }
//        task.setOnComplete {
//            println("download complete")
//        }
//        task.setOnError {
//            println(it)
//        }

        val resource = javaClass.getResource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<?import javafx.geometry.*?>\n<?import javafx.scene.control.*?>\n<?import javafx.scene.layout.*?>\n<?import javafx.scene.text.*?>\n\n<VBox fx:id=\"root\" prefHeight=\"60.0\" prefWidth=\"400.0\" xmlns=\"http://javafx.com/javafx/10.0.2-internal\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"layout.CTask\">\n   <children>\n      <GridPane alignment=\"CENTER\" prefHeight=\"55.0\" prefWidth=\"388.0\">\n        <columnConstraints>\n          <ColumnConstraints halignment=\"CENTER\" hgrow=\"SOMETIMES\" maxWidth=\"1.7976931348623157E308\" minWidth=\"30.0\" />\n          <ColumnConstraints hgrow=\"SOMETIMES\" maxWidth=\"1.7976931348623157E308\" />\n            <ColumnConstraints hgrow=\"SOMETIMES\" maxWidth=\"-Infinity\" />\n        </columnConstraints>\n        <rowConstraints>\n          <RowConstraints maxHeight=\"20.0\" minHeight=\"50.0\" prefHeight=\"0.0\" valignment=\"CENTER\" vgrow=\"SOMETIMES\" />\n        </rowConstraints>\n         <children>\n            <GridPane prefHeight=\"0.0\" prefWidth=\"368.0\" GridPane.columnIndex=\"1\">\n              <columnConstraints>\n                <ColumnConstraints hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n              </columnConstraints>\n              <rowConstraints>\n                <RowConstraints maxHeight=\"1.7976931348623157E308\" minHeight=\"20.0\" />\n                  <RowConstraints minHeight=\"16.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n                  <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n              </rowConstraints>\n               <children>\n                  <GridPane>\n                    <columnConstraints>\n                      <ColumnConstraints hgrow=\"SOMETIMES\" maxWidth=\"306.0\" minWidth=\"10.0\" prefWidth=\"300.0\" />\n                      <ColumnConstraints hgrow=\"SOMETIMES\" maxWidth=\"224.0\" minWidth=\"10.0\" prefWidth=\"68.0\" />\n                    </columnConstraints>\n                    <rowConstraints>\n                      <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n                    </rowConstraints>\n                     <children>\n                        <HBox prefHeight=\"100.0\" prefWidth=\"200.0\" GridPane.columnIndex=\"1\">\n                           <children>\n                              <ToggleButton fx:id=\"tgbRun\" minWidth=\"-Infinity\" mnemonicParsing=\"false\" text=\"r\" />\n                              <Button fx:id=\"btnSetting\" mnemonicParsing=\"false\" text=\"s\" />\n                           </children>\n                        </HBox>\n                        <Label fx:id=\"lbFile\" prefHeight=\"10.0\" text=\"file.zip\" />\n                     </children>\n                  </GridPane>\n                  <ProgressBar fx:id=\"prg\" maxWidth=\"1.7976931348623157E308\" prefHeight=\"4.0\" prefWidth=\"0.0\" progress=\"0.0\" GridPane.rowIndex=\"1\">\n                     <GridPane.margin>\n                        <Insets />\n                     </GridPane.margin>\n                     <opaqueInsets>\n                        <Insets />\n                     </opaqueInsets>\n                  </ProgressBar>\n                  <GridPane GridPane.rowIndex=\"2\">\n                    <columnConstraints>\n                      <ColumnConstraints hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n                      <ColumnConstraints halignment=\"CENTER\" hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n                        <ColumnConstraints halignment=\"RIGHT\" hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n                    </columnConstraints>\n                    <rowConstraints>\n                      <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" vgrow=\"SOMETIMES\" />\n                    </rowConstraints>\n                     <children>\n                        <Label fx:id=\"lbSpeed\" text=\"0mb\">\n                           <font>\n                              <Font size=\"10.0\" />\n                           </font>\n                           <GridPane.margin>\n                              <Insets />\n                           </GridPane.margin>\n                        </Label>\n                        <Label fx:id=\"lbTime\" text=\"00:00:00\" GridPane.columnIndex=\"1\">\n                           <font>\n                              <Font size=\"10.0\" />\n                           </font>\n                        </Label>\n                        <Label fx:id=\"lbSecondSpeed\" text=\"0kb/s\" GridPane.columnIndex=\"2\">\n                           <font>\n                              <Font size=\"10.0\" />\n                           </font>\n                        </Label>\n                     </children>\n                  </GridPane>\n               </children>\n               <GridPane.margin>\n                  <Insets left=\"4.0\" right=\"4.0\" />\n               </GridPane.margin>\n            </GridPane>\n            <Label fx:id=\"lbTaskType\" prefHeight=\"10.0\" text=\"U\" GridPane.columnIndex=\"0\" />\n         </children>\n      </GridPane>\n   </children>\n</VBox>\n")
        val loader = FXMLLoader()
        loader.location = resource
        val content = loader.load<Pane>()
        val controller = loader.getController<CTask>()
//        controller.config(task)

        listView.items.add(content)

    }

    @FXML
    fun openUploadDialog() {
        val resource = javaClass.getResource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<?import javafx.geometry.*?>\n<?import javafx.scene.control.*?>\n<?import javafx.scene.layout.*?>\n\n<VBox maxHeight=\"-Infinity\" maxWidth=\"-Infinity\" minHeight=\"-Infinity\" minWidth=\"-Infinity\" prefWidth=\"600.0\" xmlns=\"http://javafx.com/javafx/10.0.2-internal\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"controller.LayoutUpload\">\n    <children>\n        <GridPane>\n            <columnConstraints>\n                <ColumnConstraints halignment=\"RIGHT\" maxWidth=\"-Infinity\" minWidth=\"-Infinity\" />\n                <ColumnConstraints hgrow=\"SOMETIMES\" minWidth=\"10.0\" prefWidth=\"100.0\" />\n            </columnConstraints>\n            <rowConstraints>\n                <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" valignment=\"BOTTOM\" vgrow=\"SOMETIMES\" />\n                <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" valignment=\"BOTTOM\" vgrow=\"SOMETIMES\" />\n                <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" valignment=\"BOTTOM\" vgrow=\"SOMETIMES\" />\n                <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" valignment=\"BOTTOM\" vgrow=\"SOMETIMES\" />\n                <RowConstraints minHeight=\"10.0\" prefHeight=\"30.0\" valignment=\"BOTTOM\" vgrow=\"SOMETIMES\" />\n            </rowConstraints>\n            <children>\n                <ChoiceBox fx:id=\"ckboxConnects\" prefWidth=\"9999.0\" GridPane.columnIndex=\"1\" />\n                <Label text=\"instance:\" GridPane.rowIndex=\"1\">\n                    <GridPane.margin>\n                        <Insets left=\"2.0\" right=\"2.0\" />\n                    </GridPane.margin>\n                </Label>\n                <TextField fx:id=\"tefInstance\" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"1\" />\n                <TextField fx:id=\"tefUsername\" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"2\" />\n                <TextField fx:id=\"tefPassword\" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"3\" />\n                <Label text=\"username:\" GridPane.rowIndex=\"2\">\n                    <GridPane.margin>\n                        <Insets left=\"2.0\" right=\"2.0\" />\n                    </GridPane.margin>\n                </Label>\n                <Label text=\"password:\" GridPane.rowIndex=\"3\">\n                    <GridPane.margin>\n                        <Insets left=\"2.0\" right=\"2.0\" />\n                    </GridPane.margin>\n                </Label>\n                <HBox alignment=\"CENTER_RIGHT\" prefHeight=\"100.0\" prefWidth=\"200.0\" GridPane.columnIndex=\"1\" GridPane.rowIndex=\"4\">\n                    <children>\n                        <Button fx:id=\"btnRemove\" mnemonicParsing=\"false\" text=\"remove\">\n                            <HBox.margin>\n                                <Insets right=\"6.0\" />\n                            </HBox.margin>\n                        </Button>\n                        <Button fx:id=\"btnOk\" mnemonicParsing=\"false\" text=\"ok\">\n                            <HBox.margin>\n                                <Insets right=\"6.0\" />\n                            </HBox.margin>\n                        </Button>\n                    </children>\n                </HBox>\n            <ProgressIndicator fx:id=\"prgload\" maxHeight=\"20.0\" maxWidth=\"20.0\">\n               <GridPane.margin>\n                  <Insets right=\"6.0\" />\n               </GridPane.margin></ProgressIndicator>\n            </children>\n            <VBox.margin>\n                <Insets bottom=\"10.0\" left=\"10.0\" right=\"10.0\" top=\"10.0\" />\n            </VBox.margin>\n        </GridPane>\n    </children>\n</VBox>\n")
        val loader = FXMLLoader()
        loader.location = resource
        val content = loader.load<VBox>()
        val stage = Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        val scene = Scene(stage.pane())
        scene.root = content
        stage.scene = scene
        stage.show()

    }

    @FXML
    fun openDownloadDialog() {
        val resource = javaClass.getResource("/layout/layout_download.fxml")
        val loader = FXMLLoader()
        loader.location = resource
//        loader.load<Any>()
        val content = loader.load<VBox>()
        val stage = Stage()
        stage.initModality(Modality.APPLICATION_MODAL)
        val scene = Scene(stage.pane())
        scene.root = content
        stage.scene = scene
        stage.show()
    }
}

