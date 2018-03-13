import org.apache.log4j.Logger
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolDecoder
import org.apache.mina.filter.codec.ProtocolDecoderOutput
import java.nio.charset.Charset

/**
 * Created by tangminghui on 2017/8/15.
 */
class CharsetDecoder : ProtocolDecoder {

    companion object {
        val log = Logger.getLogger(CharsetDecoder.javaClass)
        val charset = Charset.forName("UTF-8")
    }

    var buff = IoBuffer.allocate(100).setAutoExpand(true)

    override fun finishDecode(p0: IoSession?, p1: ProtocolDecoderOutput?) {
        log.info("#####完成解码####")
    }

    override fun decode(session: IoSession?, input: IoBuffer, out: ProtocolDecoderOutput?) {
        log.info("#####decode#####")
        while (input.hasRemaining()) {
            val b = input.get()
            if (b.toChar() == '\n') {
                buff.flip()
                val bytes = ByteArray(buff.limit())
                buff.get(bytes)
                val message = String(bytes, charset)
                buff = IoBuffer.allocate(100).setAutoExpand(true)
                if (out != null) {
                    out.write(message)
                }
            }else {
                buff.put(b)
            }
        }
    }

    override fun dispose(p0: IoSession?) {
        if (p0 != null) {
            log.info(p0.currentWriteMessage)
        }

    }


}
