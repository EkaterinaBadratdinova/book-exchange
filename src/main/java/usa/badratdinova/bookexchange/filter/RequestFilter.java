package usa.badratdinova.bookexchange.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter("/*")
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod())) {
            String methodOverride = httpRequest.getParameter("_method");
            if ("PATCH".equalsIgnoreCase(methodOverride)) {
                HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
                    @Override
                    public String getMethod() {
                        return "PATCH";
                    }
                };
                chain.doFilter(wrapper, response);
                return;
            }

            if ("DELETE".equalsIgnoreCase(methodOverride)) {
                HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
                    @Override
                    public String getMethod() {
                        return "DELETE";
                    }
                };
                chain.doFilter(wrapper, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
