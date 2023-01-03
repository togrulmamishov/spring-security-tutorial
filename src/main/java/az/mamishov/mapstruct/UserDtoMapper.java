package az.mamishov.mapstruct;

import az.mamishov.dto.UserRequest;
import az.mamishov.dto.UserResponse;
import az.mamishov.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    UserResponse userEntityToResponse(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity userRequestToEntity(UserRequest userRequest);
}
