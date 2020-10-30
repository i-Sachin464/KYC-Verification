package com.client.kyc_verify.util

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * This class is used for network call and send data using service
 */
class NetworkCall {
    private var responseCode = 0

    /**
     * @param url : url of server
     *
     * This function open connection to server URL and post data
     */
    fun makeServiceCall(url: String?): String? {
        var connection: HttpURLConnection? = null
        var response: String? = null
        var inputStream: InputStream? = null
        try {
            val imgUrl = URL(url)
            connection = imgUrl.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connect()
            responseCode = connection.responseCode
            inputStream = connection.inputStream
            response = readIt(inputStream)
            Log.d("response", response)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) {
                    connection!!.disconnect()
                    inputStream.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return response
    }

    @Throws(Exception::class)
    private fun readIt(iStream: InputStream?): String {
        var singleLine: String? = ""
        val totalLines = StringBuilder(iStream!!.available())
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(iStream))
            while (reader.readLine().also { singleLine = it } != null) {
                totalLines.append(singleLine)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return totalLines.toString()
    }
}