package com.shopme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Display user photos
        exposeDirectory("user-photos", registry);
        //Display category images
        exposeDirectory("../category-images", registry);
        //Display brand logos
        exposeDirectory("../brand-logos", registry);
        //Display product images
        exposeDirectory("../product-images", registry);
        //Display site logos
        exposeDirectory("../site-logo", registry);
    }

    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
        Path path = Paths.get(pathPattern);
        String absolutePath = path.toFile().getAbsolutePath();

        String logicalPath = pathPattern.replace("../", "") + "/**";

        registry.addResourceHandler(logicalPath).
                addResourceLocations("file:/" + absolutePath + "/");
    }
}
