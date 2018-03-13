import org.apache.log4j.Logger
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolEncoder
import org.apache.mina.filter.codec.ProtocolEncoderOutput
import org.apache.mina.filter.codec.textline.LineDelimiter
import java.nio.charset.Charset

/**
 * Created by tangminghui on 2017/8/15.
 */
class CharsetEncoder : ProtocolEncoder {

    companion object {
        val log = Logger.getLogger(CharsetEncoder.javaClass)
        val charset = Charset.forName("UTF-8")
    }

    override fun encode(session: IoSession?, message: Any?, out: ProtocolEncoderOutput) {
        log.info("#####字符编码#####")
        val buff = IoBuffer.allocate(100).setAutoExpand(true)
        buff.putString(message.toString(), charset.newEncoder())
        buff.putString(LineDelimiter.DEFAULT.value, charset.newEncoder())
        buff.flip()
        out.write(buff)
    }

    override fun dispose(session: IoSession?) = log.info("#####dispose#####")



}
