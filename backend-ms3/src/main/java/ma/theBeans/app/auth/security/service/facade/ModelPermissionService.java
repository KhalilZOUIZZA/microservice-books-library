package ma.theBeans.app.auth.security.service.facade;

import ma.theBeans.app.auth.security.bean.ModelPermission;
import ma.theBeans.app.auth.security.dao.criteria.core.ModelPermissionCriteria;
import ma.theBeans.app.auth.service.IService;
import java.util.List;



public interface ModelPermissionService extends  IService<ModelPermission,ModelPermissionCriteria>  {
    List<ModelPermission> findAllOptimized();

}
