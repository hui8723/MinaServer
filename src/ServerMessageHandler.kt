import org.apache.mina.core.service.IoHandler
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import org.slf4j.LoggerFactory
import java.text.SimpleDateFormat
import java.util.*
import org.apache.mina.core.future.CloseFuture


/**
 * Created by tangminghui on 2017/8/17.
 * 服务端处理消息
 */
class ServerMessageHandler: IoHandler {

    companion object{
        val log = LoggerFactory.getLogger(ServerMessageHandler.javaClass)
    }

    override fun messageReceived(p0: IoSession, p1: Any) {
        log.info("服务器接收到数据 $p1")
        val content = p1.toString()
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val datetime = sdf.format(Date())
        log.info("转发messageReceived：$datetime  $content")
        val sessions = p0.service.managedSessions.values
        for (session in sessions) {
            session.write("$datetime $content")
        }
    }

    override fun sessionOpened(p0: IoSession) {
        log.info("打开一个session：${p0.id} ${p0.bothIdleCount}")
    }

    override fun sessionClosed(p0: IoSession) {
        log.info("关闭当前session： ${p0.id} ${p0.remoteAddress}")
        val closeFuture = p0.closeNow()
        closeFuture.addListener {
            if (p0 is CloseFuture) {
                (p0 as CloseFuture).setClosed()
                log.info("sessionClosed CloseFuture setClosed-->{},", p0.getSession().getId())
            }
        }
    }

    override fun messageSent(p0: IoSession?, p1: Any) {
        log.info("服务器发送消息： $p1")
    }

    override fun inputClosed(p0: IoSession?) {

    }

    override fun sessionCreated(p0: IoSession) {
        log.info("创建一个新连接： ${p0.remoteAddress}")
        p0.write("welcome to the chat room !")
    }

    override fun sessionIdle(p0: IoSession, p1: IdleStatus?) {
        log.info("当前连接${p0.remoteAddress}处于空闲状况")
    }

    override fun exceptionCaught(p0: IoSession?, p1: Throwable) {
        log.info("服务端发生异常: ${p1.message}" )
    }

}