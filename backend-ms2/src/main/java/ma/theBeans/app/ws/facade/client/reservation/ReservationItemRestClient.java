package ma.theBeans.app.ws.facade.client.reservation;

import io.swagger.v3.oas.annotations.Operation;

import ma.theBeans.app.bean.core.reservation.Reservation;
import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.service.impl.client.reservation.ReservationClientServiceImpl;
import ma.theBeans.app.ws.converter.reservation.ReservationConverter;
import org.springframework.http.HttpStatus;

import ma.theBeans.app.bean.core.reservation.ReservationItem;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationItemCriteria;
import ma.theBeans.app.service.facade.client.reservation.ReservationItemClientService;
import ma.theBeans.app.ws.converter.reservation.ReservationItemConverter;
import ma.theBeans.app.ws.dto.reservation.ReservationItemDto;
import ma.theBeans.app.thePackage.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/client/reservationItem/")
public class ReservationItemRestClient {




    @Operation(summary = "Finds a list of all reservationItems")
    @GetMapping("")
    public ResponseEntity<List<ReservationItemDto>> findAll() throws Exception {
        ResponseEntity<List<ReservationItemDto>> res = null;
        List<ReservationItem> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<ReservationItemDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a reservationItem by id")
    @GetMapping("id/{id}")
    public ResponseEntity<ReservationItemDto> findById(@PathVariable Long id) {
        ReservationItem t = service.findById(id);
        if (t != null) {
            converter.init(true);
            ReservationItemDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }



    @Operation(summary = "Saves the specified  reservationItem")
    @PostMapping("")
    public ResponseEntity<ReservationItemDto> save(@RequestBody ReservationItemDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            ReservationState rstat = new ReservationState();
            long id1 = 2 ;
            rstat.setId(id1);
            rstat.setLabel("InProgress");
            rstat.setCode("InProgress");
            rstat.setStyle("primary");
            ReservationItem myT = converter.toItem(dto);
            Reservation res = converter1.toItem(dto.getReservation());
            res.setReservationState(rstat);

            ReservationItem t = service.create(myT);


            reservationClientServiceImpl.update(res);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                ReservationItemDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  reservationItem")
    @PutMapping("")
    public ResponseEntity<ReservationItemDto> update(@RequestBody ReservationItemDto dto) throws Exception {
        ResponseEntity<ReservationItemDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            ReservationItem t = service.findById(dto.getId());
            converter.copy(dto,t);
            ReservationItem updated = service.update(t);
            ReservationItemDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of reservationItem")
    @PostMapping("multiple")
    public ResponseEntity<List<ReservationItemDto>> delete(@RequestBody List<ReservationItemDto> dtos) throws Exception {
        ResponseEntity<List<ReservationItemDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<ReservationItem> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified reservationItem")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }


    @Operation(summary = "Finds a reservationItem and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<ReservationItemDto> findWithAssociatedLists(@PathVariable Long id) {
        ReservationItem loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        ReservationItemDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds reservationItems by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<ReservationItemDto>> findByCriteria(@RequestBody ReservationItemCriteria criteria) throws Exception {
        ResponseEntity<List<ReservationItemDto>> res = null;
        List<ReservationItem> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<ReservationItemDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated reservationItems by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody ReservationItemCriteria criteria) throws Exception {
        List<ReservationItem> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<ReservationItemDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets reservationItem data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody ReservationItemCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<ReservationItemDto> findDtos(List<ReservationItem> list){
        converter.initObject(true);
        List<ReservationItemDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<ReservationItemDto> getDtoResponseEntity(ReservationItemDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public ReservationItemRestClient(ReservationClientServiceImpl reservationClientServiceImpl,ReservationItemClientService service, ReservationItemConverter converter, ReservationConverter converter1){
        this.service = service;
        this.converter = converter;
       this.converter1 = converter1;
       this.reservationClientServiceImpl = reservationClientServiceImpl;

   }

    private final ReservationItemClientService service;
    private final ReservationConverter converter1;
    private final ReservationItemConverter converter;

    private final ReservationClientServiceImpl reservationClientServiceImpl;



}
