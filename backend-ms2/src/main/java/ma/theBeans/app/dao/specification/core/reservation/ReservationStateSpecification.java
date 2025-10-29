package ma.theBeans.app.dao.specification.core.reservation;

import ma.theBeans.app.dao.criteria.core.reservation.ReservationStateCriteria;
import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class ReservationStateSpecification extends  AbstractSpecification<ReservationStateCriteria, ReservationState>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("label", criteria.getLabel(),criteria.getLabelLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public ReservationStateSpecification(ReservationStateCriteria criteria) {
        super(criteria);
    }

    public ReservationStateSpecification(ReservationStateCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
