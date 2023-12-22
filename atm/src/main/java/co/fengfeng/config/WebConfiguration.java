package co.fengfeng.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    public MyIntercepter myIntercepter;

//    /**
//     * 解决跨域请求问题
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //允许跨域的路由
//        registry.addMapping("/**")
//                // 再次加入前端Origin  localhost！=127.0.0.1
//                .allowedOrigins("http://localhost:8080")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                // 预检间隔时间
//                .maxAge(168000)
//                //设置孕育跨域请求的域名
//                .allowedOriginPatterns("*")
//                // 是否允许证书（cookies）
//                .allowCredentials(true);
//
//    }
//
//    /**
//     * 异步请求配置
//     * @param configurer
//     */
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
//        configurer.setDefaultTimeout(30000);
//    }
//
//    /**
//     * 配置拦截器，拦截路径
//     * 每次请求到拦截的路径，就会去执行拦截器中的方法
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> excludePath = new ArrayList<>();
//        // 排除拦截，除了指定的路径
//        excludePath.add("/login");// 登录
//        excludePath.add("/getCarIdInfo");// 指定的路径
//
//        registry.addInterceptor(myIntercepter)
//                .addPathPatterns("/**")
//                .excludePathPatterns(excludePath);
//    }
}
