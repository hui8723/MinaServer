import org.apache.log4j.BasicConfigurator

/**
 * Created by tangminghui on 2017/8/18.
 */
fun main(args: Array<String>) {
    BasicConfigurator.configure()
    val server = MinaServer()
    val start = server.start()
    println(start)
}