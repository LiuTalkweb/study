package springDemo.com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springDemo.com.dao.AaaDao;
import springDemo.com.dao.AreaDao;
import springDemo.com.dataresource.DataSourceContext;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private AaaDao AaaDao;
	@Autowired
	private AreaDao AreaDao;
	
	@RequestMapping("/test1")
	public String test1() {
		DataSourceContext.setDataSource("master");
		List<Map<String, Object>> list = AaaDao.dataList();
        System.out.println(list+"=====================");
		return "hello test1";
	}
	
	@RequestMapping("/testa")
	public String testa() {
		DataSourceContext.setDataSource("slave");
		List<Map<String, Object>> list = AreaDao.areaList();
        System.out.println(list+"=====================");
		return "hello testa";
	}
}
