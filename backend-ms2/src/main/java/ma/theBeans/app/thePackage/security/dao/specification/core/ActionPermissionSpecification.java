package ma.theBeans.app.thePackage.security.dao.specification.core;

import ma.theBeans.app.thePackage.security.bean.ActionPermission;
import ma.theBeans.app.thePackage.security.dao.criteria.core.ActionPermissionCriteria;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class ActionPermissionSpecification extends  AbstractSpecification<ActionPermissionCriteria, ActionPermission>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("reference", criteria.getReference(),criteria.getReferenceLike());
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
    }

    public ActionPermissionSpecification(ActionPermissionCriteria criteria) {
        super(criteria);
    }

    public ActionPermissionSpecification(ActionPermissionCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
