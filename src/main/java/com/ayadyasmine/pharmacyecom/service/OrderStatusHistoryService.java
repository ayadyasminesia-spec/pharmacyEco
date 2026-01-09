package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.OrderStatusHistory;
import com.ayadyasmine.pharmacyecom.repository.OrderStatusHistoryRepository;
import com.ayadyasmine.pharmacyecom.service.dto.OrderStatusHistoryDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.OrderStatusHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ayadyasmine.pharmacyecom.domain.OrderStatusHistory}.
 */
@Service
@Transactional
public class OrderStatusHistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusHistoryService.class);

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    private final OrderStatusHistoryMapper orderStatusHistoryMapper;

    public OrderStatusHistoryService(
        OrderStatusHistoryRepository orderStatusHistoryRepository,
        OrderStatusHistoryMapper orderStatusHistoryMapper
    ) {
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderStatusHistoryMapper = orderStatusHistoryMapper;
    }

    /**
     * Save a orderStatusHistory.
     *
     * @param orderStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderStatusHistoryDTO save(OrderStatusHistoryDTO orderStatusHistoryDTO) {
        LOG.debug("Request to save OrderStatusHistory : {}", orderStatusHistoryDTO);
        OrderStatusHistory orderStatusHistory = orderStatusHistoryMapper.toEntity(orderStatusHistoryDTO);
        orderStatusHistory = orderStatusHistoryRepository.save(orderStatusHistory);
        return orderStatusHistoryMapper.toDto(orderStatusHistory);
    }

    /**
     * Update a orderStatusHistory.
     *
     * @param orderStatusHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderStatusHistoryDTO update(OrderStatusHistoryDTO orderStatusHistoryDTO) {
        LOG.debug("Request to update OrderStatusHistory : {}", orderStatusHistoryDTO);
        OrderStatusHistory orderStatusHistory = orderStatusHistoryMapper.toEntity(orderStatusHistoryDTO);
        orderStatusHistory = orderStatusHistoryRepository.save(orderStatusHistory);
        return orderStatusHistoryMapper.toDto(orderStatusHistory);
    }

    /**
     * Partially update a orderStatusHistory.
     *
     * @param orderStatusHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderStatusHistoryDTO> partialUpdate(OrderStatusHistoryDTO orderStatusHistoryDTO) {
        LOG.debug("Request to partially update OrderStatusHistory : {}", orderStatusHistoryDTO);

        return orderStatusHistoryRepository
            .findById(orderStatusHistoryDTO.getId())
            .map(existingOrderStatusHistory -> {
                orderStatusHistoryMapper.partialUpdate(existingOrderStatusHistory, orderStatusHistoryDTO);

                return existingOrderStatusHistory;
            })
            .map(orderStatusHistoryRepository::save)
            .map(orderStatusHistoryMapper::toDto);
    }

    /**
     * Get all the orderStatusHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderStatusHistoryDTO> findAll() {
        LOG.debug("Request to get all OrderStatusHistories");
        return orderStatusHistoryRepository
            .findAll()
            .stream()
            .map(orderStatusHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one orderStatusHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderStatusHistoryDTO> findOne(Long id) {
        LOG.debug("Request to get OrderStatusHistory : {}", id);
        return orderStatusHistoryRepository.findById(id).map(orderStatusHistoryMapper::toDto);
    }

    /**
     * Delete the orderStatusHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete OrderStatusHistory : {}", id);
        orderStatusHistoryRepository.deleteById(id);
    }
}
