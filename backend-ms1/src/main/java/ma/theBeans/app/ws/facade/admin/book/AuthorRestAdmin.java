package ma.theBeans.app.ws.facade.admin.book;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.theBeans.app.bean.core.book.Author;
import ma.theBeans.app.dao.criteria.core.book.AuthorCriteria;
import ma.theBeans.app.service.facade.admin.book.AuthorAdminService;
import ma.theBeans.app.ws.converter.book.AuthorConverter;
import ma.theBeans.app.ws.dto.book.AuthorDto;
import ma.theBeans.app.thePackage.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin/author/")
public class AuthorRestAdmin {




    @Operation(summary = "Finds a list of all authors")
    @GetMapping("")
    public ResponseEntity<List<AuthorDto>> findAll() throws Exception {
        ResponseEntity<List<AuthorDto>> res = null;
        List<Author> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<AuthorDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all authors")
    @GetMapping("optimized")
    public ResponseEntity<List<AuthorDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<AuthorDto>> res = null;
        List<Author> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<AuthorDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a author by id")
    @GetMapping("id/{id}")
    public ResponseEntity<AuthorDto> findById(@PathVariable Long id) {
        Author t = service.findById(id);
        if (t != null) {
            AuthorDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a author by fullName")
    @GetMapping("fullName/{fullName}")
    public ResponseEntity<AuthorDto> findByFullName(@PathVariable String fullName) {
	    Author t = service.findByReferenceEntity(new Author(fullName));
        if (t != null) {
            AuthorDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  author")
    @PostMapping("")
    public ResponseEntity<AuthorDto> save(@RequestBody AuthorDto dto) throws Exception {
        if(dto!=null){
            Author myT = converter.toItem(dto);
            Author t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                AuthorDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  author")
    @PutMapping("")
    public ResponseEntity<AuthorDto> update(@RequestBody AuthorDto dto) throws Exception {
        ResponseEntity<AuthorDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Author t = service.findById(dto.getId());
            converter.copy(dto,t);
            Author updated = service.update(t);
            AuthorDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of author")
    @PostMapping("multiple")
    public ResponseEntity<List<AuthorDto>> delete(@RequestBody List<AuthorDto> dtos) throws Exception {
        ResponseEntity<List<AuthorDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<Author> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified author")
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


    @Operation(summary = "Finds a author and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<AuthorDto> findWithAssociatedLists(@PathVariable Long id) {
        Author loaded =  service.findWithAssociatedLists(id);
        AuthorDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds authors by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<AuthorDto>> findByCriteria(@RequestBody AuthorCriteria criteria) throws Exception {
        ResponseEntity<List<AuthorDto>> res = null;
        List<Author> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<AuthorDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated authors by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody AuthorCriteria criteria) throws Exception {
        List<Author> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<AuthorDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets author data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody AuthorCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<AuthorDto> findDtos(List<Author> list){
        List<AuthorDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<AuthorDto> getDtoResponseEntity(AuthorDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public AuthorRestAdmin(AuthorAdminService service, AuthorConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final AuthorAdminService service;
    private final AuthorConverter converter;





}
