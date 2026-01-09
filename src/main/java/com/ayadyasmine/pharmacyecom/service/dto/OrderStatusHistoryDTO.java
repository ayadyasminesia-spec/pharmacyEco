package com.ayadyasmine.pharmacyecom.service.dto;

import com.ayadyasmine.pharmacyecom.domain.enumeration.OrderStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.ayadyasmine.pharmacyecom.domain.OrderStatusHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderStatusHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private OrderStatus status;

    @NotNull
    private Instant changedAt;

    private String notes;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderStatusHistoryDTO)) {
            return false;
        }

        OrderStatusHistoryDTO orderStatusHistoryDTO = (OrderStatusHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderStatusHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderStatusHistoryDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", changedAt='" + getChangedAt() + "'" +
            ", notes='" + getNotes() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
