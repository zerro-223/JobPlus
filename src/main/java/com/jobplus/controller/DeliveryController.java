package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    @Autowired private DeliveryService deliveryService;

    @RequireRole(1)
    @PostMapping
    public Result<Delivery> deliver(@RequestParam Integer positionId, @RequestParam Integer resumeId, HttpSession session) {
        User u = (User) session.getAttribute("user");
        Delivery d = deliveryService.deliver(u.getId(), positionId, resumeId);
        return Result.success(d);
    }

    @RequireRole(1)
    @GetMapping
    public Result<List<DeliveryVO>> myDeliveries(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(deliveryService.getMyDeliveries(u.getId()));
    }

    @RequireRole(0)
    @GetMapping("/{id}")
    public Result<DeliveryVO> detail(@PathVariable Integer id) {
        return Result.success(deliveryService.getDeliveryDetail(id));
    }

    @RequireRole(2)
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Integer id, @RequestParam Integer status, HttpSession session) {
        User u = (User) session.getAttribute("user");
        deliveryService.updateStatus(id, status, u);
        return Result.success();
    }
}
