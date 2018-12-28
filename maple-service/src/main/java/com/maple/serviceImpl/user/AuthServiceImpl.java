package com.maple.serviceImpl.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.maple.dao.user.PermissionMapper;
import com.maple.dao.user.RoleMapper;
import com.maple.dao.user.RolePermissionMapper;
import com.maple.entity.user.PermissionVO;
import com.maple.entity.user.RoleVO;
import com.maple.pojo.user.Permission;
import com.maple.pojo.user.Role;
import com.maple.pojo.user.RolePermissionKey;
import com.maple.service.user.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthServiceImpl.class);
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RolePermissionMapper rolePermissionMapper;
	
	public int addPermission(Permission permission) {
		return this.permissionMapper.insert(permission);
	}

	public List<Permission> permList() {
		return this.permissionMapper.findAll();
	}

	public int updatePerm(Permission permission) {
		return this.permissionMapper.updateByPrimaryKeySelective(permission);
	}

	public Permission getPermission(int id) {
		return this.permissionMapper.selectByPrimaryKey(id);
	}

	public String delPermission(int id) {
		//�鿴��Ȩ���Ƿ����ӽڵ㣬����У���ɾ���ӽڵ�
		List<Permission> childPerm = this.permissionMapper.findChildPerm(id);
		if(null != childPerm && childPerm.size()>0){
			return "ɾ��ʧ�ܣ�������ɾ����Ȩ�޵��ӽڵ�";
		}
		if(this.permissionMapper.deleteByPrimaryKey(id)>0){
			return "ok";
		}else{
			return "ɾ��ʧ�ܣ������Ժ�����";
		}
	}

	public List<Role> roleList() {
		return this.roleMapper.findList();
	}

	public List<PermissionVO> findPerms() {
		return this.permissionMapper.findPerms();
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String addRole(Role role, String permIds) {
		this.roleMapper.insert(role);
		int roleId=role.getId();
		String[] arrays=permIds.split(",");
		logger.debug("Ȩ��id =arrays="+arrays.toString());
		setRolePerms(roleId, arrays);
		return "ok";
	}

	public RoleVO findRoleAndPerms(Integer id) {
		return this.roleMapper.findRoleAndPerms(id);
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String updateRole(Role role, String permIds) {
		int roleId=role.getId();
		String[] arrays=permIds.split(",");
		logger.debug("Ȩ��id =arrays="+arrays.toString());
		//1�����½�ɫ�����ݣ�
		int num=this.roleMapper.updateByPrimaryKeySelective(role);
		if(num<1){
			//����ع�
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		//2��ɾ��ԭ��ɫȨ�ޣ�
		batchDelRolePerms(roleId);
		//3������µĽ�ɫȨ�����ݣ�
		setRolePerms(roleId, arrays);
		return "ok";
	}



	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
	public String delRole(int id) {
		//1.ɾ����ɫ��Ӧ��Ȩ��
		batchDelRolePerms(id);
		//2.ɾ����ɫ
		int num=this.roleMapper.deleteByPrimaryKey(id);
		if(num<1){
			//����ع�
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "����ʧ��";
		}
		return "ok";
	}

	public List<Role> getRoles() {
		//TODO ���ݲ��ź�Ȩ�޵ȼ����ƽ�ɫ��ʾ
		return this.roleMapper.getRoles();
	}

	public List<Role> getRoleByUser(Integer userId) {
		return this.roleMapper.getRoleByUserId(userId);
	}

	public List<Permission> findPermsByRoleId(Integer id) {
		return this.permissionMapper.findPermsByRole(id);
	}

	public List<PermissionVO> getUserPerms(Integer id) {
		return this.permissionMapper.getUserPerms(id);
	}

	/**
	 * ����ɾ����ɫȨ���м������
	 * @param roleId
	 */
	private void batchDelRolePerms(int roleId) {
		List<RolePermissionKey> rpks=this.rolePermissionMapper.findByRole(roleId);
		if(null!=rpks && rpks.size()>0){
			for (RolePermissionKey rpk : rpks) {
				this.rolePermissionMapper.deleteByPrimaryKey(rpk);
			}
		}
	}

	/**
	 * ����ǰ��ɫ����Ȩ��
	 * @param roleId
	 * @param arrays
	 */
	private void setRolePerms(int roleId, String[] arrays) {
		for (String permid : arrays) {
			RolePermissionKey rpk=new RolePermissionKey();
			rpk.setRoleId(roleId);
			rpk.setPermitId(Integer.valueOf(permid));
			this.rolePermissionMapper.insert(rpk);
		}
	}
}
