package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.SellerDao;
import travel.domain.Seller;
import travel.util.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findSellerBySid(int sid) {
        String sql = "select * form tab_seller where sid = ?";
        Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return seller;
    }
}
