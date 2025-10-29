package ma.theBeans.app.ws.facade.admin.book;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.theBeans.app.bean.core.book.Copy;
import ma.theBeans.app.dao.criteria.core.book.CopyCriteria;
import ma.theBeans.app.service.facade.admin.book.CopyAdminService;
import ma.theBeans.app.ws.converter.book.CopyConverter;
import ma.theBeans.app.ws.dto.book.CopyDto;
import ma.theBeans.app.thePackage.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin/copy/")
public class CopyRestAdmin {




    @Operation(summary = "Finds a list of all copys")
    @GetMapping("")
    public ResponseEntity<List<CopyDto>> findAll() throws Exception {
        ResponseEntity<List<CopyDto>> res = null;
        List<Copy> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<CopyDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all copys")
    @GetMapping("optimized")
    public ResponseEntity<List<CopyDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<CopyDto>> res = null;
        List<Copy> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<CopyDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a copy by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CopyDto> findById(@PathVariable Long id) {
        Copy t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CopyDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a copy by serialNumber")
    @GetMapping("serialNumber/{serialNumber}")
    public ResponseEntity<CopyDto> findBySerialNumber(@PathVariable String serialNumber) {
	    Copy t = service.findByReferenceEntity(new Copy(serialNumber));
        if (t != null) {
            converter.init(true);
            CopyDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  copy")
    @PostMapping("")
    public ResponseEntity<CopyDto> save(@RequestBody CopyDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Copy myT = converter.toItem(dto);
            Copy t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CopyDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  copy")
    @PutMapping("")
    public ResponseEntity<CopyDto> update(@RequestBody CopyDto dto) throws Exception {
        ResponseEntity<CopyDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Copy t = service.findById(dto.getId());
            converter.copy(dto,t);
            Copy updated = service.update(t);
            CopyDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of copy")
    @PostMapping("multiple")
    public ResponseEntity<List<CopyDto>> delete(@RequestBody List<CopyDto> dtos) throws Exception {
        ResponseEntity<List<CopyDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Copy> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified copy")
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


    @Operation(summary = "Finds a copy and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CopyDto> findWithAssociatedLists(@PathVariable Long id) {
        Copy loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CopyDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds copys by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CopyDto>> findByCriteria(@RequestBody CopyCriteria criteria) throws Exception {
        ResponseEntity<List<CopyDto>> res = null;
        List<Copy> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<CopyDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated copys by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CopyCriteria criteria) throws Exception {
        List<Copy> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<CopyDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets copy data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CopyCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CopyDto> findDtos(List<Copy> list){
        converter.initObject(true);
        List<CopyDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CopyDto> getDtoResponseEntity(CopyDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public CopyRestAdmin(CopyAdminService service, CopyConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CopyAdminService service;
    private final CopyConverter converter;





}
