package name.pomegranate.se2einzelbeispiel.helpers

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket

class NetHelper {
    companion object {
        private const val DOMAIN = "se2-submission.aau.at"
        private const val PORT = 20080

        fun getFromServer(matrikelnummer: String): String {

            val socket = Socket(DOMAIN, PORT)

            val output = DataOutputStream(socket.getOutputStream())
            val input = BufferedReader(InputStreamReader(socket.getInputStream()))

            // \n is needed to get an answer from the server
            output.writeBytes(matrikelnummer + '\n')

            val result = input.readLine()

            // Close readers and socket
            input.close()
            output.close()
            socket.close()

            return result
        }
    }
}
