package m3_publishsubscribe;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.129");
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();
        //定义交换机
       c.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
       //方法一:自己随机命名对列
//       String queue= UUID.randomUUID().toString();
//       c.queueDeclare(queue,false,true,true,null);
        //方法二:让Rabbitmq服务器随机命名,非持久,独占,自动删除
        String queue = c.queueDeclare().getQueue();
        c.queueBind(queue,"logs","");
        DeliverCallback deliverCallback = new DeliverCallback(){
            @Override
            public void handle(String consumeTag, Delivery message) throws IOException {
                String msg = new String(message.getBody());
                System.out.println("收到:" +msg);
            }
        };
        CancelCallback cancelCallback = new CancelCallback(){
            @Override
            public void handle(String consumeTag) throws IOException {
            }
        };
        c.basicConsume(queue,true,deliverCallback,cancelCallback);
    }
}
