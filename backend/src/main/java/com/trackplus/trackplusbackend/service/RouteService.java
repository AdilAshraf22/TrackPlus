package com.trackplus.trackplusbackend.service;

import com.trackplus.trackplusbackend.dto.RouteRequest;
import com.trackplus.trackplusbackend.entity.Route;
import com.trackplus.trackplusbackend.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route createRoute(RouteRequest request) {

        Route route = new Route();

        route.setOrigin(request.getOrigin());
        route.setDestination(request.getDestination());
        route.setDistanceKm(request.getDistanceKm());
        route.setAvgSpeedKmph(request.getAvgSpeedKmph());

        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
}