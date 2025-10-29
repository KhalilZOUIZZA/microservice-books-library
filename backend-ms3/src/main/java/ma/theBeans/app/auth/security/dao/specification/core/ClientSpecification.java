package ma.theBeans.app.auth.security.dao.specification.core;

import ma.theBeans.app.auth.bean.Client;
import ma.theBeans.app.auth.security.dao.criteria.core.ClientCriteria;
import ma.theBeans.app.auth.specification.AbstractSpecification;


public class ClientSpecification extends  AbstractSpecification<ClientCriteria, Client>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("description", criteria.getDescription(),criteria.getDescriptionLike());
        addPredicateBool("passwordChanged", criteria.getPasswordChanged());
        addPredicate("username", criteria.getUsername(),criteria.getUsernameLike());
        addPredicateBool("accountNonExpired", criteria.getAccountNonExpired());
        addPredicateBool("credentialsNonExpired", criteria.getCredentialsNonExpired());
        addPredicateBool("enabled", criteria.getEnabled());
        addPredicate("email", criteria.getEmail(),criteria.getEmailLike());
        addPredicate("password", criteria.getPassword(),criteria.getPasswordLike());
        addPredicateBool("accountNonLocked", criteria.getAccountNonLocked());
    }

    public ClientSpecification(ClientCriteria criteria) {
        super(criteria);
    }

    public ClientSpecification(ClientCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}