package com.dao;

import com.entity.User;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.inject.Named;

/**
 * Created by root on 16-9-28.
 */
@Named
public class UserDao extends BaseDao<User, Long> {

    public User findByUserName(String userName) {
        Criteria c = getSession().createCriteria(User.class).add(Restrictions.eq("userName", userName));
        return (User) c.uniqueResult();
    }

}
