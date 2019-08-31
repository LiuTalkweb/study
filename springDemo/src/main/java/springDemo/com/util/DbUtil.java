package springDemo.com.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import springDemo.com.dao.AaaDao;

public class DbUtil {

	DriverManager dm;
	DataSource ds;
	DataSourceAutoConfiguration dsac;
	SqlSessionFactoryBean ssfb;
	SqlSessionFactoryBuilder sfb;
	SqlSessionFactory ssf;
	SqlSession ss;
	Connection ct;
	Configuration cfg;
	TransactionFactory tf;
	Executor e;
	ParameterHandler ph;
	MapperFactoryBean<?> mfb;
	MapperScannerConfigurer msc;
	MapperRegistry mr;
	
	public void aa() throws Exception {
//		Configuration configuration ;
		String file = "D:\\myspace\\springDemo\\src\\main\\resources\\mybatis-config.xml";
		Reader reader = new InputStreamReader(new FileInputStream(file));
		sfb = new SqlSessionFactoryBuilder();
		ssf = sfb.build(reader);
		ss = ssf.openSession();
		AaaDao dao = ss.getMapper(AaaDao.class);
		String statement = "springDemo.com.dao.AaaDao.dataList";
		List<Object> list = ss.selectList(statement);
		for(Object obj : list) {
			System.out.println(obj);
		}
		List<Map<String, Object>> list2 = dao.dataList();
		System.out.println(list2.size());
	}
}
