package cn.leo.aio.header

import java.nio.ByteBuffer

object PacketFactory {


    fun encodePacketBuffer(data: ByteArray, cmd: Short = 0): ByteBuffer {
        val magic = byteArrayOf(0x02, 0x05, 0x06)
        val ver: Byte = 1
        val len = data.size
        val reverse = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
        val packet = magic + ver + getByteArray(cmd) + getByteArray(len) + reverse + data
        return ByteBuffer.wrap(packet)
    }

    fun decodePacketBuffer(packet: ByteBuffer): Packet {
        return Packet(packet)
    }

    fun getInt(byteArray: ByteArray): Int {
        if (byteArray.size == 4) {
            return byteArray[0].toInt().and(0xFF).shl(24)
                    .or(byteArray[1].toInt().and(0xFF).shl(16))
                    .or(byteArray[2].toInt().and(0xFF).shl(8))
                    .or(byteArray[3].toInt().and(0xFF))
        } else if (byteArray.size == 2) {
            return byteArray[0].toInt().and(0xFF).shl(8)
                    .or(byteArray[1].toInt().and(0xFF))
        }
        return 0
    }

    private fun getByteArray(num: Int): ByteArray {
        val b1: Byte = (num.ushr(24) and 0xFF).toByte()
        val b2: Byte = (num.ushr(16) and 0xFF).toByte()
        val b3: Byte = (num.ushr(8) and 0xFF).toByte()
        val b4: Byte = (num and 0xFF).toByte()
        return byteArrayOf(b1, b2, b3, b4)
    }

    private fun getByteArray(num: Short): ByteArray {
        val toInt = num.toInt()
        val b1: Byte = (toInt.ushr(8) and 0xFF).toByte()
        val b2: Byte = (toInt and 0xFF).toByte()
        return byteArrayOf(b1, b2)
    }
}