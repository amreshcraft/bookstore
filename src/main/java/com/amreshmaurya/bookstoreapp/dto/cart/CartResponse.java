package  com.amreshmaurya.bookstoreapp.dto.cart;

// Response DTO

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CartResponse {
    private UUID cartId;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;
    private Integer totalItems;
}