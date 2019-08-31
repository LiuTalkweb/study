package springDemo.com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springDemo.com.dao.AaaDao;

@Service("aaaServiceImpl")
public class AaaServiceImpl implements AaaService {

	@Autowired
	private AaaDao dao;
	
	@Override
	public List<Map<String, Object>> dataList() {
		return dao.dataList();
	}
	

@PostConstruct 
 
public void init() { 
 
} 
 
@PreDestroy 
 
public void destory() { 
 
}

}
