package ma.theBeans.app.dao.specification.core.book;

import ma.theBeans.app.dao.criteria.core.book.BookCriteria;
import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class BookSpecification extends  AbstractSpecification<BookCriteria, Book>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("title", criteria.getTitle(),criteria.getTitleLike());
        addPredicate("editionDate", criteria.getEditionDate(), criteria.getEditionDateFrom(), criteria.getEditionDateTo());
        addPredicateInt("numberOfCopies", criteria.getNumberOfCopies(), criteria.getNumberOfCopiesMin(), criteria.getNumberOfCopiesMax());
        addPredicateBool("available", criteria.getAvailable());
        addPredicate("imageUrl", criteria.getImageUrl(),criteria.getImageUrlLike());
        addPredicateFk("category","id", criteria.getCategory()==null?null:criteria.getCategory().getId());
        addPredicateFk("category","id", criteria.getCategorys());
        addPredicateFk("category","code", criteria.getCategory()==null?null:criteria.getCategory().getCode());
        addPredicateFk("author","id", criteria.getAuthor()==null?null:criteria.getAuthor().getId());
        addPredicateFk("author","id", criteria.getAuthors());
        addPredicateFk("author","code", criteria.getAuthor()==null?null:criteria.getAuthor().getCode());
    }

    public BookSpecification(BookCriteria criteria) {
        super(criteria);
    }

    public BookSpecification(BookCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
