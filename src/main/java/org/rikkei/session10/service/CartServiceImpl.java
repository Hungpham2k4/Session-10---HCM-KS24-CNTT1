package org.rikkei.session10.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rikkei.session10.dto.request.AddCartRequest;
import org.rikkei.session10.entity.CartItem;
import org.rikkei.session10.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemRepository repository;

    @Override
    public CartItem addToCart(AddCartRequest request) {

        log.info(
                "Add cart request: userId={}, productId={}, quantity={}",
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );

        Optional<CartItem> existingItem =
                repository.findByUserIdAndProductId(
                        request.getUserId(),
                        request.getProductId()
                );

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            item.setQuantity(
                    item.getQuantity() + request.getQuantity()
            );

            CartItem saved = repository.save(item);

            log.info(
                    "Quantity updated. productId={}, newQuantity={}",
                    saved.getProductId(),
                    saved.getQuantity()
            );

            return saved;
        }

        CartItem newItem = CartItem.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();

        CartItem saved = repository.save(newItem);

        log.info(
                "New cart item created. id={}",
                saved.getId()
        );

        return saved;
    }

    @Override
    public List<CartItem> getCartByUser(String userId) {

        log.info("Get cart of user={}", userId);

        return repository.findByUserId(userId);
    }

    @Override
    public CartItem findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Cart item not found")
                );
    }

    @Override
    public CartItem create(CartItem item) {
        return repository.save(item);
    }

    @Override
    public CartItem update(Long id, CartItem item) {

        CartItem existing = findById(id);

        existing.setUserId(item.getUserId());
        existing.setProductId(item.getProductId());
        existing.setQuantity(item.getQuantity());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {

        CartItem item = findById(id);

        repository.delete(item);
    }
}