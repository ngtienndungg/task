package vn.dungnt.nothing.data.mappers

import vn.dungnt.nothing.data.base.Mapper
import vn.dungnt.nothing.data.models.UserModel
import vn.dungnt.nothing.domain.entities.UserEntity

class UserMapper : Mapper<UserModel, UserEntity> {
    override fun toEntity(model: UserModel): UserEntity {
        return UserEntity(
            model.id,
            model.username,
            model.password,
            model.email,
            model.accessToken,
            model.firstName,
            model.lastName,
            model.avatar
        )
    }

    override fun toModel(entity: UserEntity): UserModel {
        return UserModel().also {
            it.id = entity.id
            it.username = entity.username
            it.password = entity.password
            it.email = entity.email
            it.accessToken = entity.accessToken
            it.firstName = entity.firstName
            it.lastName = entity.lastName
            it.avatar = entity.avatar
        }
    }
}