package springDemo.com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

@Service("BbbServiceImpl")
public class BbbServiceImpl implements AaaService {

	@Override
	public List<Map<String, Object>> dataList() {
		System.out.println("bbb");
		return null;
	}

@PostConstruct 
 
public void init() { 
 
} 
 
@PreDestroy 
 
public void destory() { 
 
}
}
