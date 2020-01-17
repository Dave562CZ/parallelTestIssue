package parallelTestIssue.submodule

import java.io.File
import kotlin.test.Test

class Test94Test {

    @Test
    fun test1() {
        val id = System.getProperty("org.gradle.test.worker")
        val file = File("build/ids/$id")
        file.createNewFile()
    }
}