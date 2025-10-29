package ma.theBeans.app.auth.security.service.impl;


import ma.theBeans.app.auth.security.bean.ModelPermission;
import ma.theBeans.app.auth.security.dao.criteria.core.ModelPermissionCriteria;
import ma.theBeans.app.auth.security.dao.facade.core.ModelPermissionDao;
import ma.theBeans.app.auth.security.dao.specification.core.ModelPermissionSpecification;
import ma.theBeans.app.auth.security.service.facade.ModelPermissionService;
import ma.theBeans.app.auth.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelPermissionServiceImpl extends AbstractServiceImpl<ModelPermission, ModelPermissionCriteria, ModelPermissionDao> implements ModelPermissionService {



    public ModelPermission findByReferenceEntity(ModelPermission t){
        return  dao.findByReference(t.getReference());
    }


    public List<ModelPermission> findAllOptimized() {
        return dao.findAllOptimized();
    }





    public void configure() {
        super.configure(ModelPermission.class, ModelPermissionSpecification.class);
    }



    public ModelPermissionServiceImpl(ModelPermissionDao dao) {
        super(dao);
    }

}
