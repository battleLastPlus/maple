package com.maple.dao.user;

import java.util.List;

import com.maple.pojo.user.RolePermissionKey;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);
    
    List<RolePermissionKey> findByRole(int roleId);
}