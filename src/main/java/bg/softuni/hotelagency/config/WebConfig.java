package bg.softuni.hotelagency.config;

import bg.softuni.hotelagency.interceptor.AvatarInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public AvatarInterceptor avatarInterceptor(){
        return new AvatarInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(avatarInterceptor());
    }
}
