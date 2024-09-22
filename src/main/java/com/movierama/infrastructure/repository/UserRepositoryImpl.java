package com.movierama.infrastructure.repository;

import com.movierama.domain.model.User;
import com.movierama.domain.repository.UserRepository;
import com.movierama.infrastructure.entity.UserEntity;
import com.movierama.infrastructure.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Autowired
    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserEntityMapper userEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(userEntity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(userEntityMapper::toDomain);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        userJpaRepository.delete(userEntity);
    }
}