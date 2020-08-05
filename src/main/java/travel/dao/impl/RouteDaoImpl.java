package travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import travel.dao.RouteDao;
import travel.domain.Route;
import travel.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    //根据cid查询共有多少条记录
    @Override
    public int finTotalCount(int cid,String rname) {
//        String sql = "select count(*) from tab_route where cid = ?";
        //1.定义一个sql模板
        String sql = "select count(*) from tab_route where 1=1" ;
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();  //条件们
        //2.判断这些参数是否有值
        if(cid != 0){
            sb.append(" and cid = ?");
            params.add(cid);    //添加?对应的值
        }
        if(rname != null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        Integer sum = template.queryForObject(sql, Integer.class, params.toArray());
        return sum;
    }
    //分页查询
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        String sql = "select * from table_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();  //条件们
        //2.判断这些参数是否有值
        if(cid != 0){
            sb.append(" and cid = ?");
            params.add(cid);    //添加?对应的值
        }
        if(rname != null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        params.add(start);
        params.add(pageSize);
        sb.append(" limit ?,?");
        sql = sb.toString();
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        return query;
    }

    //查询详情信息
    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }
}
