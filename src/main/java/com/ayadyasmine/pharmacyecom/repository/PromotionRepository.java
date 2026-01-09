package com.ayadyasmine.pharmacyecom.repository;

import com.ayadyasmine.pharmacyecom.domain.Promotion;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Promotion entity.
 *
 * When extending this class, extend PromotionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PromotionRepository extends PromotionRepositoryWithBagRelationships, JpaRepository<Promotion, Long> {
    default Optional<Promotion> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Promotion> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Promotion> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query("SELECT p FROM Promotion p JOIN p.products prod " +
           "WHERE prod.id = :productId " +
           "AND p.active = true " +
           "AND p.startDate <= :now " +
           "AND p.endDate >= :now")
    List<Promotion> findActivePromotionsForProduct(@Param("productId") Long productId, @Param("now") Instant now);
}
