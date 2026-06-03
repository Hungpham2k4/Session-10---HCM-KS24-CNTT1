package org.rikkei.session10.service;

import org.rikkei.session10.dto.request.AddCartRequest;
import org.rikkei.session10.entity.CartItem;

import java.util.List;

public interface CartService {

    CartItem addToCart(AddCartRequest request);

    List<CartItem> getCartByUser(String userId);

    CartItem findById(Long id);

    CartItem create(CartItem item);

    CartItem update(Long id, CartItem item);

    void delete(Long id);
}