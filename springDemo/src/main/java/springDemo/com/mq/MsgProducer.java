package springDemo.com.mq;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{

	private RabbitTemplate rabbitTemplate;
	
	@Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setReturnCallback(this);
    }

	/**
	 * 根据路由规则将消息发送到指定交换机绑定的队列中
	 * @param exchange
	 * @param route
	 * @param content
	 */
	public void sendMsg(String exchange,String route,String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(exchange, route, content, correlationId);
    }
	
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println(" 回调id:" + correlationData);
        if (ack) {
        	System.out.println("消息成功消费");
        } else {
        	System.out.println("消息消费失败:" + cause);
        }
	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println("消息主体 message : "+message);
        System.out.println("消息主体 message : "+replyCode);
        System.out.println("描述："+replyText);
        System.out.println("消息使用的交换器 exchange : "+exchange);
        System.out.println("消息使用的路由键 routing : "+routingKey);
	}
}
