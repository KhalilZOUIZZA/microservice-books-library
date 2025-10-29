package ma.theBeans.app.dao.specification.core.book;

import ma.theBeans.app.dao.criteria.core.book.AuthorCriteria;
import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class AuthorSpecification extends  AbstractSpecification<AuthorCriteria, Author>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("fullName", criteria.getFullName(),criteria.getFullNameLike());
    }

    public AuthorSpecification(AuthorCriteria criteria) {
        super(criteria);
    }

    public AuthorSpecification(AuthorCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
