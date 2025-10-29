package ma.theBeans.app.thePackage.security.service.facade;

import ma.theBeans.app.thePackage.security.bean.ModelPermission;
import ma.theBeans.app.thePackage.security.dao.criteria.core.ModelPermissionCriteria;
import ma.theBeans.app.thePackage.service.IService;
import java.util.List;



public interface ModelPermissionService extends  IService<ModelPermission,ModelPermissionCriteria>  {
    List<ModelPermission> findAllOptimized();

}
