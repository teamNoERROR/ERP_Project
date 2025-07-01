package kr.co.noerror.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultiDatabaseConfig {

	private Properties loadProperties(String prefix) throws Exception {
		Properties pp = new Properties();
		pp.load(this.getClass().getClassLoader().getResourceAsStream("database.properties"));
		
		Properties newProps = new Properties();
		newProps.setProperty("driver", pp.getProperty(prefix + ".driver-class-name"));
		newProps.setProperty("url", pp.getProperty(prefix + ".url"));
		newProps.setProperty("username", pp.getProperty(prefix + ".username"));
		newProps.setProperty("password", pp.getProperty(prefix + ".password"));
		newProps.setProperty("mappers", pp.getProperty("mybatis.mapper-locations")); // 공통으로 사용
		return newProps;
	}

	private DataSource buildDataSource(Properties p) {
		return DataSourceBuilder.create()
			.driverClassName(p.getProperty("driver"))
			.url(p.getProperty("url"))
			.username(p.getProperty("username"))
			.password(p.getProperty("password"))
			.build();
	}

	private SqlSessionFactory buildSqlFactory(DataSource ds, String mappers, ApplicationContext ac) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(ds);
		factoryBean.setMapperLocations(ac.getResources(mappers));
		return factoryBean.getObject();
	}

	private SqlSessionTemplate buildSqlTemplate(SqlSessionFactory sf) {
		return new SqlSessionTemplate(sf);
	}

	// Oracle (first)
	@Bean(name = "oracle")
	@Primary
	public DataSource ds1() throws Exception {
		return buildDataSource(loadProperties("spring.first.datasource"));
	}

	@Bean(name = "sqlfactory_oracle")
	@Primary
	public SqlSessionFactory factory1(@Qualifier("oracle") DataSource ds, ApplicationContext ac) throws Exception {
		Properties p = loadProperties("spring.first.datasource");
		return buildSqlFactory(ds, p.getProperty("mappers"), ac);
	}

	@Bean(name = "sqltemplate_oracle")
	@Primary
	public SqlSessionTemplate template1(@Qualifier("sqlfactory_oracle") SqlSessionFactory sf) {
		return buildSqlTemplate(sf);
	}

	// Mysql (second)
//	@Bean(name = "mysql")
//	public DataSource ds2() throws Exception {
//		return buildDataSource(loadProperties("spring.second.datasource"));
//	}
//
//	@Bean(name = "sqlfactory_mysql")
//	public SqlSessionFactory factory2(@Qualifier("mysql") DataSource ds, ApplicationContext ac) throws Exception {
//		Properties p = loadProperties("spring.second.datasource");
//		return buildSqlFactory(ds, p.getProperty("mappers"), ac);
//	}
//
//	@Bean(name = "sqltemplate_mysql")
//	public SqlSessionTemplate template2(@Qualifier("sqlfactory_mysql") SqlSessionFactory sf) {
//		return buildSqlTemplate(sf);
//	}	

}
