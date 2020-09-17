package life.beyond.community.config;

import life.beyond.community.component.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    SessionInterceptor sessionInterceptor(){
        return new SessionInterceptor();
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(sessionInterceptor()).addPathPatterns("/**");
    }
}
