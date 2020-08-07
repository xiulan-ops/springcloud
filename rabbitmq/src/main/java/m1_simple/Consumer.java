package m1_simple;

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
      c.queueDeclare("helloworld",false,false,false,null);

        DeliverCallback deliverCallback = new DeliverCallback() {

            @Override
            public void handle(String consumerTag, Delivery  message) throws IOException {
                byte[] body = message.getBody();
                String msg = new String(body);
                System.out.println("收到:"+msg);

            }
        };
        CancelCallback cancelCallback = new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
            }
        };
        //从helloworld 队列接收消息,消费消息
        c.basicConsume("helloworld",false,deliverCallback,cancelCallback);
    }
}
