package travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CharchaterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //将父接口转为子接口
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取请求方法
        String method = req.getMethod();
        //解决post请求中文数据乱码问题
        if(method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("text/html;character = UTF-8");
            filterChain.doFilter(req,resp);
        }
    }
    @Override
    public void destroy() {

    }
}
