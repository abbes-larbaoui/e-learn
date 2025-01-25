package dz.kyrios.core.controller;

import dz.kyrios.core.config.filter.clause.Clause;
import dz.kyrios.core.config.filter.clause.ClauseOneArg;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.core.dto.subject.SubjectRequest;
import dz.kyrios.core.dto.subject.SubjectResponse;
import dz.kyrios.core.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    @PreAuthorize("@authz.hasCustomAuthority('SUBJECT_LIST')")
    public ResponseEntity<Object> getAllLazy(@SortParam PageRequest pageRequest,
                                             @Critiria List<Clause> filter,
                                             @SearchValue ClauseOneArg searchValue) {

        filter.add(searchValue);
        return new ResponseEntity<>(subjectService.findAllFilter(pageRequest, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('SUBJECT_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        SubjectResponse response = subjectService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("@authz.hasCustomAuthority('SUBJECT_CREATE')")
    public ResponseEntity<Object> create(@RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('SUBJECT_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody SubjectRequest request,
                                         @PathVariable Long id) {
        SubjectResponse response = subjectService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('SUBJECT_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
