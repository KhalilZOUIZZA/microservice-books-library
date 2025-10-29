package ma.theBeans.app.dao.specification.core.reservation;

import ma.theBeans.app.dao.criteria.core.reservation.ClientCriteria;
import ma.theBeans.app.bean.core.reservation.Client;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


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
