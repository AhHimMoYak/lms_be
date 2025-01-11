package click.ahimmoyak.companyservice.auth.config.handler;

import click.ahimmoyak.companyservice.auth.dto.validation.SecurityExceptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String referer = request.getHeader("Referer");

        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        String json = new ObjectMapper().writeValueAsString(SecurityExceptionDto.builder()
                .statusCode(HttpServletResponse.SC_FORBIDDEN)
                .msg("Access Denied: You do not have the right role to access this resource.")
                .build());
        response.getWriter().write(json);
    }
}
