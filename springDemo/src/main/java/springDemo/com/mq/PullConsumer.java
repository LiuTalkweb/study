package springDemo.com.mq;
import org.springframework.amqp.core.ExchangeTypes;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class PullConsumer {

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

        GetResponse response = channel.basicGet(QUEUE_NAME, false);
        System.out.println(new String(response.getBody()));
        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);
    }
	
	public static void main(String[] args) {
		try {
			new PullConsumer().testBasicConsumer1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
