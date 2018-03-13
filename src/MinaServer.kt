import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFactory
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.nio.NioSocketAcceptor
import java.io.IOException
import java.net.InetSocketAddress

/**
 * Created by tangminghui on 2017/8/18.
 * 服务器启动类
 */
class MinaServer {

//    创建非阻塞的server端的Socket连接
    private val acceptor by lazy {
        NioSocketAcceptor()
    }

    fun start() : Boolean {
        val filterChain = acceptor.filterChain
//        添加编码过滤器 处理乱码、编码问题
        filterChain.addLast("codec",ProtocolCodecFilter(CharsetCodecFactory()))
//        设置核心消息业务处理器
        acceptor.handler = ServerMessageHandler()
//        设置session配置，30秒内无操作进入空闲状况
        acceptor.sessionConfig.setIdleTime(IdleStatus.BOTH_IDLE,30)

//        绑定端口3456
        try {
            acceptor.bind(InetSocketAddress(3456))
        }catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }


}