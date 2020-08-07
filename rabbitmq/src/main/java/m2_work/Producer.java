package m2_work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Producer {
    public static void main(String[] args) throws Exception {
        //连接 Rabbitmq 服务器
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.126.129");
        f.setPort(5672); // 5672是通信端口
        f.setUsername("admin");
        f.setPassword("admin");
        //如果使用我的服务器，每个同学使用自己的虚拟主机，避免互相影响
        // f.setVirtualHost("/wht");
        Connection con = f.newConnection();
        Channel c = con.createChannel();
        //定义队列，会通知服务器想使用一个 "helloworld" 队列，
        //服务器会找到这个队列，如果不存在，会服务器会新建队列
        c.queueDeclare("task_queue", true, false, false, null);
        while(true){
            System.out.print("输入消息:");
           String msg = new Scanner(System.in).nextLine();
           if("exit".equals(msg)){
               break;
           }
            c.basicPublish("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("消息已发送: "+msg);
        }
        c.close();
    }
}
