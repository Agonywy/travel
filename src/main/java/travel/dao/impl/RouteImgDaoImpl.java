package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.RouteImgDao;
import travel.domain.RouteImg;
import travel.util.JDBCUtils;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    @Override
    public List<RouteImg> findByRid(int rid) {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "select  * from tab_route_img where rid = ?";
        List<RouteImg> list = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return list;

    }
}
