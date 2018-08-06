package cn.leo.aio.service

import cn.leo.aio.utils.Logger
import java.util.*

object ChannelManager {
    private var channelList = Collections.synchronizedList(ArrayList<Channel>())

    fun add(channel: Channel) {
        channelList.add(channel)
        Logger.d("有客户端接入:${channel.host}[总:${channelList.size}]")
    }

    fun remove(channel: Channel) {
        channelList.remove(channel)
        Logger.d("有客户端断开:${channel.host}[总:${channelList.size}]")
    }

    fun sendMsgToAll(data: ByteArray) {
        channelList.forEach { it.send(data) }
    }

    fun contains(channel: Channel) = channelList.contains(channel)
}