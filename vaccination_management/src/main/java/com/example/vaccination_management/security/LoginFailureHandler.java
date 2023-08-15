package com.example.vaccination_management.security;

import com.example.vaccination_management.dto.LoginDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        if (username.equals("")) {
            System.out.println("Rỗng username");
        }
        if (password.equals("")) {
            System.out.println("Rỗng password");
        }
        request.setAttribute("msg", "Đằng nhập thất bại");
        request.setAttribute("param", "error");

        String errorMessage = "Tên đăng nhập  hoặc tài khoản không hợp lệ !";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid Username or Password";
        } else if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "Invalid Secret key";
        }
        request.setAttribute("errorMessage",errorMessage);
        setDefaultFailureUrl("/login?error=true&errorMessage");

        super.onAuthenticationFailure(request, response, exception);
//        request.setAttribute("errorMessage",errorMessage);
//        request.getRequestDispatcher("/login?errorMessage").forward(request,response);
//        response.sendRedirect("/login");

//        response.sendRedirect(request.getContextPath() + "/login");
//        super.onAuthenticationFailure(request, response, exception);

    }
}
