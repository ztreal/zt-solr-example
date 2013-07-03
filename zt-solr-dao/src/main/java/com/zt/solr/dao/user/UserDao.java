package com.zt.solr.dao.user;

import com.zt.solr.domain.user.User;

import java.util.List;

/**
 * User: zhangtan
 * Date: 12-9-7
 * Time: 上午9:27
 */
public interface UserDao {

    /**
     * 增加用户
     * @param user  用户vo
     */
    public void insertUser(User user);

    /**
     * getSqlSession().selectList(sqlId, paramMap,new RowBounds(pageId, pageSize));
     * 查询用户
     * @param user  用户vo
     */
    public List<User> queryAll(User user);
}
