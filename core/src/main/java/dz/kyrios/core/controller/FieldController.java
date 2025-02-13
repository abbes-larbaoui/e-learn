package dz.kyrios.core.controller;

import dz.kyrios.core.config.filter.clause.Clause;
import dz.kyrios.core.config.filter.clause.ClauseOneArg;
import dz.kyrios.core.config.filter.enums.Operation;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.core.dto.field.FieldRequest;
import dz.kyrios.core.dto.field.FieldResponse;
import dz.kyrios.core.service.FieldService;
import dz.kyrios.core.statics.GeneralStatus;
import dz.kyrios.core.statics.SubscriptionPlanStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FieldController {

    private final FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/api/v1/fields")
    @PreAuthorize("@authz.hasCustomAuthority('FIELD_LIST')")
    public ResponseEntity<Object> getAllLazy(@SortParam PageRequest pageRequest,
                                             @Critiria List<Clause> filter,
                                             @SearchValue ClauseOneArg searchValue) {

        filter.add(searchValue);
        return new ResponseEntity<>(fieldService.findAllFilter(pageRequest, filter), HttpStatus.OK);
    }

    @GetMapping("/api/v1/fields/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('FIELD_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        FieldResponse response = fieldService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/v1/fields")
    @PreAuthorize("@authz.hasCustomAuthority('FIELD_CREATE')")
    public ResponseEntity<Object> create(@RequestBody FieldRequest request) {
        FieldResponse response = fieldService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/fields/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('FIELD_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody FieldRequest request,
                                         @PathVariable Long id) {
        FieldResponse response = fieldService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/fields/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('FIELD_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        fieldService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/v1/public/fields")
    public ResponseEntity<Object> getPublicFields(@SortParam PageRequest pageRequest,
                                                  @Critiria List<Clause> filter,
                                                  @SearchValue ClauseOneArg searchValue) {
        ClauseOneArg clause = new ClauseOneArg("status", Operation.Equals, GeneralStatus.ACTIVE.name());
        filter.add(clause);
        filter.add(searchValue);
        return new ResponseEntity<>(fieldService.findAllFilter(pageRequest, filter), HttpStatus.OK);
    }
}
