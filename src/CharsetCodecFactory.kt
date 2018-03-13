import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFactory
import org.apache.mina.filter.codec.ProtocolDecoder
import org.apache.mina.filter.codec.ProtocolEncoder

/**
 * Created by tangminghui on 2017/8/15.
 * 编码工厂与编码过滤工厂
 */

class CharsetCodecFactory : ProtocolCodecFactory {

    @Throws(Exception::class)
    override fun getEncoder(ioSession: IoSession): ProtocolEncoder? = CharsetEncoder()

    @Throws(Exception::class)
    override fun getDecoder(ioSession: IoSession): ProtocolDecoder? = CharsetDecoder()
}
