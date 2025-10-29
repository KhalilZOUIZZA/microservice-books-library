package ma.theBeans.app.thePackage.security.service.facade;

import ma.theBeans.app.thePackage.security.bean.Role;
import ma.theBeans.app.thePackage.security.dao.criteria.core.RoleCriteria;
import ma.theBeans.app.thePackage.service.IService;



public interface RoleService extends  IService<Role,RoleCriteria>  {
    Role findByAuthority(String authority);
    int deleteByAuthority(String authority);


    



}
