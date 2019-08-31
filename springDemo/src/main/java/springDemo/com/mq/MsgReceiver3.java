package springDemo.com.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.QUEUE_B)
public class MsgReceiver3 {

	@RabbitHandler
    public void process(String content) {
        System.out.println(this.getClass()+"接收处理队列A当中的消息： " + content);
    }
}
