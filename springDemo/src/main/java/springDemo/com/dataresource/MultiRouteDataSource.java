package springDemo.com.dataresource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiRouteDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDataSource();
	}
}
