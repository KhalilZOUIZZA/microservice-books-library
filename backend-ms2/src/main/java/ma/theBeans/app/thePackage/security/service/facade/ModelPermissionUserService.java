package ma.theBeans.app.thePackage.security.service.facade;

import ma.theBeans.app.thePackage.security.bean.ModelPermissionUser;
import ma.theBeans.app.thePackage.security.dao.criteria.core.ModelPermissionUserCriteria;
import ma.theBeans.app.thePackage.service.IService;

import java.util.List;



public interface ModelPermissionUserService extends  IService<ModelPermissionUser,ModelPermissionUserCriteria>  {

    List<ModelPermissionUser> initModelPermissionUser();
    List<ModelPermissionUser> initSecurityModelPermissionUser();
    List<ModelPermissionUser> findByActionPermissionId(Long id);
    int deleteByActionPermissionId(Long id);
    long countByActionPermissionReference(String reference);
    List<ModelPermissionUser> findByModelPermissionId(Long id);
    int deleteByModelPermissionId(Long id);
    long countByModelPermissionReference(String reference);
    List<ModelPermissionUser> findByUserId(Long id);
    Boolean findByUserUsernameAndModelPermissionReferenceAndActionPermissionReference( String username ,  String modelReference,  String actionReference);
    int deleteByUserId(Long id);
    long countByUserEmail(String email);

    List<ModelPermissionUser> findByUserUsername(String username);

}
