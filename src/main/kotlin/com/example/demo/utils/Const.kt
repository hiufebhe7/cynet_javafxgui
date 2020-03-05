package com.example.demo.utils

import java.nio.file.Paths


class Store {

//    var ACCOUNT: Account? = null

}

class Const {

    companion object {

        val VERSION = 1
        val APP_NAME = "cynet"
        val SERVER_URL = ""

        val INSTANCE_NAME = "instance_name"
        val INSTANCE_URL = "instance_url"
        val CLIENT_ID = "client_id"
        val CLIENT_SECRET = "client_secret"
        val REMEMBER = "remember"

        val GRANT_TYPE = "password"

        val OAUTH_REDIRECT_HOST = "urn:ietf:wg:oauth:2.0:oob"

        val SCOPES = "read write follow"
        val SCOPE = "read+write+follow"

        val UNIT_1KB = 1024
        val UNIT_1MB = 1024 * 1024
        val UNIT_CHUNK = UNIT_1MB * 10

        val DIR_ROOT = ""
        val PATH_DIR_CACHE = "./cache"
        val PATH_FILE_CONFIG = Paths.get(PATH_DIR_CACHE,"config.json").toFile().path
        val PATH_DIR_DOWNLOAD = DIR_ROOT

        val FILE_NULL = "/null.gif"
        val PACK_MEDIATYPE = "imge/gif"
        val PACK_NAME = "file"
        val PACK_FILENAME = "part.gif"
    }
}

enum class MastodonTag() {
    Home,
    Notifications,
    Public,
    Federated
}