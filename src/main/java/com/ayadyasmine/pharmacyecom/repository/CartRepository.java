package com.ayadyasmine.pharmacyecom.repository;

import com.ayadyasmine.pharmacyecom.domain.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Trouve le panier lié au login de l'utilisateur qui est actuellement actif
    Optional<Cart> findByCustomerUserLoginAndActiveTrue(String login);
}
