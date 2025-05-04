package org.example.backendenicargy.Configs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:4200")
                            .allowedMethods("*")
                            .allowedHeaders("*")
                            .allowCredentials(true); 
                }
                @Override
                public void addResourceHandlers(ResourceHandlerRegistry registry) {
                    // If your uploads folder is directly under the backend project root:
                    registry.addResourceHandler("/uploads/**")
                            .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");

                    // Optional: log to verify loading
                    System.out.println("Serving static files from: uploads/");
                }
            };
        }



}
