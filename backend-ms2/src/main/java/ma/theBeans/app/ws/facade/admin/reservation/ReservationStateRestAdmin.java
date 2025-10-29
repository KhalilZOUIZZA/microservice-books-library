package ma.theBeans.app.ws.facade.admin.reservation;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.theBeans.app.bean.core.reservation.ReservationState;
import ma.theBeans.app.dao.criteria.core.reservation.ReservationStateCriteria;
import ma.theBeans.app.service.facade.admin.reservation.ReservationStateAdminService;
import ma.theBeans.app.ws.converter.reservation.ReservationStateConverter;
import ma.theBeans.app.ws.dto.reservation.ReservationStateDto;
import ma.theBeans.app.thePackage.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin/reservationState/")
public class ReservationStateRestAdmin {




    @Operation(summary = "Finds a list of all reservationStates")
    @GetMapping("")
    public ResponseEntity<List<ReservationStateDto>> findAll() throws Exception {
        ResponseEntity<List<ReservationStateDto>> res = null;
        List<ReservationState> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ReservationStateDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all reservationStates")
    @GetMapping("optimized")
    public ResponseEntity<List<ReservationStateDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<ReservationStateDto>> res = null;
        List<ReservationState> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ReservationStateDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a reservationState by id")
    @GetMapping("id/{id}")
    public ResponseEntity<ReservationStateDto> findById(@PathVariable Long id) {
        ReservationState t = service.findById(id);
        if (t != null) {
            ReservationStateDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a reservationState by label")
    @GetMapping("label/{label}")
    public ResponseEntity<ReservationStateDto> findByLabel(@PathVariable String label) {
	    ReservationState t = service.findByReferenceEntity(new ReservationState(label));
        if (t != null) {
            ReservationStateDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  reservationState")
    @PostMapping("")
    public ResponseEntity<ReservationStateDto> save(@RequestBody ReservationStateDto dto) throws Exception {
        if(dto!=null){
            ReservationState myT = converter.toItem(dto);
            ReservationState t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                ReservationStateDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  reservationState")
    @PutMapping("")
    public ResponseEntity<ReservationStateDto> update(@RequestBody ReservationStateDto dto) throws Exception {
        ResponseEntity<ReservationStateDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            ReservationState t = service.findById(dto.getId());
            converter.copy(dto,t);
            ReservationState updated = service.update(t);
            ReservationStateDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of reservationState")
    @PostMapping("multiple")
    public ResponseEntity<List<ReservationStateDto>> delete(@RequestBody List<ReservationStateDto> dtos) throws Exception {
        ResponseEntity<List<ReservationStateDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<ReservationState> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified reservationState")
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


    @Operation(summary = "Finds a reservationState and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<ReservationStateDto> findWithAssociatedLists(@PathVariable Long id) {
        ReservationState loaded =  service.findWithAssociatedLists(id);
        ReservationStateDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds reservationStates by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<ReservationStateDto>> findByCriteria(@RequestBody ReservationStateCriteria criteria) throws Exception {
        ResponseEntity<List<ReservationStateDto>> res = null;
        List<ReservationState> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<ReservationStateDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated reservationStates by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody ReservationStateCriteria criteria) throws Exception {
        List<ReservationState> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<ReservationStateDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets reservationState data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody ReservationStateCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<ReservationStateDto> findDtos(List<ReservationState> list){
        List<ReservationStateDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<ReservationStateDto> getDtoResponseEntity(ReservationStateDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public ReservationStateRestAdmin(ReservationStateAdminService service, ReservationStateConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final ReservationStateAdminService service;
    private final ReservationStateConverter converter;





}
