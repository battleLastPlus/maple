package com.maple.serviceImpl.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maple.common.utils.DateUtil;
import com.maple.dao.user.RoleMapper;
import com.maple.dao.user.UserMapper;
import com.maple.dao.user.UserRoleMapper;
import com.maple.entity.user.UserDTO;
import com.maple.entity.user.UserRoleDTO;
import com.maple.entity.user.UserRolesVO;
import com.maple.entity.user.UserSearchDTO;
import com.maple.page.PageDataResult;
import com.maple.pojo.user.Role;
import com.maple.pojo.user.User;
import com.maple.pojo.user.UserRoleKey;
import com.maple.service.user.UserService;
import com.maple.utils.SendMsgServer;

/**
 * @author yrf
 * @date 2018��12��19��
 */

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public PageDataResult getUsers(UserSearchDTO userSearch, int page, int limit) {
		// ʱ�䴦��
		if (null != userSearch) {
			if (StringUtils.isNotEmpty(userSearch.getInsertTimeStart()) && StringUtils.isEmpty(userSearch.getInsertTimeEnd())) {
				userSearch.setInsertTimeEnd(DateUtil.format(new Date()));
			} else if (StringUtils.isEmpty(userSearch.getInsertTimeStart()) && StringUtils.isNotEmpty(userSearch.getInsertTimeEnd())) {
				userSearch.setInsertTimeStart(DateUtil.format(new Date()));
			}
			if (StringUtils.isNotEmpty(userSearch.getInsertTimeStart()) && StringUtils.isNotEmpty(userSearch.getInsertTimeEnd())) {
				if (userSearch.getInsertTimeEnd().compareTo(userSearch.getInsertTimeStart()) < 0) {
					String temp = userSearch.getInsertTimeStart();
					userSearch.setInsertTimeStart(userSearch.getInsertTimeEnd());
					userSearch.setInsertTimeEnd(temp);
				}
			}
		}
		PageDataResult pdr = new PageDataResult();
		PageHelper.startPage(page, limit);
		List<UserRoleDTO> urList = userMapper.getUsers(userSearch);
		// ��ȡ��ҳ��ѯ�������
		PageInfo<UserRoleDTO> pageInfo = new PageInfo<UserRoleDTO>(urList);
		// ���û�ȡ�����ܼ�¼��total��
		pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
		// ����ɫ������ȡ����Ӧ���ֶ���
		if (null != urList && urList.size() > 0) {
			for (UserRoleDTO ur : urList) {
				List<Role> roles = roleMapper.getRoleByUserId(ur.getId());
				if (null != roles && roles.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < roles.size(); i++) {
						Role r = roles.get(i);
						sb.append(r.getRoleName());
						if (i != (roles.size() - 1)) {
							sb.append("��");
						}
					}
					ur.setRoleNames(sb.toString());
				}
			}
		}
		pdr.setList(urList);
		return pdr;
	}

	@Override
	public String setDelUser(Integer id, Integer isDel, Integer insertUid, Integer version) {
		User dataUser = this.userMapper.selectByPrimaryKey(id);
		// �汾��һ��
		if (null != dataUser && null != dataUser.getVersion() && !String.valueOf(version).equals(String.valueOf(dataUser.getVersion()))) {
			return "����ʧ�ܣ������Ժ�����";
		}
		return this.userMapper.setDelUser(id, isDel, insertUid) == 1 ? "ok" : "ɾ��ʧ�ܣ������Ժ�����";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = { RuntimeException.class, Exception.class })
	public String setUser(User user, String roleIds) {
		int userId;
		if (user.getId() != null) {
			// �ж��û��Ƿ��Ѿ�����
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser && !String.valueOf(existUser.getId()).equals(String.valueOf(user.getId()))) {
				return "���ֻ����Ѿ�����";
			}
			User exist = this.userMapper.findUserByName(user.getUsername());
			if (null != exist && !String.valueOf(exist.getId()).equals(String.valueOf(user.getId()))) {
				return "���û����Ѿ�����";
			}
			User dataUser = this.userMapper.selectByPrimaryKey(user.getId());
			// �汾��һ��
			if (null != dataUser && null != dataUser.getVersion() && !String.valueOf(user.getVersion()).equals(String.valueOf(dataUser.getVersion()))) {
				return "����ʧ�ܣ������Ժ�����";
			}
			// �����û�
			userId = user.getId();
			user.setUpdateTime(new Date());
			// ���ü�������
			if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			}
			this.userMapper.updateByPrimaryKeySelective(user);
			// ɾ��֮ǰ�Ľ�ɫ
			List<UserRoleKey> urs = this.userRoleMapper.findByUserId(userId);
			if (null != urs && urs.size() > 0) {
				for (UserRoleKey ur : urs) {
					this.userRoleMapper.deleteByPrimaryKey(ur);
				}
			}
			// ������Լ����޸����֮��ֱ���˳������µ�¼
			
			//===================================��ʱע��==========================================================================//
			
//			User adminUser = (User) SecurityUtils.getSubject().getPrincipal();
//			if (adminUser != null && adminUser.getId().intValue() == user.getId().intValue()) {
//				logger.debug("�����Լ�����Ϣ���˳����µ�¼��adminUser=" + adminUser);
//				SecurityUtils.getSubject().logout();
//			}
//			// ����һ�����Ƽ�����ͨ��SessionDAO�õ��������ߵ��û���Collection<Session> sessions =
//			// sessionDAO.getActiveSessions();
//			// �����ҵ�ƥ��ģ�����������Ϣ�����Ƽ����ֲ�ʽ���û�����̫���ʱ�򣬻������⡣����
//			// ���������Ƽ������û���Ϣ�۸�flag����version����ǣ�д����������ÿ�������ж�flag����version���Ƿ�Ķ������иĶ��������µ�¼���Զ������û���Ϣ���Ƽ�����
//
//			// ���ehcache�������û�Ȩ�޻��棬���봥����Ȩ��������ִ����Ȩ����doGetAuthorizationInfo
//			RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
//			ShiroRealm authRealm = (ShiroRealm) rsm.getRealms().iterator().next();
//			authRealm.clearCachedAuth();
//			logger.debug("��������û�Ȩ�޻��棡����");
			
			//===================================��ʱע��==========================================================================//
		} else {
			// �ж��û��Ƿ��Ѿ�����
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser) {
				return "���ֻ����Ѿ�����";
			}
			User exist = this.userMapper.findUserByName(user.getUsername());
			if (null != exist) {
				return "���û����Ѿ�����";
			}
			// �����û�
			user.setInsertTime(new Date());
			user.setIsDel(false);
			user.setIsJob(false);
			// ���ü�������
			if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			} else {
				user.setPassword(DigestUtils.md5Hex("654321"));
			}
			this.userMapper.insert(user);
			userId = user.getId();
		}
		// ���û��ڽ�ɫ
		String[] arrays = roleIds.split(",");
		for (String roleId : arrays) {
			UserRoleKey urk = new UserRoleKey();
			urk.setRoleId(Integer.valueOf(roleId));
			urk.setUserId(userId);
			this.userRoleMapper.insert(urk);
		}
		return "ok";
	}

	public static void main(String[] args) {
		System.out.println(DigestUtils.md5Hex("654321"));
	}

	@Override
	public String setJobUser(Integer id, Integer isJob, Integer insertUid, Integer version) {
		User dataUser = this.userMapper.selectByPrimaryKey(id);
		// �汾��һ��
		if (null != dataUser && null != dataUser.getVersion() && !String.valueOf(version).equals(String.valueOf(dataUser.getVersion()))) {
			return "����ʧ�ܣ������Ժ�����";
		}
		return this.userMapper.setJobUser(id, isJob, insertUid) == 1 ? "ok" : "����ʧ�ܣ������Ժ�����";
	}

	@Override
	public UserRolesVO getUserAndRoles(Integer id) {
		// ��ȡ�û�������Ӧ��roleIds
		return this.userMapper.getUserAndRoles(id);

	}

	@Override
	public String sendMsg(UserDTO user) {
		// У���û��������� �Ƿ���ȷ
		User existUser = this.userMapper.findUser(user.getUsername(), DigestUtils.md5Hex(user.getPassword()));
		if (null != existUser && existUser.getMobile().equals(user.getMobile())) {
			String mobileCode = "";
			if (existUser.getSendTime() != null) {
				long beginTime = existUser.getSendTime().getTime();
				long endTime = new Date().getTime();
				// 1��������Ч
				if (((endTime - beginTime) < 60000)) {
					logger.debug("���Ͷ�����֤�롾wyait-manager-->UserServiceImpl.sendMsg���û���Ϣ=existUser:" + existUser);
					mobileCode = existUser.getMcode();
				}
			}
			if (StringUtils.isBlank(mobileCode)) {
				// 1�������ڣ���Ч
				mobileCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
				// �������
				existUser.setMcode(mobileCode);
			}
			// ������֤��ʱ�䣬�ӳ�����ǰʱ��
			existUser.setSendTime(new Date());
			this.userMapper.updateByPrimaryKeySelective(existUser);
			// ���Ͷ�����֤�� ok��no
			return SendMsgServer.SendMsg(mobileCode + "(��֤��)���粻�Ǳ��˲���������Դ���Ϣ��", user.getMobile());
		} else {
			return "��������û���Ϣ����������������";
		}
	}

	@Override
	public User findUserByMobile(String mobile) {
		return this.userMapper.findUserByMobile(mobile);
	}

	@Override
	public String sendMessage(int userId, String mobile) {
		String mobile_code = String.valueOf((Math.random() * 9 + 1) * 100000);
		// �������
		User user = new User();
		user.setId(userId);
		user.setMcode(mobile_code);
		user.setSendTime(new Date());
		this.userMapper.updateByPrimaryKeySelective(user);
		// ���Ͷ�����֤�� ok��no
		return SendMsgServer.SendMsg(mobile_code + "(��֤��)���粻�Ǳ��˲���������Դ���Ϣ��", user.getMobile());
	}

	@Override
	public int updatePwd(Integer id, String password) {
		return this.userMapper.updatePwd(id, password);
	}

	@Override
	public int setUserLockNum(Integer id, int isLock) {
		return this.userMapper.setUserLockNum(id, isLock);
	}
}
