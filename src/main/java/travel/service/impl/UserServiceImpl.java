package travel.service.impl;

import travel.dao.UserDao;
import travel.dao.impl.UserDaoImpl;
import travel.domain.User;
import travel.service.UserService;
import travel.util.MailUtils;
import travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    private UserDao dao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1.根据用户名去查询用户对象
        User u = dao.findByUsername(user.getUsername());
        if(u != null){
            //注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码（要求唯一）,通过UUID.randomUUID()生成一个唯一的字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);
        //3.激活邮件发送,发送正文？是个超链接,用户访问所以写绝对路径
        String content = "<a href='http://localhost/travel/user/active?code="+u.getCode()+"'>点击激活【Agony旅游网】</a>";
        MailUtils.sendMail(u.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        User user = dao.findUserByCode(code);
        if(user != null){
            dao.updateStatus(code);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        return dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

}
