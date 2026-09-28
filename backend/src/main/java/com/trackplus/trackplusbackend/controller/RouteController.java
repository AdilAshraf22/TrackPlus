package com.trackplus.trackplusbackend.controller;

import com.trackplus.trackplusbackend.dto.RouteRequest;
import com.trackplus.trackplusbackend.entity.Route;
import com.trackplus.trackplusbackend.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public Route createRoute(@Valid @RequestBody RouteRequest request) {
        return routeService.createRoute(request);
    }

    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }
}
