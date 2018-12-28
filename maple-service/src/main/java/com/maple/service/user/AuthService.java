package com.maple.service.user;

import java.util.List;

import com.maple.entity.user.PermissionVO;
import com.maple.entity.user.RoleVO;
import com.maple.pojo.user.Permission;
import com.maple.pojo.user.Role;

public interface AuthService {
	int addPermission(Permission permission);

	List<Permission> permList();

	int updatePerm(Permission permission);

	Permission getPermission(int id);

	String delPermission(int id);

	/**
	 * ��ѯ���н�ɫ
	 * @return
	 */
	List<Role> roleList();

	/**
	 * ������ѯȨ�����б�
	 * @return
	 */
	List<PermissionVO> findPerms();

	/**
	 * ��ӽ�ɫ
	 * @param role
	 * @param permIds
	 * @return
	 */
	String addRole(Role role, String permIds);

	RoleVO findRoleAndPerms(Integer id);

	/**
	 * ���½�ɫ����Ȩ
	 * @param role
	 * @param permIds
	 * @return
	 */
	String updateRole(Role role, String permIds);

	/**
	 * ɾ����ɫ�Լ�����Ӧ��Ȩ��
	 * @param id
	 * @return
	 */
	String delRole(int id);

	/**
	 * �������н�ɫ
	 * @return
	 */
	List<Role> getRoles();

	/**
	 * �����û���ȡ��ɫ�б�
	 * @param userId
	 * @return
	 */
	List<Role> getRoleByUser(Integer userId);

	/**
	 * ���ݽ�ɫid��ȡȨ������
	 * @param id
	 * @return
	 */
	List<Permission> findPermsByRoleId(Integer id);

	/**
	 * �����û�id��ȡȨ������
	 * @param id
	 * @return
	 */
	List<PermissionVO> getUserPerms(Integer id);
}
