package net.kuper.tz.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import net.kuper.tz.core.datasource.DataSourceNames;
import net.kuper.tz.core.datasource.DynamicDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DynamicDataSourceConfig {

    /**
     * 创建 DataSource Bean
     * */

    @Bean
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource oneDataSource(){
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @QuartzDataSource
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource twoDataSource(){
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    /**
     * 如果还有数据源,在这继续添加 DataSource Bean
     * */

    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource oneDataSource, DataSource twoDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceNames.ONE, oneDataSource);
        targetDataSources.put(DataSourceNames.TWO, twoDataSource);
        // 还有数据源,在targetDataSources中继续添加
        return new DynamicDataSource(oneDataSource, targetDataSources);
    }
}
