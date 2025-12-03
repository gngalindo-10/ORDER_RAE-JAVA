package project.order_rae.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {


        String redirectURL = "/";

        for (GrantedAuthority auth : authentication.getAuthorities()) {

            String role = auth.getAuthority();

            // Cliente regresa al index
            if (role.equals("ROLE_Cliente")) {
                redirectURL = "/";
                break;
            }

            // Todos los demás roles van al dashboard
            if (role.equals("ROLE_ADMIN") ||
                role.equals("ROLE_Asesor Comercial") ||
                role.equals("ROLE_Gerente") ||
                role.equals("ROLE_Jefe Logistico")) {

                redirectURL = "/dashboard";
                break;
            }
        }

        response.sendRedirect(redirectURL);
    }
}
