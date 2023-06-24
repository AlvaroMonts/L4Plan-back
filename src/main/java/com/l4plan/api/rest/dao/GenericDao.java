package com.l4plan.api.rest.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public abstract class GenericDao<T extends Serializable> {

    private final Class<T> entityClass;

    @Autowired
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Transactional
    public T get(Long id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional
    public List<T> getAll() {
        return entityManager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
    }

    @Transactional
    public T insert(T t) {
        entityManager.persist(t);
        return t;
    }

    @Transactional
    public void update(T t) {
        entityManager.merge(t);
    }

    @Transactional
    public void delete(Long id) {
        entityManager.remove(entityManager.find(entityClass, id));
    }
}
