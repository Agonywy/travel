package travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import travel.domain.ResultInfo;
import travel.domain.User;
import travel.service.UserService;
import travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    //注册方法
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //验证码校验
        String check = req.getParameter("check");
        //从session中获取验证码
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            info.setErrorMsg("验证码错误");
            info.setFlag(false);
            //将info对象序列化为json字符串
            ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(info);
            //将数据响应给客户端
            //设置头部,返回json
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write(s);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = req.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service来完成注册
        boolean flag = service.regist(user);
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(flag);
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        //将info对象序列化为json,返回给客户端
        //使用Jackson的核心对象ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回到客户端
        //设置content-type头部
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(json);
    }
    //登录方法
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //验证码校验
        String check = req.getParameter("check");
        //从session中获取验证码
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            info.setErrorMsg("验证码错误");
            info.setFlag(false);
            return;
        }


        //1.获取用户名,密码,激活状态
        Map<String, String[]> map = req.getParameterMap();
        //2.封装一个User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用userService去处理
        User u = service.login(user);
        //4.判断用户对象是否null
        if(u == null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //用户是否激活
        if (u!= null && !"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("用户没有激活");
        }
        //判断登录成功
        if(u!=null && "Y".equals(u.getStatus())){
            info.setFlag(true);
        }
        //响应数据给前台,将对象序列化为json数据传递给前台
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json,charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(),info);
    }
    //查找用户方法
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session中获取登录用户
        Object user= req.getSession().getAttribute("user");
        //直接将user写回客户端
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(),user);
    }
    //退出方法
    public void exit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.销毁session
        req.getSession().invalidate();
        //2.重定向跳转：注意这个要使用虚拟路径
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
    //激活用户
    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取激活码
        String code = req.getParameter("code");
        //2.实现激活
        if(code != ""){
            //3.使用激活码去激活用户
            boolean flag = service.active(code);
            String msg = null;
            //判断标记
            if(flag){
                //激活成功
                msg = "激活成功请<a href='login.html'>登录</a>";
            }else{
                //激活失败
                msg = "激活失败,请联系管理员";
            }
            //将消息返回给服务器

            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write(msg);
        }

    }

}
