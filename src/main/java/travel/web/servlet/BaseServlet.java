package travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    //请求分发--->找指定方法执行
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求路径
        String uri = req.getRequestURI();
        //2.获取方法名 /travel/user/方法名
        String requestMethod = uri.substring(uri.lastIndexOf('/') + 1);
        //3.获取方法对象Method
        try {
            Method method = UserServlet.class.getMethod(requestMethod, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //method.setAccessible(true);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    //直接将传入的对象序列化json返回,并且写回客户端
    public void writeValue(Object obj,HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(),obj);
    }
    //直接将传入的对象序列化json返回,并且返回给调用者
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(obj);
        return s;
    }
}
