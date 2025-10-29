package ma.theBeans.app.auth.security.service.impl;


import ma.theBeans.app.auth.security.bean.ActionPermission;
import ma.theBeans.app.auth.security.dao.criteria.core.ActionPermissionCriteria;
import ma.theBeans.app.auth.security.dao.facade.core.ActionPermissionDao;
import ma.theBeans.app.auth.security.dao.specification.core.ActionPermissionSpecification;
import ma.theBeans.app.auth.security.service.facade.ActionPermissionService;
import ma.theBeans.app.auth.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionPermissionServiceImpl extends AbstractServiceImpl<ActionPermission, ActionPermissionCriteria, ActionPermissionDao> implements ActionPermissionService {





    public ActionPermission findByReferenceEntity(ActionPermission t){
        return  dao.findByReference(t.getReference());
    }


    public List<ActionPermission> findAllOptimized() {
        return dao.findAllOptimized();
    }





    public void configure() {
        super.configure(ActionPermission.class, ActionPermissionSpecification.class);
    }



    public ActionPermissionServiceImpl(ActionPermissionDao dao) {
        super(dao);
    }

}
