package cn.xiaoguo.config;

import cn.xiaoguo.filter.JwtAuthenticationTokenFilter;
import cn.xiaoguo.handler.security.AccessDeniedHandlerImpl;
import cn.xiaoguo.handler.security.AuthenticationEntryPointImpl;
import cn.xiaoguo.service.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName SecurityConfig
 * @Description TODO
 * @Author 小果
 * @Date 2024/6/7 19:16
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // 这个类主要是获取库中的用户信息，交给security
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    AuthenticationEntryPointImpl authenticationEntryPoint;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                //不通过Session获取SecurityContext
                // 处理请求
                .authorizeHttpRequests(authorize -> {
                    // 放开登录注册接口
                    authorize.requestMatchers("/user/login","/user/register").anonymous();
                   //放开分类查询接口
                    authorize.requestMatchers("/videoType/**").anonymous();
                    // 其他的都需要认证
                    authorize.anyRequest().authenticated();
                })
                //处理访问被拒绝的情况
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
                )
                //处理未经认证的访问
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                );
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.addFilter(corsFilter());
        return http.build();
    }

    //security跨域
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);
        config.addExposedHeader("token"); //暴露"token"头,使前端可以访问到
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // 身份认证提供程序（加密方式，用户查询）
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 这个主要是为了其他地方可以使用认证管理器
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    // 加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}