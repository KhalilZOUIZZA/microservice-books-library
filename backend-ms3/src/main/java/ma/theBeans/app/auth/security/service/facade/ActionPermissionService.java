package ma.theBeans.app.auth.security.service.facade;

import ma.theBeans.app.auth.security.bean.ActionPermission;
import ma.theBeans.app.auth.security.dao.criteria.core.ActionPermissionCriteria;
import ma.theBeans.app.auth.service.IService;
import java.util.List;


public interface ActionPermissionService extends  IService<ActionPermission,ActionPermissionCriteria>  {

    List<ActionPermission> findAllOptimized();

}
