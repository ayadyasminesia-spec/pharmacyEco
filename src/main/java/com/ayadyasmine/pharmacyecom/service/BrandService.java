package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.Brand;
import com.ayadyasmine.pharmacyecom.repository.BrandRepository;
import com.ayadyasmine.pharmacyecom.service.dto.BrandDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.BrandMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ayadyasmine.pharmacyecom.domain.Brand}.
 */
@Service
@Transactional
public class BrandService {

    private static final Logger LOG = LoggerFactory.getLogger(BrandService.class);

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save.
     * @return the persisted entity.
     */
    public BrandDTO save(BrandDTO brandDTO) {
        LOG.debug("Request to save Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        return brandMapper.toDto(brand);
    }

    /**
     * Update a brand.
     *
     * @param brandDTO the entity to save.
     * @return the persisted entity.
     */
    public BrandDTO update(BrandDTO brandDTO) {
        LOG.debug("Request to update Brand : {}", brandDTO);
        Brand brand = brandMapper.toEntity(brandDTO);
        brand = brandRepository.save(brand);
        return brandMapper.toDto(brand);
    }

    /**
     * Partially update a brand.
     *
     * @param brandDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BrandDTO> partialUpdate(BrandDTO brandDTO) {
        LOG.debug("Request to partially update Brand : {}", brandDTO);

        return brandRepository
            .findById(brandDTO.getId())
            .map(existingBrand -> {
                brandMapper.partialUpdate(existingBrand, brandDTO);

                return existingBrand;
            })
            .map(brandRepository::save)
            .map(brandMapper::toDto);
    }

    /**
     * Get one brand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BrandDTO> findOne(Long id) {
        LOG.debug("Request to get Brand : {}", id);
        return brandRepository.findById(id).map(brandMapper::toDto);
    }

    /**
     * Delete the brand by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
    }
}
