package com.ecommerce.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Xác định gói (package) cơ sở để quét các thành phần như @Component, @Service, @Repository,... và các bean khác trong ứng dụng
 */
@SpringBootApplication(scanBasePackages = {"com.ecommerce.library.*", "com.ecommerce.admin.*"})
/**
 * được sử dụng để bật tính năng quản lý các repository trong ứng dụng
 * Xác định gói (package) chứa các repository (interface) của ứng dụng.
 * Repository trong Spring Data JPA được sử dụng để truy vấn và tương tác với cơ sở dữ liệu.
 * */
@EnableJpaRepositories(value = "com.ecommerce.library.repository")
/**
 * sử dụng để quét và tìm kiếm các lớp @Entity trong ứng dụng
 * Xác định gói (package) chứa các lớp đối tượng (@Entity) trong ứng dụng.
 * Các lớp @Entity được sử dụng để ánh xạ đối tượng với các bảng trong cơ sở dữ liệu.
 * */
@EntityScan(value = "com.ecommerce.library.model")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
