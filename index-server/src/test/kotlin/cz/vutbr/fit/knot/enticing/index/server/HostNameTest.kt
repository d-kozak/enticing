package cz.vutbr.fit.knot.enticing.index.server

import org.junit.jupiter.api.Test
import java.net.InetAddress

class HostNameTest {
    @Test
    fun getHostNameTest() {
        println(InetAddress.getLocalHost().hostName)
    }
}