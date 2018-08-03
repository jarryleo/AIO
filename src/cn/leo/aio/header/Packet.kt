package cn.leo.aio.header

import java.nio.ByteBuffer

class Packet() {
    constructor(packet: ByteBuffer) : this() {
        packet.flip()
        val array = packet.array()
        magic = array.copyOf(3)
        ver = array[3]
        cmd = PacketFactory.getInt(array.copyOfRange(4, 6)).toShort()
        len = PacketFactory.getInt(array.copyOfRange(6, 10))
        data = array.copyOfRange(16, array.size)
    }

    var magic = byteArrayOf(0x02, 0x05, 0x06)
    var ver: Byte = 1
    var cmd: Short = 0
    var len = 0
    var data: ByteArray? = null

}