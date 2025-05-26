package kr.co.noerror.Model;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/wh_images/**")
                .addResourceLocations("file:///C:/no_error_erp/ERP_Project/kr.co.noerror/src/main/webapp/noerror_erp_upload/");
    }
}