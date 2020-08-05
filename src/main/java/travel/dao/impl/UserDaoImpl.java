package travel.dao.impl;

import travel.dao.UserDao;
import travel.domain.User;
import travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    //1.根据用户名去查询用户对象
    public User findByUsername(String username) {
        User user = null;
        try {
            //1.定义sql语句
            String sql = "select * from tab_user where username = ?";
            //2.执行sql,bean属性行映射
            //这个方法不会返回null,而会抛出异常,所以try处理一下更好
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    //2.保存用户信息@Override
    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),
                user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    public User findUserByCode(String code) {
        JdbcTemplate template = new JdbcTemplate();
        User user = null;
        //1.sql语句
        String sql = "select * from tab_user where code = ?";
        //执行sql
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class),code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(String code) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "update tab_user set status = 'Y' where code = ?";
        template.update(sql,code);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from tab_user where username = ? and password = ?";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
