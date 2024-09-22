// Infrastructure Layer: src/main/java/com/movierama/infrastructure/mapper/UserEntityMapper.java

package com.movierama.infrastructure.mapper;

import com.movierama.domain.model.User;
import com.movierama.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between User and UserEntity.
 */
@Component
public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        return entity;
    }

    public User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getPassword(), entity.getEmail());
    }
}
