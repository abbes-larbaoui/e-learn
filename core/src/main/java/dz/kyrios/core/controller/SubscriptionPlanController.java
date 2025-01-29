package dz.kyrios.core.controller;

import dz.kyrios.core.config.filter.clause.Clause;
import dz.kyrios.core.config.filter.clause.ClauseOneArg;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.Critiria;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.SearchValue;
import dz.kyrios.core.config.filter.handlerMethodArgumentResolver.SortParam;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanRequest;
import dz.kyrios.core.dto.subscriptionplan.SubscriptionPlanResponse;
import dz.kyrios.core.service.SubscriptionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    @Autowired
    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @GetMapping
    @PreAuthorize("@authz.hasCustomAuthority('SUBSCRIPTION_PLAN_LIST')")
    public ResponseEntity<Object> getAllLazy(@SortParam PageRequest pageRequest,
                                             @Critiria List<Clause> filter,
                                             @SearchValue ClauseOneArg searchValue) {

        filter.add(searchValue);
        return new ResponseEntity<>(subscriptionPlanService.findAllFilter(pageRequest, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('SUBSCRIPTION_PLAN_VIEW')")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {
        SubscriptionPlanResponse response = subscriptionPlanService.getOne(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("@authz.hasCustomAuthority('SUBSCRIPTION_PLAN_CREATE')")
    public ResponseEntity<Object> create(@RequestBody SubscriptionPlanRequest request) {
        SubscriptionPlanResponse response = subscriptionPlanService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('SUBSCRIPTION_PLAN_UPDATE')")
    public ResponseEntity<Object> update(@RequestBody SubscriptionPlanRequest request,
                                         @PathVariable Long id) {
        SubscriptionPlanResponse response = subscriptionPlanService.update(request, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.hasCustomAuthority('SUBSCRIPTION_PLAN_DELETE')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        subscriptionPlanService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
