package travel.service;

import travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活用户
     * @param code
     * @return
     */
    public boolean active(String code);

    /**
     * 用户登录
     * @return
     */

    User login(User user);
}
