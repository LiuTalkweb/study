package springDemo.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springDemo.com.mq.MsgProducer;
import springDemo.com.mq.RabbitConfig;
import springDemo.com.service.AaaService;

@RestController
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	@Qualifier("aaaServiceImpl")
	private AaaService aaaService;
	
	@Autowired
	private MsgProducer msgProducer;
	
	@GetMapping("/aa")
	List<Map<String, Object>> test() {
		System.out.println(aaaService.toString()+" class: "+ aaaService.getClass());
		List<Map<String, Object>> list = aaaService.dataList();
		return list;
	}
	
	@RequestMapping("/test3")
	public String hello2() {	
		return "hello boot3";
	}
	
	@RequestMapping("/test2")
	public String hello3() {	
		return "hello boot2";
	}
	
	/**
	 * 点对点
	 * @return
	 */
	@RequestMapping("/sendMsg")
	public String sendMsg() {
		for(int i=0;i<20;i++) {
			msgProducer.sendMsg(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A,"hello world 队列A");
		}
		msgProducer.sendMsg(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_B,"hello world 队列B");
		return "sendMsg";
	}
	
	/**
	 * 广播
	 * @return
	 */
	@RequestMapping("/sendFanoutMsg")
	public String sendFanoutMsg() {
		msgProducer.sendMsg(RabbitConfig.FANOUT_EXCHANGE,RabbitConfig.ROUTINGKEY_B,"下面广播一个通知");
		return "sendFanoutMsg";
	}
	
	/**
	 * 发送主题类消息
	 * @param topic
	 * @return
	 */
	@RequestMapping("/sendTopicMsg")
	public String sendTopicMsg(String topic) {
		msgProducer.sendMsg(RabbitConfig.TOPIC_EXCHANGE,topic,"发送主题类消息");
		return "sendFanoutMsg";
	}
}
