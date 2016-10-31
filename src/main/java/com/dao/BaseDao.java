package com.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;

public class BaseDao<T, PK extends Long> {

    private static Logger log = Logger.getLogger(BaseDao.class);
    private SessionFactory sessionFactory;
    private Class<?> clazz;

    public BaseDao() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<?>) pt.getActualTypeArguments()[0];
    }

    @Inject
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }


    public void save(T t) {
        getSession().save(t);
    }

    public List<T> findAll() {
        Criteria c = getSession().createCriteria(clazz);
        return c.list();
    }

    public void del(PK id) {
        getSession().delete(findById(id));
    }

    public T findById(PK id) {
        return (T) getSession().get(clazz, id);
    }

    public void update(T t) {
        getSession().update(t);
    }

    /**
     * 批量保存数据到数据库
     *
     * @param list
     * @param max
     */
    public void batchSave(List<T> list, int max) {
        log.info("插入文件的长度为：" + list.size());
        if (max <= 0) {
            max = 100;
        }
        Session session = sessionFactory.getCurrentSession();
        for (int i = 0; i < list.size(); i++) {
            session.save(list.get(i));
            if (i % max == 0) {
                session.flush();
                session.clear();
            }
        }
    }

}
