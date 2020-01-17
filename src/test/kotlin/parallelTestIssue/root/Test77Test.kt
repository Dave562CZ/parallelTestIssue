package parallelTestIssue.root

import java.io.File
import kotlin.test.Test

class Test77Test {

    @Test
    fun test1() {
        val id = System.getProperty("org.gradle.test.worker")
        val file = File("build/ids/$id")
        file.createNewFile()
    }
}