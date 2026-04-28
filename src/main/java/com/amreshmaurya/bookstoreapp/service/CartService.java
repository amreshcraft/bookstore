package com.amreshmaurya.bookstoreapp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amreshmaurya.bookstoreapp.dao.BookDAO;
import com.amreshmaurya.bookstoreapp.dao.CartDAO;
import com.amreshmaurya.bookstoreapp.dao.CartItemDAO;
import com.amreshmaurya.bookstoreapp.dto.cart.CartDTO;
import com.amreshmaurya.bookstoreapp.entity.Book;
import com.amreshmaurya.bookstoreapp.entity.Cart;
import com.amreshmaurya.bookstoreapp.entity.CartItem;
import com.amreshmaurya.bookstoreapp.entity.User;
import com.amreshmaurya.bookstoreapp.mapper.CartMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartDAO cartDAO;
    private final CartItemDAO cartItemDAO;
    private final BookDAO bookDAO;
    private final CartMapper cartMapper;

    // Get Cart
    public CartDTO getCart(UUID userId) {
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toDTO(cart);
    }

    // Add to Cart
    @Transactional
    public CartDTO addToCart(UUID userId, UUID bookId, int qty) {

        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        Cart cart = getOrCreateCart(userId);

        Book book = bookDAO.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        CartItem cartItem = cartItemDAO
                .findByCartIdAndBookId(cart.getId(), bookId)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + qty);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setBook(book);
            cartItem.setQuantity(qty);
            cartItem.setPriceAtAdded(book.getPrice());
        }

        cartItem.setTotalPrice(
                cartItem.getPriceAtAdded()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        cartItemDAO.save(cartItem);

        updateCartTotal(cart);

        return cartMapper.toDTO(cart);
    }

    // Update Quantity
    @Transactional
    public CartDTO updateQuantity(UUID cartItemId, int qty) {

        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        CartItem item = cartItemDAO.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(qty);
        item.setTotalPrice(
                item.getPriceAtAdded().multiply(BigDecimal.valueOf(qty)));

        cartItemDAO.save(item);

        updateCartTotal(item.getCart());

        return cartMapper.toDTO(item.getCart());
    }

    // Remove Item
    @Transactional
    public void removeItem(UUID cartItemId) {

        CartItem item = cartItemDAO.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();

        cartItemDAO.delete(item);

        updateCartTotal(cart);
    }

    // Clear Cart
    @Transactional
    public void clearCart(UUID userId) {

        Cart cart = getOrCreateCart(userId);

        List<CartItem> items = cartItemDAO.findByCartId(cart.getId());

        cartItemDAO.deleteAll(items);

        cart.setTotalAmount(BigDecimal.ZERO);

        cartDAO.save(cart);
    }

    // ================== INTERNAL METHODS ==================

    private Cart getOrCreateCart(UUID userId) {

        return cartDAO.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    User user = new User();
                    user.setId(userId);
                    cart.setUser(user);
                    return cartDAO.save(cart);
                });
    }

    private void updateCartTotal(Cart cart) {

        List<CartItem> items = cartItemDAO.findByCartId(cart.getId());

        BigDecimal total = items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(total);

        cartDAO.save(cart);
    }
}
