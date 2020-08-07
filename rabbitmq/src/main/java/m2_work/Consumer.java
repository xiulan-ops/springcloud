package m2_work;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) throws Exception {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.129");
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();
        //定义队列
        c.queueDeclare("task_queue",true,false,false,null);
        System.out.println("等待接收数据");

        DeliverCallback deliverCallback = new DeliverCallback() {

            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {

                String msg = new String(message.getBody());
                System.out.println("收到:" + msg);
                for (int i = 0; i < msg.length(); i++) {
                    if (msg.charAt(i)=='.') {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
            c.basicAck(message.getEnvelope().getDeliveryTag(),false);
            System.out.println("处理结束");
        }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
            }
        };

        c.basicQos(1);
        //从helloworld 队列接收消息,消费消息
        c.basicConsume("task_queue",false,deliverCallback,cancelCallback);
        System.out.println("开始消费数据");
    }
}
