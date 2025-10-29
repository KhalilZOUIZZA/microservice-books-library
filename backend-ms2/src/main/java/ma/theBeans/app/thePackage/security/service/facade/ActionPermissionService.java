package ma.theBeans.app.thePackage.security.service.facade;

import ma.theBeans.app.thePackage.security.bean.ActionPermission;
import ma.theBeans.app.thePackage.security.dao.criteria.core.ActionPermissionCriteria;
import ma.theBeans.app.thePackage.service.IService;
import java.util.List;


public interface ActionPermissionService extends  IService<ActionPermission,ActionPermissionCriteria>  {

    List<ActionPermission> findAllOptimized();

}
