package az.mamishov.mapper;

import az.mamishov.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserEntity> findByUsername(String username);

    UserEntity findById(Integer id);

    void insertUser(UserEntity user);

    Integer getInsertedId();
}
