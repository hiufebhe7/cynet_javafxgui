package com.example.demo.utils

class ManagerConnect {

    val connects = ArrayList<Connect>()

    fun add(c: Connect) {
        val fc = connects.find {
            c.instance == it.instance && c.username == it.username && c.password == it.password
        }
        if (fc == null) {
            connects.add(c)
        }
    }

    fun remove(c: Connect) {
        val fc = connects.find {
            c.instance == it.instance && c.username == it.username && c.password == it.password
        }

        fc?.let {
            connects.remove(fc)
        }
    }

    fun clear() {
        connects.clear()
    }

    fun copyBy(manager: ManagerConnect) {
        connects.clear()
        connects.addAll(manager.connects)
    }
}

data class Connect(
        val instance: String,
        val username: String,
        val password: String
) {
    override fun toString(): String {
        return "${instance}  ${username}  ${password}"
    }
}