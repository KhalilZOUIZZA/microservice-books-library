package ma.theBeans.app.ws.facade.client.book;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.theBeans.app.bean.core.book.Book;
import ma.theBeans.app.dao.criteria.core.book.BookCriteria;
import ma.theBeans.app.service.facade.client.book.BookClientService;
import ma.theBeans.app.ws.converter.book.BookConverter;
import ma.theBeans.app.ws.dto.book.BookDto;
import ma.theBeans.app.thePackage.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/client/book/")
public class BookRestClient {




    @Operation(summary = "Finds a list of all books")
    @GetMapping("")
    public ResponseEntity<List<BookDto>> findAll() throws Exception {
        ResponseEntity<List<BookDto>> res = null;
        List<Book> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
            converter.initObject(true);
        List<BookDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all books")
    @GetMapping("optimized")
    public ResponseEntity<List<BookDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<BookDto>> res = null;
        List<Book> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<BookDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a book by id")
    @GetMapping("id/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        Book t = service.findById(id);
        if (t != null) {
            converter.init(true);
            BookDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a book by code")
    @GetMapping("code/{code}")
    public ResponseEntity<BookDto> findByCode(@PathVariable String code) {
	    Book t = service.findByReferenceEntity(new Book(code));
        if (t != null) {
            converter.init(true);
            BookDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  book")
    @PostMapping("")
    public ResponseEntity<BookDto> save(@RequestBody BookDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Book myT = converter.toItem(dto);
            Book t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                BookDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  book")
    @PutMapping("")
    public ResponseEntity<BookDto> update(@RequestBody BookDto dto) throws Exception {
        ResponseEntity<BookDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Book t = service.findById(dto.getId());
            converter.copy(dto,t);
            Book updated = service.update(t);
            BookDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of book")
    @PostMapping("multiple")
    public ResponseEntity<List<BookDto>> delete(@RequestBody List<BookDto> dtos) throws Exception {
        ResponseEntity<List<BookDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Book> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified book")
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


    @Operation(summary = "Finds a book and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<BookDto> findWithAssociatedLists(@PathVariable Long id) {
        Book loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        BookDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds books by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<BookDto>> findByCriteria(@RequestBody BookCriteria criteria) throws Exception {
        ResponseEntity<List<BookDto>> res = null;
        List<Book> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<BookDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated books by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody BookCriteria criteria) throws Exception {
        List<Book> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        converter.initObject(true);
        List<BookDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets book data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody BookCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<BookDto> findDtos(List<Book> list){
        converter.initList(false);
        converter.initObject(true);
        List<BookDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<BookDto> getDtoResponseEntity(BookDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public BookRestClient(BookClientService service, BookConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final BookClientService service;
    private final BookConverter converter;





}
