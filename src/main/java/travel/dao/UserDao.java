package travel.dao;

import travel.domain.User;

public interface UserDao {
    //1.根据用户名去查询用户对象
    public User findByUsername(String username);
    //2.保存用户信息
    public void save(User user);

    //根据激活码去查找用户是否存在
    public User findUserByCode(String code);
    //修改用户激活状态status
    public void updateStatus(String code);
    //用户密码登录
    User findByUsernameAndPassword(String username,String password);

}
