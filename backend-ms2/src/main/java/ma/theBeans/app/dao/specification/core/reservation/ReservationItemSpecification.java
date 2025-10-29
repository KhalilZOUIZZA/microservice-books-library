package ma.theBeans.app.dao.specification.core.reservation;

import ma.theBeans.app.dao.criteria.core.reservation.ReservationItemCriteria;
import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.thePackage.specification.AbstractSpecification;


public class ReservationItemSpecification extends  AbstractSpecification<ReservationItemCriteria, ReservationItem>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("returnDate", criteria.getReturnDate(), criteria.getReturnDateFrom(), criteria.getReturnDateTo());
        addPredicateFk("copy","id", criteria.getCopy()==null?null:criteria.getCopy().getId());
        addPredicateFk("copy","id", criteria.getCopys());
        addPredicateFk("copy","serialNumber", criteria.getCopy()==null?null:criteria.getCopy().getSerialNumber());
        addPredicateFk("reservation","id", criteria.getReservation()==null?null:criteria.getReservation().getId());
        addPredicateFk("reservation","id", criteria.getReservations());
        addPredicateFk("reservation","code", criteria.getReservation()==null?null:criteria.getReservation().getCode());
    }

    public ReservationItemSpecification(ReservationItemCriteria criteria) {
        super(criteria);
    }

    public ReservationItemSpecification(ReservationItemCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
