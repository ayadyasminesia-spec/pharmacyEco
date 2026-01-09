package com.ayadyasmine.pharmacyecom.repository;

import com.ayadyasmine.pharmacyecom.domain.Promotion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PromotionRepositoryWithBagRelationships {
    Optional<Promotion> fetchBagRelationships(Optional<Promotion> promotion);

    List<Promotion> fetchBagRelationships(List<Promotion> promotions);

    Page<Promotion> fetchBagRelationships(Page<Promotion> promotions);
}
