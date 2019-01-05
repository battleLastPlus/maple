package com.maple.service.user;

import com.maple.entity.user.UserDTO;
import com.maple.entity.user.UserRolesVO;
import com.maple.entity.user.UserSearchDTO;
import com.maple.page.PageDataResult;
import com.maple.pojo.user.User;

/**
 * @author yrf
 * @date 2018��12��19��
 */
public interface UserService {
	/**
	 * ��ҳ��ѯ�û��б�
	 * @param page
	 * @param limit
	 * @return
	 */
	PageDataResult getUsers(UserSearchDTO userSearch, int page, int limit);

	/**
	 *	�����û�����������¡�
	 * @param user
	 * @param roleIds
	 * @return
	 */
	String setUser(User user, String roleIds);

	/**
	 * �����û��Ƿ���ְ
	 * @param id
	 * @param isJob
	 * @param insertUid
	 * @return
	 */
	String setJobUser(Integer id, Integer isJob,Integer insertUid,Integer version);

	/**
	 * ɾ���û�
	 * @param id
	 * @param isDel
	 * @return
	 */
	String setDelUser(Integer id, Integer isDel,Integer insertUid,Integer version);

	/**
	 * ��ѯ�û�����
	 * @param id
	 * @return
	 */
	UserRolesVO getUserAndRoles(Integer id);

	/**
	 * ���Ͷ�����֤��
	 * @param user
	 * @return
	 */
	String sendMsg(UserDTO user);

	/**
	 * �����ֻ��Ų�ѯ�û�����
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);

	/**
	 * �����ֻ��ŷ��Ͷ�����֤��
	 * @param mobile
	 * @return
	 */
	String sendMessage(int userId, String mobile);

	/**
	 * �޸��û��ֻ���
	 * @param id
	 * @param password
	 * @return
	 */
	int updatePwd(Integer id, String password);

	/**
	 * �����û�
	 * @param id
	 * @param isLock  0:������1������
	 * @return
	 */
	int setUserLockNum(Integer id,int isLock);
}
