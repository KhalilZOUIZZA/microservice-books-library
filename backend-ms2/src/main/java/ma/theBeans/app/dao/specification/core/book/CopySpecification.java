package ma.theBeans.app.dao.specification.core.book;

import ma.theBeans.app.dao.criteria.core.book.CopyCriteria;
import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class CopySpecification extends  AbstractSpecification<CopyCriteria, Copy>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("serialNumber", criteria.getSerialNumber(),criteria.getSerialNumberLike());
        addPredicateFk("book","id", criteria.getBook()==null?null:criteria.getBook().getId());
        addPredicateFk("book","id", criteria.getBooks());
        addPredicateFk("book","code", criteria.getBook()==null?null:criteria.getBook().getCode());
    }

    public CopySpecification(CopyCriteria criteria) {
        super(criteria);
    }

    public CopySpecification(CopyCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
