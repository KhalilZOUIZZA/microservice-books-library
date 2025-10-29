package ma.theBeans.app.auth.security.service.impl;


import ma.theBeans.app.auth.security.bean.Role;
import ma.theBeans.app.auth.security.dao.criteria.core.RoleCriteria;
import ma.theBeans.app.auth.security.dao.facade.core.RoleDao;
import ma.theBeans.app.auth.security.dao.specification.core.RoleSpecification;
import ma.theBeans.app.auth.security.service.facade.RoleService;
import ma.theBeans.app.auth.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractServiceImpl<Role, RoleCriteria, RoleDao> implements RoleService {


    @Override
    public Role findByAuthority(String authority) {
        return dao.findByAuthority(authority);
    }

    @Override
    public int deleteByAuthority(String authority) {
        return deleteByAuthority(authority);
    }



    public Role findByReferenceEntity(Role t){
        return  dao.findByAuthority(t.getAuthority());
    }


    public List<Role> findAllOptimized() {
        return dao.findAllOptimized();
    }





    public void configure() {
        super.configure(Role.class, RoleSpecification.class);
    }



    public RoleServiceImpl(RoleDao dao) {
        super(dao);
    }

}
