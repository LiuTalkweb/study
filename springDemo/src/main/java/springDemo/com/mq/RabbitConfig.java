package springDemo.com.mq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {

	@Value("${spring.rabbitmq.host}")
    private String host;
 
    @Value("${spring.rabbitmq.port}")
    private int port;
 
    @Value("${spring.rabbitmq.username}")
    private String username;
 
    @Value("${spring.rabbitmq.password}")
    private String password;
    
    /*directExchange*/
    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";
    /*fanoutExchange*/
    public static final String FANOUT_EXCHANGE = "my-mq-exchange-fanout";
    /*topicExchange*/
    public static final String TOPIC_EXCHANGE = "my-mq-exchange-topic";
 
    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";
 
    /*directRoute*/
    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";
    /*topicRoute*/
    public static final String ROUTINGKEY_D = ".orange.*";
    
    @Bean
    public RabbitAdmin rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    
    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }
    
    @Bean
    public SimpleRabbitListenerContainerFactory connectionFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)/*必须是prototype类型*/
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
    
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_A);
    }
    
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }
    @Bean
    public Queue queueB() {
    	return new Queue(QUEUE_B, true); //队列持久
    }
    
    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }
    
    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_B);
    }
    
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConfig.FANOUT_EXCHANGE);
    }
    
    @Bean
    Binding bindingExchangeA(Queue queueA,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
    
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(RabbitConfig.TOPIC_EXCHANGE);
    }
    
    @Bean
    Binding bindingExchangeTopic(Queue queueA, TopicExchange topicExchange) {
        return BindingBuilder.bind(queueA).to(topicExchange).with(RabbitConfig.ROUTINGKEY_D);
    }
}
