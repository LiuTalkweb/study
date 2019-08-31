package springDemo.com.controller;

import java.util.ServiceLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextListener;

import springDemo.com.service.AaaService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	RequestContextListener rcl;
	ServletListenerRegistrationBean<RequestContextListener> aa;
	ServiceLoader<AaaService> loaders ;

	@Autowired
	@Qualifier("aaaServiceImpl")
	private AaaService aaaService;
	
	@RequestMapping("/test1")
	public String test1() {	
		System.out.println(aaaService.toString());
		return "hello test1";
	}
}
