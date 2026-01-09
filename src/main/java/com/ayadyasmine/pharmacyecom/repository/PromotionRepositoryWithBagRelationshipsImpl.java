package com.ayadyasmine.pharmacyecom.repository;

import com.ayadyasmine.pharmacyecom.domain.Promotion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PromotionRepositoryWithBagRelationshipsImpl implements PromotionRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PROMOTIONS_PARAMETER = "promotions";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Promotion> fetchBagRelationships(Optional<Promotion> promotion) {
        return promotion.map(this::fetchProducts);
    }

    @Override
    public Page<Promotion> fetchBagRelationships(Page<Promotion> promotions) {
        return new PageImpl<>(fetchBagRelationships(promotions.getContent()), promotions.getPageable(), promotions.getTotalElements());
    }

    @Override
    public List<Promotion> fetchBagRelationships(List<Promotion> promotions) {
        return Optional.of(promotions).map(this::fetchProducts).orElse(Collections.emptyList());
    }

    Promotion fetchProducts(Promotion result) {
        return entityManager
            .createQuery(
                "select promotion from Promotion promotion left join fetch promotion.products where promotion.id = :id",
                Promotion.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Promotion> fetchProducts(List<Promotion> promotions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, promotions.size()).forEach(index -> order.put(promotions.get(index).getId(), index));
        List<Promotion> result = entityManager
            .createQuery(
                "select promotion from Promotion promotion left join fetch promotion.products where promotion in :promotions",
                Promotion.class
            )
            .setParameter(PROMOTIONS_PARAMETER, promotions)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
