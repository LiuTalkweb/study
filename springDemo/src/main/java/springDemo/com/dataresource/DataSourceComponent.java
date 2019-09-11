package springDemo.com.dataresource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceComponent {

	@Resource
    private MasterDataSourceConfig masterDataSourceConfig;

    @Resource
    private SlaveDataSourceConfig slaveDataSourceConfig;


     @Bean(name = "master")
    public DataSource masterDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(masterDataSourceConfig.getUrl());
        dataSource.setUsername(masterDataSourceConfig.getUsername());
        dataSource.setPassword(masterDataSourceConfig.getPassword());
        dataSource.setDriverClassName(masterDataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Bean(name = "slave")
    public DataSource slaveDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(slaveDataSourceConfig.getUrl());
        dataSource.setUsername(slaveDataSourceConfig.getUsername());
        dataSource.setPassword(slaveDataSourceConfig.getPassword());
        dataSource.setDriverClassName(slaveDataSourceConfig.getDriverClassName());
        return dataSource;
    }

    @Primary//不加这个会报错。
    @Bean(name = "multiDataSource")
    public MultiRouteDataSource exampleRouteDataSource() {
        MultiRouteDataSource multiDataSource = new MultiRouteDataSource();
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put("master", masterDataSource());
        targetDataSources.put("slave", slaveDataSource());
        multiDataSource.setTargetDataSources(targetDataSources);
        multiDataSource.setDefaultTargetDataSource(masterDataSource());
        return multiDataSource;
    }
}
