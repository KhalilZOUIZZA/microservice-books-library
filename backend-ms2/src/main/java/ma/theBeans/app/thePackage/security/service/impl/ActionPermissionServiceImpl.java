package ma.theBeans.app.thePackage.security.service.impl;


import ma.theBeans.app.thePackage.security.bean.ActionPermission;
import ma.theBeans.app.thePackage.security.dao.criteria.core.ActionPermissionCriteria;
import ma.theBeans.app.thePackage.security.dao.facade.core.ActionPermissionDao;
import ma.theBeans.app.thePackage.security.dao.specification.core.ActionPermissionSpecification;
import ma.theBeans.app.thePackage.security.service.facade.ActionPermissionService;
import ma.theBeans.app.thePackage.service.AbstractServiceImpl;
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
