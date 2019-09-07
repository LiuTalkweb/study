package springDemo.com.mq;
import java.io.IOException;

import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Consumer2 {

	public void testBasicConsumer1() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setVirtualHost("/");
        factory.setHost("47.105.154.150");
        factory.setPort(AMQP.PROTOCOL.PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        String EXCHANGE_NAME = "exchange.direct";
        String QUEUE_NAME = "queue_name";
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeTypes.DIRECT,true);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "key");
//        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(message);
//                channel.basicAck(envelope.getDeliveryTag(), false);
                if (message.contains(":3")){
	                // requeue：重新入队列，false：直接丢弃，相当于告诉队列可以直接删除掉
//                    channel.basicReject(envelope.getDeliveryTag(), true);
                	channel.basicRecover(false);
                } else {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
	
	public static void main(String[] args) {
		try {
			new Consumer2().testBasicConsumer1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
