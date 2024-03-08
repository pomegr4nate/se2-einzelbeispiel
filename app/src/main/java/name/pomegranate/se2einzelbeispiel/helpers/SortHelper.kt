package name.pomegranate.se2einzelbeispiel.helpers

import java.util.Comparator

class SortHelper {
    companion object {
        // 12203558 mod 7 = 3
        fun sortMatrikelnummer(matrikelnummer: String) : String {

            // The string can be converted to a byte array, as this makes the comparison easier
            var bytes = matrikelnummer.toByteArray()

            bytes = bytes.sortedWith(matrikelnummerComparator).toByteArray()

            // Turn ByteArray back into String
            return bytes.decodeToString()
        }

        /**
         * Compares by:
         * * even numbers before odd numbers
         * * even / odd numbers ascending
         */
        private val matrikelnummerComparator = object : Comparator<Byte> {
            override fun compare(o1: Byte, o2: Byte): Int {
                if (o1 == o2)
                    return 0 // Equal

                if (o1 % 2 == 0) {
                    if (o2 % 2 != 0) {
                        // o1 is even, o2 is odd
                        return -1
                    }
                } else {
                    if(o2 % 2 == 0) {
                        // o1 is odd, o2 is even
                        return 1
                    }
                }

                // Both are odd / even
                return o1 - o2
            }
        }
    }
}