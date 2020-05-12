package net.kuper.tz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
//@Profile({"dev", "test"})
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public Docket genApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("敏捷开发")
                .apiInfo(defaultApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.kuper.tz.dev"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getTokenPar());
    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用模块")
                .apiInfo(defaultApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.kuper.tz.common"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getTokenPar());
    }

    @Bean
    public Docket systemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("系统模块")
                .apiInfo(defaultApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.kuper.tz.system"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getTokenPar());
    }

    @Bean
    public Docket monitorApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("监控模块")
                .apiInfo(defaultApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.kuper.tz.monitor"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getTokenPar());
    }

    @Bean
    public Docket scheduleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("批处理模块")
                .apiInfo(defaultApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.kuper.tz.schedule"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getTokenPar());
    }
    @Bean
    public Docket storageApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("文件存储模块")
                .apiInfo(defaultApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.kuper.tz.storage"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(getTokenPar());
    }


    private ApiInfo defaultApiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("文档内容仅供参考")
                .contact(new Contact("kuper", "", "shengongwen@163.com"))
//                .termsOfServiceUrl("/")
                .version("1.2")
                .build();
    }

    /**
     * 全局参数配置
     **/
    public List getTokenPar() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token")
                .description("认证信息")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .defaultValue("R")
                .required(true);
        pars.add(tokenPar.build());
//        tokenPar.name("s")
//                .description("签名信息")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .defaultValue("xxxxxxxxxxxxxxxxxxxxxxxxxxx")
//                .required(false);
//        pars.add(tokenPar.build());
//        tokenPar.name("p")
//                .description("发布平台")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .defaultValue("ANDROID")
//                .required(false);
//        pars.add(tokenPar.build());
//        tokenPar.name("v")
//                .description("客户端版本")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .defaultValue("V1.0.1")
//                .required(false);
//        pars.add(tokenPar.build());

        return pars;
    }
}
