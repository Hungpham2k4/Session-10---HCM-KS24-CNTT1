package org.rikkei.session10.repository;

import org.rikkei.session10.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByUserIdAndProductId(
            String userId,
            String productId
    );

    List<CartItem> findByUserId(String userId);
}