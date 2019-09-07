package springDemo.com.mq;

import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

@Component
public class Producer1 {

	public void testBasicPublish() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("47.105.154.150");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String EXCHANGE_NAME = "exchange.direct";
        String QUEUE_NAME = "queue_name";
        String ROUTING_KEY = "key";
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT,true);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);//NoWait
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        channel.confirmSelect();
        
        String message = "Hello RabbitMQ:";
        for (int i = 0; i < 10; i++) {
        	//消息持久化
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_BASIC, (message + i).getBytes("UTF-8"));
            /*//单个confirm
            if(!channel.waitForConfirms()){
            	System.out.println("send message failed.");
            }*/
        }
        /*//批量confirm
        long start = System.currentTimeMillis();  
        channel.waitForConfirms();
        System.out.println("执行waitForConfirmsOrDie耗费时间: "+(System.currentTimeMillis()-start)+"ms"); */
        
        //通过监听器confirm
        long start = System.currentTimeMillis();  
        channel.addConfirmListener(new ConfirmListener() {
              
            @Override  
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("nack: deliveryTag = "+deliveryTag+" multiple: "+multiple);  
            }  
              
            @Override  
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack: deliveryTag = "+deliveryTag+" multiple: "+multiple);  
            }  
        });
        
        System.out.println("执行waitForConfirmsOrDie耗费时间: "+(System.currentTimeMillis()-start)+"ms");  
        
        channel.close();
        connection.close();
    }
	
	public static void main(String[] args) {
		try {
			new Producer1().testBasicPublish();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
