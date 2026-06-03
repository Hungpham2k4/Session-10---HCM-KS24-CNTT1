package org.rikkei.session10.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rikkei.session10.dto.request.AddCartRequest;
import org.rikkei.session10.entity.CartItem;
import org.rikkei.session10.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @Valid @RequestBody AddCartRequest request
    ) {

        log.info(
                "Received add cart request from user={}",
                request.getUserId()
        );

        CartItem item = cartService.addToCart(request);

        return ResponseEntity.ok(item);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(
            @PathVariable String userId
    ) {

        log.info(
                "Get cart request userId={}",
                userId
        );

        return ResponseEntity.ok(
                cartService.getCartByUser(userId)
        );
    }
}