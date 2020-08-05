package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.FavoriteDao;
import travel.domain.Favorite;
import travel.util.JDBCUtils;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    //查询是否收藏
    public Favorite findByUidAndRid(int rid, int uid) {
        String sql = "select flag from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return favorite;
    }

    //收藏次数
    @Override
    public int findCountByRid(int rid) {
        String sql = "select count(*) from tab_favorite where rid = ?";
        Integer count = template.queryForObject(sql, Integer.class, rid);
        return count;
    }
}
