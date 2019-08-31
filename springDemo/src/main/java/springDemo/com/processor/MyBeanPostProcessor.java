package springDemo.com.processor;

import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	AbstractAutoProxyCreator aa;
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equalsIgnoreCase("AaaServiceImpl")) {
			System.out.println("BeforeInitialization "+beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(beanName.equalsIgnoreCase("AaaServiceImpl")) {
			System.out.println("AfterInitialization "+beanName);
		}
		return bean;
	}

}
