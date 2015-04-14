package edu.alvin.ninja.core.filters;

import com.google.common.base.Strings;

import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@Singleton
public class HttpMethodFilter implements Filter {

    public static final String METHOD = "_method";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String method = request.getParameter(METHOD);
            if (Strings.isNullOrEmpty(method)) {
                method = request.getMethod();
            } else {
                method = method.toUpperCase();
            }
            servletRequest = new HttpMethodRequestWrapper(request, method);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }

    public static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {
        private String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public String getContentType() {
            String contentType = super.getContentType();
            if (contentType != null) {
                return contentType;
            }
            return "application/x-www-form-urlencoded";
        }
    }
}
