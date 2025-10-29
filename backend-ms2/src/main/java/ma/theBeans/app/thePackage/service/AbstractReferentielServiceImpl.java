package ma.theBeans.app.thePackage.service;

import ma.theBeans.app.thePackage.bean.BaseEntity;
import ma.theBeans.app.thePackage.criteria.BaseCriteria;
import ma.theBeans.app.thePackage.repository.AbstractRepository;

public abstract class AbstractReferentielServiceImpl<T extends BaseEntity, CRITERIA extends BaseCriteria, REPO extends AbstractRepository<T, Long>> extends AbstractServiceImpl<T, CRITERIA, REPO> {

    public AbstractReferentielServiceImpl(REPO dao) {
        super(dao);
    }

}
