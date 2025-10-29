package ma.theBeans.app.dao.specification.core.reservation;

import ma.theBeans.app.dao.criteria.core.reservation.ReservationCriteria;
import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class ReservationSpecification extends  AbstractSpecification<ReservationCriteria, Reservation>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("requestDate", criteria.getRequestDate(), criteria.getRequestDateFrom(), criteria.getRequestDateTo());
        addPredicate("theoreticalReturnDate", criteria.getTheoreticalReturnDate(), criteria.getTheoreticalReturnDateFrom(), criteria.getTheoreticalReturnDateTo());
        addPredicate("effectiveReturnDate", criteria.getEffectiveReturnDate(), criteria.getEffectiveReturnDateFrom(), criteria.getEffectiveReturnDateTo());
        addPredicateFk("reservationState","id", criteria.getReservationState()==null?null:criteria.getReservationState().getId());
        addPredicateFk("reservationState","id", criteria.getReservationStates());
        addPredicateFk("reservationState","code", criteria.getReservationState()==null?null:criteria.getReservationState().getCode());
        addPredicateFk("client","id", criteria.getClient()==null?null:criteria.getClient().getId());
        addPredicateFk("client","id", criteria.getClients());
        addPredicateFk("client","email", criteria.getClient()==null?null:criteria.getClient().getEmail());
    }

    public ReservationSpecification(ReservationCriteria criteria) {
        super(criteria);
    }

    public ReservationSpecification(ReservationCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
