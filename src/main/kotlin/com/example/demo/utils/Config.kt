package com.example.demo.utils

import com.google.gson.Gson
import java.io.File
import java.nio.file.Paths

class Config private constructor() {

    var managerConnect = ManagerConnect()
        get() = field
        private set
//    val managerToken = HashMap<Connect, String>()
    val file = File(Const.PATH_FILE_CONFIG)

    init {
        val dir = File(Const.PATH_DIR_CACHE)
        if (!dir.exists()){
            dir.mkdir()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun save() {
        val gson = Gson().toJson(this)
        file.writeText(gson)
    }

    fun load() {
        val gson = file.readText()
        val config = Gson().fromJson(gson, Config::class.java)
        config?.let {
            managerConnect.copyBy(config.managerConnect)
        }
    }

    companion object {
        val single by lazy {
            Config()
        }
    }
}