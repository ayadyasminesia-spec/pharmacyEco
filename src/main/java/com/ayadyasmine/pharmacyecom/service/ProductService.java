package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.Product;
import com.ayadyasmine.pharmacyecom.repository.ProductRepository;
import com.ayadyasmine.pharmacyecom.service.dto.ProductDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.ProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ayadyasmine.pharmacyecom.domain.Product}.
 */
@Service
@Transactional
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final PromotionService promotionService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, PromotionService promotionService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.promotionService = promotionService;
    }

    /**
     * Save a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO save(ProductDTO productDTO) {
        LOG.debug("Request to save Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        ProductDTO savedDTO = productMapper.toDto(product);
        savedDTO.setDiscountedPrice(promotionService.getDiscountedPrice(product));
        return savedDTO;
    }

    /**
     * Update a product.
     *
     * @param productDTO the entity to save.
     * @return the persisted entity.
     */
    public ProductDTO update(ProductDTO productDTO) {
        LOG.debug("Request to update Product : {}", productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        ProductDTO updatedDTO = productMapper.toDto(product);
        updatedDTO.setDiscountedPrice(promotionService.getDiscountedPrice(product));
        return updatedDTO;
    }

    /**
     * Partially update a product.
     *
     * @param productDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {
        LOG.debug("Request to partially update Product : {}", productDTO);

        return productRepository
            .findById(productDTO.getId())
            .map(existingProduct -> {
                productMapper.partialUpdate(existingProduct, productDTO);

                return existingProduct;
            })
            .map(productRepository::save)
            .map(product -> {
                ProductDTO dto = productMapper.toDto(product);
                dto.setDiscountedPrice(promotionService.getDiscountedPrice(product));
                return dto;
            });
    }

    /**
     * Get all the products with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProductDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productRepository.findAllWithEagerRelationships(pageable)
            .map(product -> {
                ProductDTO dto = productMapper.toDto(product);
                dto.setDiscountedPrice(promotionService.getDiscountedPrice(product));
                return dto;
            });
    }

    /**
     * Get one product by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        LOG.debug("Request to get Product : {}", id);
        return productRepository.findOneWithEagerRelationships(id)
            .map(product -> {
                ProductDTO dto = productMapper.toDto(product);
                dto.setDiscountedPrice(promotionService.getDiscountedPrice(product));
                return dto;
            });
    }

    /**
     * Delete the product by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Product : {}", id);
        productRepository.deleteById(id);
    }
}
