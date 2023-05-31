package com.ecommerce.admin.config;

import com.ecommerce.library.model.Admin;
import com.ecommerce.library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class AdminServiceConfig implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) throw new UsernameNotFoundException("Không thể tìm thấy username");
        return new User(
                admin.getUsername(),
                admin.getPassword(),
                //một danh sách List<SimpleGrantedAuthority> chứa các quyền (authorities) đã được tạo từ danh sách các đối tượng Role.
                admin.getRoles()
                        .stream() //Chuyển đổi danh sách admin.getRoles() thành một luồng (stream) các đối tượng Role.
                        //Ánh xạ (map) mỗi đối tượng Role thành một đối tượng SimpleGrantedAuthority mới, với role.getName() được sử dụng để lấy tên quyền (authority).
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())//Thu thập (collect) các đối tượng SimpleGrantedAuthority đã được ánh xạ vào một danh sách (List).
        );
    }
}
