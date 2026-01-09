package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.Product;
import com.ayadyasmine.pharmacyecom.domain.Promotion;
import com.ayadyasmine.pharmacyecom.repository.PromotionRepository;
import com.ayadyasmine.pharmacyecom.service.dto.PromotionDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.PromotionMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling promotions and dynamic pricing.
 */
@Service
@Transactional
public class PromotionService {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionService.class);

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public PromotionService(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    // Standard CRUD methods for Promotion
    public PromotionDTO save(PromotionDTO promotionDTO) {
        LOG.debug("Request to save Promotion : {}", promotionDTO);
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDto(promotion);
    }

    public PromotionDTO update(PromotionDTO promotionDTO) {
        LOG.debug("Request to update Promotion : {}", promotionDTO);
        Promotion promotion = promotionMapper.toEntity(promotionDTO);
        promotion = promotionRepository.save(promotion);
        return promotionMapper.toDto(promotion);
    }

    public Optional<PromotionDTO> partialUpdate(PromotionDTO promotionDTO) {
        LOG.debug("Request to partially update Promotion : {}", promotionDTO);

        return promotionRepository
            .findById(promotionDTO.getId())
            .map(existingPromotion -> {
                promotionMapper.partialUpdate(existingPromotion, promotionDTO);

                return existingPromotion;
            })
            .map(promotionRepository::save)
            .map(promotionMapper::toDto);
    }

    public List<PromotionDTO> findAll() {
        LOG.debug("Request to get all Promotions");
        return promotionRepository.findAll().stream()
            .map(promotionMapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public Page<PromotionDTO> findAllWithEagerRelationships(Pageable pageable) {
        LOG.debug("Request to get all Promotions with eager relationships");
        Page<Promotion> page = promotionRepository.findAllWithEagerRelationships(pageable);
        return page.map(promotionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<PromotionDTO> findOne(Long id) {
        LOG.debug("Request to get Promotion : {}", id);
        return promotionRepository.findById(id).map(promotionMapper::toDto);
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Promotion : {}", id);
        promotionRepository.deleteById(id);
    }

    // Dynamic pricing methods
    public BigDecimal getDiscountedPrice(Product product) {
        List<Promotion> activePromos = promotionRepository.findActivePromotionsForProduct(product.getId(), Instant.now());

        if (activePromos.isEmpty()) {
            return product.getPrice();
        }

        // If multiple promos, take the most advantageous (the biggest percentage)
        int maxDiscount = activePromos.stream()
            .mapToInt(Promotion::getDiscountPercent)
            .max()
            .orElse(0);

        BigDecimal discount = product.getPrice()
            .multiply(new BigDecimal(maxDiscount))
            .divide(new BigDecimal(100), RoundingMode.HALF_UP);

        return product.getPrice().subtract(discount);
    }

    /**
     * Get the discount percentage for a product if any active promotion exists
     */
    public int getDiscountPercentForProduct(Long productId) {
        List<Promotion> activePromos = promotionRepository.findActivePromotionsForProduct(productId, Instant.now());

        if (activePromos.isEmpty()) {
            return 0;
        }

        // Return the maximum discount percentage
        return activePromos.stream()
            .mapToInt(Promotion::getDiscountPercent)
            .max()
            .orElse(0);
    }
}