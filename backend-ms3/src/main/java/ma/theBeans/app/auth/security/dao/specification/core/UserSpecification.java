package ma.theBeans.app.auth.security.dao.specification.core;

import ma.theBeans.app.auth.security.bean.User;
import ma.theBeans.app.auth.security.dao.criteria.core.UserCriteria;
import ma.theBeans.app.auth.specification.AbstractSpecification;


public class UserSpecification extends  AbstractSpecification<UserCriteria, User>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBool("credentialsNonExpired", criteria.getCredentialsNonExpired());
        addPredicateBool("enabled", criteria.getEnabled());
        addPredicate("email", criteria.getEmail(),criteria.getEmailLike());
        addPredicateBool("accountNonExpired", criteria.getAccountNonExpired());
        addPredicateBool("accountNonLocked", criteria.getAccountNonLocked());
        addPredicate("username", criteria.getUsername(),criteria.getUsernameLike());
        addPredicate("firstNme", criteria.getFirstName(),criteria.getFirstNameLike());
        addPredicate("lastName", criteria.getLastName(),criteria.getLastNameLike());
        addPredicate("phone", criteria.getPhone(),criteria.getPhoneLike());
        addPredicate("password", criteria.getPassword(),criteria.getPasswordLike());
        addPredicateBool("passwordChanged", criteria.getPasswordChanged());
    }

    public UserSpecification(UserCriteria criteria) {
        super(criteria);
    }

    public UserSpecification(UserCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
