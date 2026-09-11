package com.ecommerce.order.dto;

import com.ecommerce.order.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {

    @NotNull
    private Order.OrderStatus status;
}