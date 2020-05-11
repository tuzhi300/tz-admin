package net.kuper.tz;

//import net.kuper.tz.admin.config.DynamicDataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Import({DynamicDataSourceConfig.class})
@MapperScan({"net.kuper.tz.*.dao"})
@SpringBootApplication(scanBasePackages = {"net.kuper.tz"}, exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class TzAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(TzAdminApplication.class, args);
    }

}
