package ma.theBeans.app.auth.security.service.facade;

import ma.theBeans.app.auth.security.bean.Role;
import ma.theBeans.app.auth.security.dao.criteria.core.RoleCriteria;
import ma.theBeans.app.auth.service.IService;



public interface RoleService extends  IService<Role,RoleCriteria>  {
    Role findByAuthority(String authority);
    int deleteByAuthority(String authority);


    



}
