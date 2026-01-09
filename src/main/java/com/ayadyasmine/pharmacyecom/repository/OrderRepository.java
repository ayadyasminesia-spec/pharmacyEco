package com.ayadyasmine.pharmacyecom.repository;

import com.ayadyasmine.pharmacyecom.domain.Order;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByCustomerUserLoginOrderByOrderDateDesc(String login);
}
