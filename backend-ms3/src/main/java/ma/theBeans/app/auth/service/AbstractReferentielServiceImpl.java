package ma.theBeans.app.auth.service;

import ma.theBeans.app.auth.bean.BaseEntity;
import ma.theBeans.app.auth.criteria.BaseCriteria;
import ma.theBeans.app.auth.repository.AbstractRepository;

public abstract class AbstractReferentielServiceImpl<T extends BaseEntity, CRITERIA extends BaseCriteria, REPO extends AbstractRepository<T, Long>> extends AbstractServiceImpl<T, CRITERIA, REPO> {

    public AbstractReferentielServiceImpl(REPO dao) {
        super(dao);
    }

}
