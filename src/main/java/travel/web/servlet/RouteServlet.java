package travel.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import travel.domain.PageBean;
import travel.domain.Route;
import travel.domain.User;
import travel.service.FavoriteService;
import travel.service.RouteService;
import travel.service.impl.FavoriteServiceImpl;
import travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebServlet("route/*")
public class RouteServlet extends BaseServlet {

     //分页查询
    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        //1.接收参数
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");
        String rname = req.getParameter("rname");
        //处理乱码
        rname = new String(rname.getBytes("iso-8859-1"),"UTF-8");
        //处理参数
        int cid = 0;  //类别id
        if(cidStr!=null && cidStr.length()>0 && "null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;    //当前页数,如果传递不传递,则默认是第一页
        if(currentPageStr!=null && currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;    //默认为第一页
        }
        int pageSize = 0; //每页显示条数,如果不传递默认显示5条记录
        if(pageSizeStr!=null && pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;   //默认5条数据
        }

        //3.调用service查询PageBean对象
        RouteService service = new RouteServiceImpl();
        //4.将对象序列化传给客户端
        resp.setContentType("application/json;charset=UTF-8");
        PageBean<Route> routePageBean = service.pageQuery(cid, currentPage, pageSize,rname);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(resp.getOutputStream(),routePageBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据rid查询一个旅游线路的详情信息
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        RouteService service = new RouteServiceImpl();
        Route route = service.findOne(rid);
        //转为json返回给客户端
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json,charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(),route);
    }
    //判断当前用户是否收藏过该线路
    public void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FavoriteService favoriteService = new FavoriteServiceImpl();
        //1.获取前台传过来的参数rid
        String rid = req.getParameter("rid");
        //2.获取当前登录的用户user
        User user = (User) req.getSession().getAttribute("user");
        int uid;
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }
        //3.调用favoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(),flag);
    }
}
