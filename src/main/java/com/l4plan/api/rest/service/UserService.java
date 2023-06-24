package com.l4plan.api.rest.service;

import com.l4plan.api.rest.dao.UserDao;
import com.l4plan.api.rest.dto.RegisterUserDTO;
import com.l4plan.api.rest.dto.UserDTO;
import com.l4plan.api.rest.model.User;
import com.l4plan.api.rest.utils.ObjectMapperUtils;
import com.l4plan.api.rest.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService extends GenericService<User, UserDTO> {

    @Autowired
    private UserDao userDao;

    public boolean existsUserByEmail(String email) {
        return userDao.existsUserByEmail(email);
    }

    public UserDTO findByEmail(String email) {
        List<User> result = userDao.findByEmail(email);

        if(CollectionUtils.isEmpty(result)) {
            throw new UsernameNotFoundException("El correo electronico no existe");
        } else if(result.size() > 1) {
            // This should not happen
            throw new UsernameNotFoundException("Hay mas de una cuenta con este correo electronico, ponte en contacto con el administrador");
        }

        return ObjectMapperUtils.map(result.get(0), UserDTO.class);
    }

    public Long registerUser(RegisterUserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.USER);
        user.setPass(dto.getPass());
        userDao.insert(user);
        return user.getId();
    }
}
