package ma.theBeans.app.dao.specification.core.book;

import ma.theBeans.app.dao.criteria.core.book.CategoryCriteria;
import ma.theBeans.app.bean.core.book.Category;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class CategorySpecification extends  AbstractSpecification<CategoryCriteria, Category>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
    }

    public CategorySpecification(CategoryCriteria criteria) {
        super(criteria);
    }

    public CategorySpecification(CategoryCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
