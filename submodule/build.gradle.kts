import java.util.concurrent.CyclicBarrier


tasks.test {
    doFirst {
        mkdir(buildDir.resolve("ids"))
        val barrier = rootProject.extra["barrier"] as CyclicBarrier
        barrier.await()
    }
    maxParallelForks = 6
}