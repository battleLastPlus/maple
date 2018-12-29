package com.maple.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @类描述：数据源配置
 * @创建人：maple
 */
@Configuration
//指明了扫描dao层，并且给dao层注入指定的SqlSessionTemplate 
@MapperScan(basePackages = "com.maple.**.dao", sqlSessionTemplateRef  = "mapleSqlSessionTemplate")
public class DataSourceConfig {
	/**
	 * 创建datasource对象
	 * @return
	 */
	@Bean(name = "mapleDataSource")
	@ConfigurationProperties(prefix = "spring.datasource")// prefix值必须是application.properteis中对应属性的前缀
	@Primary
	public DataSource mapleDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * 创建sql工程
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "mapleSqlSessionFactory")
	@Primary
	public SqlSessionFactory mapleSqlSessionFactory(@Qualifier("mapleDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		//对应mybatis.type-aliases-package配置
		bean.setTypeAliasesPackage("com.maple.pojo.*");
		//对应mybatis.mapper-locations配置  
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/maple/mapper/**/*Mapper.xml"));
		//开启驼峰映射
		bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return bean.getObject();
	}

	/**
	 * 配置事务管理
	 * @param dataSource
	 * @return
	 */
	@Bean(name = "mapleTransactionManager")
	@Primary
	public DataSourceTransactionManager mapleTransactionManager(@Qualifier("mapleDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * sqlSession模版，用于配置自动扫描pojo实体类
	 * @param sqlSessionFactory
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "mapleSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate mapleSqlSessionTemplate(@Qualifier("mapleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
