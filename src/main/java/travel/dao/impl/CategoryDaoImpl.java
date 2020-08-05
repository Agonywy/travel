package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.CategoryDao;
import travel.domain.Category;
import travel.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    public List<Category> findAll() {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        List<Category> list = new ArrayList<>();
        String sql = "select * from tab_category";
        try {
            list = (List<Category>) template.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
