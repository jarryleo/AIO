package test;

import cn.leo.aio.service.AioService;

public class Main {
    public static void main(String[] args) {
        AioService service = new AioService();
        service.start(9000);
    }
}
