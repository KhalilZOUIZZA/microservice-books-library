package ma.theBeans.app.thePackage.security.dao.specification.core;

import ma.theBeans.app.thePackage.security.bean.Role;
import ma.theBeans.app.thePackage.security.dao.criteria.core.RoleCriteria;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class RoleSpecification extends  AbstractSpecification<RoleCriteria, Role>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("authority", criteria.getAuthority(),criteria.getAuthorityLike());
    }

    public RoleSpecification(RoleCriteria criteria) {
        super(criteria);
    }

    public RoleSpecification(RoleCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
