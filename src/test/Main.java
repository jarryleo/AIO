package test;

import cn.leo.aio.service.AioServiceCore;
import cn.leo.aio.service.Client;
import cn.leo.aio.service.ServiceListener;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(String[] args) {
        AioServiceCore service = new AioServiceCore();
        service.start(9000, new ServiceListener() {
            @Override
            public void onNewConnectComing(@NotNull Client client) {

            }

            @Override
            public void onConnectInterrupt(@NotNull Client client) {

            }

            @Override
            public void onDataArrived(@NotNull Client client, @NotNull byte[] data, short cmd) {

            }
        });
    }
}
