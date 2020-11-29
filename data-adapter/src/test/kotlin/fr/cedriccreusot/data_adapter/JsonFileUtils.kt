package fr.cedriccreusot.data_adapter

import java.io.File

object JsonFileUtils {
    fun readJsonFile(filename: String): String
    {
        val json = StringBuilder()
        for (line in File(javaClass.classLoader!!.getResource(filename).path).readLines()) {
            json.append(line)
        }
        return json.toString()
    }
}