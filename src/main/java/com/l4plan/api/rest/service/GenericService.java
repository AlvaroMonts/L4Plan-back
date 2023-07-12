package com.l4plan.api.rest.service;

import com.l4plan.api.rest.dao.GenericDao;
import com.l4plan.api.rest.utils.ObjectMapperUtils;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Service
public abstract class GenericService<T extends Serializable, DTO> {

    @Autowired
    private GenericDao<T> genericDao;

    private final Class<DTO> dtoClass;
    
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public GenericService() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        dtoClass = (Class<DTO>) pt.getActualTypeArguments()[1];
    }

    public List<DTO> findAll() {
        List<T> entities = genericDao.getAll();
        return ObjectMapperUtils.mapList(entities, dtoClass);
    }

    public DTO findById(Long id) {
        T entity = genericDao.get(id);
        if(entity == null) {
            return null;
        }
        return ObjectMapperUtils.map(entity, dtoClass);
    }

    public Long create(DTO dto) {
        T entity = ObjectMapperUtils.map(dto, entityClass);
        genericDao.insert(entity);
        return (Long) getEntityId(entity);
    }

    public void update(Long id, DTO dto) {
        DTO dtoById = findById(id);
        if(dtoById != null) {
            BeanUtils.copyProperties(dto, dtoById, "id");
            T entity = ObjectMapperUtils.map(dtoById, entityClass);
            genericDao.update(entity);
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(Long id) {
        DTO dtoById = findById(id);
        if(dtoById != null) {
            genericDao.delete(id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Object getEntityId(@NotNull Object dto) {
        for (Field field : dto.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    return field.get(dto);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
