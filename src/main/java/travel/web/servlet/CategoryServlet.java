package travel.web.servlet;

import travel.domain.Category;
import travel.service.CategoryService;
import travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();
    //查询所有分类信息
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.查询所有
        List<Category> list = service.findAll();
        //2.序列化json返回
        writeValue(list,resp);
    }
}
