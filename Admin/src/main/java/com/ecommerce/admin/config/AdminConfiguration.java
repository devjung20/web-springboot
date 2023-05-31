package com.ecommerce.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AdminConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new AdminServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    // xác thực người dùng dựa trên thông tin người dùng được lấy từ một nguồn dữ liệu như cơ sở dữ liệu
    @Bean
    public DaoAuthenticationProvider daoAuthenticationConfigurer() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());// Cung cáp userservice cho spring security
        provider.setPasswordEncoder(passwordEncoder());// cung cấp password encoder
        return provider;
    }

    //cấu hình cơ chế xác thực người dùng
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationConfigurer());
    }

    //cấu hình các quy tắc bảo mật
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/*").permitAll() //Cho phép tất cả các yêu cầu tới bất kỳ URL nào (pattern "/*") được truy cập công khai mà không yêu cầu xác thực.
                .antMatchers("/admin/*").hasAuthority("ADMIN") //Yêu cầu rằng các yêu cầu tới các URL bắt đầu bằng "/admin/" chỉ có quyền "ADMIN" mới được truy cập
                .and()
                .formLogin() //Bật chức năng đăng nhập và cấu hình các tùy chọn liên quan đến đăng nhập.
                .loginPage("/login") // Xác định URL của trang đăng nhập.
                .loginProcessingUrl("/do-login") //Xác định URL để xử lý yêu cầu đăng nhập.
                .defaultSuccessUrl("/index") //Xác định URL mà người dùng sẽ được chuyển đến sau khi đăng nhập thành công.
                .permitAll() // Cho phép tất cả các yêu cầu tới các URL liên quan đến đăng nhập được truy cập công khai mà không yêu cầu xác thực.
                .and()
                .logout() //ật chức năng đăng xuất và cấu hình các tùy chọn liên quan đến đăng xuất.
                .invalidateHttpSession(true) //Đánh dấu phiên người dùng (HttpSession) là không hợp lệ sau khi đăng xuất.
                .clearAuthentication(true) //Xóa thông tin xác thực của người dùng sau khi đăng xuất.
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //Xác định URL để xử lý yêu cầu đăng xuất.
                .logoutSuccessUrl("/login?logout") //Xác định URL mà người dùng sẽ được chuyển đến sau khi đăng xuất thành công.
                .permitAll(); //Cho phép tất cả các yêu cầu tới các URL liên quan đến đăng xuất được truy cập công khai mà không yêu cầu xác thực.
    }
}
