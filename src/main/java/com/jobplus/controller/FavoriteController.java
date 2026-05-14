package com.jobplus.controller;

import com.jobplus.common.dto.*;
import com.jobplus.entity.*;
import com.jobplus.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import com.jobplus.common.annotation.RequireRole;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {
    @Autowired private FavoriteService favoriteService;

    @RequireRole(1)
    @PostMapping
    public Result<Void> add(@RequestParam Integer positionId, HttpSession session) {
        User u = (User) session.getAttribute("user");
        favoriteService.addFavorite(u.getId(), positionId);
        return Result.success();
    }

    @RequireRole(1)
    @DeleteMapping("/{positionId}")
    public Result<Void> remove(@PathVariable Integer positionId, HttpSession session) {
        User u = (User) session.getAttribute("user");
        favoriteService.removeFavorite(u.getId(), positionId);
        return Result.success();
    }

    @RequireRole(1)
    @GetMapping
    public Result<List<PositionVO>> myFavorites(HttpSession session) {
        User u = (User) session.getAttribute("user");
        return Result.success(favoriteService.getMyFavorites(u.getId()));
    }
}
