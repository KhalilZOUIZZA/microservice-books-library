package ma.theBeans.app.thePackage.security.service.facade;

import ma.theBeans.app.thePackage.security.bean.RoleUser;
import ma.theBeans.app.thePackage.security.dao.criteria.core.RoleUserCriteria;
import ma.theBeans.app.thePackage.service.IService;

import java.util.List;



public interface RoleUserService extends  IService<RoleUser,RoleUserCriteria>  {

    List<RoleUser> findByRoleId(Long id);
    int deleteByRoleId(Long id);
    long countByRoleAuthority(String authority);
    List<RoleUser> findByUserId(Long id);
    int deleteByUserId(Long id);
    long countByUserEmail(String email);



}
