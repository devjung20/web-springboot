package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title","Login Page");
        return "login";
    }

    @RequestMapping("/index")
    public String home(Model model) {
        model.addAttribute("title","Home Page");
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title","Register Page");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title","Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                              BindingResult result, Model model) {
        //BindingResult: dữ liệu từ DTO và BindingResult sẽ ktra và xử lý lỗi
        try {
            if (result.hasErrors()) { //Kiểm hasErrors = true có nghĩa xảy ra lỗi
                model.addAttribute("adminDto", adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Email của bạn đã được đăng ký!");
                System.out.println("admin not null");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("success", "Đăng ký thành công!");
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Mật khẩu không giống nhau!");
                System.out.println("password not same");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Máy chủ bị lỗi, xin vui lòng thử lại sau!");
        }

        return "register";
    }
}
