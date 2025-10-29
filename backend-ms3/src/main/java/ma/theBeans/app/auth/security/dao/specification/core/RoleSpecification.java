package ma.theBeans.app.auth.security.dao.specification.core;

import ma.theBeans.app.auth.security.bean.Role;
import ma.theBeans.app.auth.security.dao.criteria.core.RoleCriteria;
import ma.theBeans.app.auth.specification.AbstractSpecification;


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
