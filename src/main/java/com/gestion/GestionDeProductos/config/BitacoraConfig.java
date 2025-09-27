package com.gestion.GestionDeProductos.config;

import com.gestion.GestionDeProductos.logging.BitacoraInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BitacoraConfig implements WebMvcConfigurer {

    @Autowired
    private BitacoraInterceptor bitacoraInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bitacoraInterceptor)
                .addPathPatterns("/api/**");
    }
}

