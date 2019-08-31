package springDemo.com.mq;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;


@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver {

	@RabbitHandler
    public void process(String message, Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		 channel.basicAck(tag,false);
		System.out.println(this.getClass()+"接收处理队列A当中的消息： " + message);
    }
}
