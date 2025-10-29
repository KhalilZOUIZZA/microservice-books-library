package ma.theBeans.app.auth.security.service.facade;

import ma.theBeans.app.auth.security.bean.RoleUser;
import ma.theBeans.app.auth.security.dao.criteria.core.RoleUserCriteria;
import ma.theBeans.app.auth.service.IService;

import java.util.List;



public interface RoleUserService extends  IService<RoleUser,RoleUserCriteria>  {

    List<RoleUser> findByRoleId(Long id);
    int deleteByRoleId(Long id);
    long countByRoleAuthority(String authority);
    List<RoleUser> findByUserId(Long id);
    int deleteByUserId(Long id);
    long countByUserEmail(String email);



}
