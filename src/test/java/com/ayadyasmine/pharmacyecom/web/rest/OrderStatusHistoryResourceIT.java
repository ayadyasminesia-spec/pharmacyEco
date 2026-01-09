package com.ayadyasmine.pharmacyecom.web.rest;

import static com.ayadyasmine.pharmacyecom.domain.OrderStatusHistoryAsserts.*;
import static com.ayadyasmine.pharmacyecom.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ayadyasmine.pharmacyecom.IntegrationTest;
import com.ayadyasmine.pharmacyecom.domain.OrderStatusHistory;
import com.ayadyasmine.pharmacyecom.domain.enumeration.OrderStatus;
import com.ayadyasmine.pharmacyecom.repository.OrderStatusHistoryRepository;
import com.ayadyasmine.pharmacyecom.service.dto.OrderStatusHistoryDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.OrderStatusHistoryMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderStatusHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderStatusHistoryResourceIT {

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.PENDING;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.CONFIRMED;

    private static final Instant DEFAULT_CHANGED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHANGED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/order-status-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrderStatusHistoryRepository orderStatusHistoryRepository;

    @Autowired
    private OrderStatusHistoryMapper orderStatusHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderStatusHistoryMockMvc;

    private OrderStatusHistory orderStatusHistory;

    private OrderStatusHistory insertedOrderStatusHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatusHistory createEntity() {
        return new OrderStatusHistory().status(DEFAULT_STATUS).changedAt(DEFAULT_CHANGED_AT).notes(DEFAULT_NOTES);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatusHistory createUpdatedEntity() {
        return new OrderStatusHistory().status(UPDATED_STATUS).changedAt(UPDATED_CHANGED_AT).notes(UPDATED_NOTES);
    }

    @BeforeEach
    void initTest() {
        orderStatusHistory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedOrderStatusHistory != null) {
            orderStatusHistoryRepository.delete(insertedOrderStatusHistory);
            insertedOrderStatusHistory = null;
        }
    }

    @Test
    @Transactional
    void createOrderStatusHistory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);
        var returnedOrderStatusHistoryDTO = om.readValue(
            restOrderStatusHistoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderStatusHistoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrderStatusHistoryDTO.class
        );

        // Validate the OrderStatusHistory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrderStatusHistory = orderStatusHistoryMapper.toEntity(returnedOrderStatusHistoryDTO);
        assertOrderStatusHistoryUpdatableFieldsEquals(
            returnedOrderStatusHistory,
            getPersistedOrderStatusHistory(returnedOrderStatusHistory)
        );

        insertedOrderStatusHistory = returnedOrderStatusHistory;
    }

    @Test
    @Transactional
    void createOrderStatusHistoryWithExistingId() throws Exception {
        // Create the OrderStatusHistory with an existing ID
        orderStatusHistory.setId(1L);
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        orderStatusHistory.setStatus(null);

        // Create the OrderStatusHistory, which fails.
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        restOrderStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChangedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        orderStatusHistory.setChangedAt(null);

        // Create the OrderStatusHistory, which fails.
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        restOrderStatusHistoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderStatusHistoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderStatusHistories() throws Exception {
        // Initialize the database
        insertedOrderStatusHistory = orderStatusHistoryRepository.saveAndFlush(orderStatusHistory);

        // Get all the orderStatusHistoryList
        restOrderStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatusHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].changedAt").value(hasItem(DEFAULT_CHANGED_AT.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getOrderStatusHistory() throws Exception {
        // Initialize the database
        insertedOrderStatusHistory = orderStatusHistoryRepository.saveAndFlush(orderStatusHistory);

        // Get the orderStatusHistory
        restOrderStatusHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, orderStatusHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderStatusHistory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.changedAt").value(DEFAULT_CHANGED_AT.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getNonExistingOrderStatusHistory() throws Exception {
        // Get the orderStatusHistory
        restOrderStatusHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderStatusHistory() throws Exception {
        // Initialize the database
        insertedOrderStatusHistory = orderStatusHistoryRepository.saveAndFlush(orderStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderStatusHistory
        OrderStatusHistory updatedOrderStatusHistory = orderStatusHistoryRepository.findById(orderStatusHistory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrderStatusHistory are not directly saved in db
        em.detach(updatedOrderStatusHistory);
        updatedOrderStatusHistory.status(UPDATED_STATUS).changedAt(UPDATED_CHANGED_AT).notes(UPDATED_NOTES);
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(updatedOrderStatusHistory);

        restOrderStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderStatusHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrderStatusHistoryToMatchAllProperties(updatedOrderStatusHistory);
    }

    @Test
    @Transactional
    void putNonExistingOrderStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderStatusHistory.setId(longCount.incrementAndGet());

        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderStatusHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderStatusHistory.setId(longCount.incrementAndGet());

        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(orderStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderStatusHistory.setId(longCount.incrementAndGet());

        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(orderStatusHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedOrderStatusHistory = orderStatusHistoryRepository.saveAndFlush(orderStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderStatusHistory using partial update
        OrderStatusHistory partialUpdatedOrderStatusHistory = new OrderStatusHistory();
        partialUpdatedOrderStatusHistory.setId(orderStatusHistory.getId());

        partialUpdatedOrderStatusHistory.changedAt(UPDATED_CHANGED_AT).notes(UPDATED_NOTES);

        restOrderStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the OrderStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderStatusHistoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrderStatusHistory, orderStatusHistory),
            getPersistedOrderStatusHistory(orderStatusHistory)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrderStatusHistoryWithPatch() throws Exception {
        // Initialize the database
        insertedOrderStatusHistory = orderStatusHistoryRepository.saveAndFlush(orderStatusHistory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the orderStatusHistory using partial update
        OrderStatusHistory partialUpdatedOrderStatusHistory = new OrderStatusHistory();
        partialUpdatedOrderStatusHistory.setId(orderStatusHistory.getId());

        partialUpdatedOrderStatusHistory.status(UPDATED_STATUS).changedAt(UPDATED_CHANGED_AT).notes(UPDATED_NOTES);

        restOrderStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderStatusHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrderStatusHistory))
            )
            .andExpect(status().isOk());

        // Validate the OrderStatusHistory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrderStatusHistoryUpdatableFieldsEquals(
            partialUpdatedOrderStatusHistory,
            getPersistedOrderStatusHistory(partialUpdatedOrderStatusHistory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrderStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderStatusHistory.setId(longCount.incrementAndGet());

        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderStatusHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderStatusHistory.setId(longCount.incrementAndGet());

        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(orderStatusHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderStatusHistory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        orderStatusHistory.setId(longCount.incrementAndGet());

        // Create the OrderStatusHistory
        OrderStatusHistoryDTO orderStatusHistoryDTO = orderStatusHistoryMapper.toDto(orderStatusHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderStatusHistoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(orderStatusHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderStatusHistory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderStatusHistory() throws Exception {
        // Initialize the database
        insertedOrderStatusHistory = orderStatusHistoryRepository.saveAndFlush(orderStatusHistory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the orderStatusHistory
        restOrderStatusHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderStatusHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return orderStatusHistoryRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected OrderStatusHistory getPersistedOrderStatusHistory(OrderStatusHistory orderStatusHistory) {
        return orderStatusHistoryRepository.findById(orderStatusHistory.getId()).orElseThrow();
    }

    protected void assertPersistedOrderStatusHistoryToMatchAllProperties(OrderStatusHistory expectedOrderStatusHistory) {
        assertOrderStatusHistoryAllPropertiesEquals(expectedOrderStatusHistory, getPersistedOrderStatusHistory(expectedOrderStatusHistory));
    }

    protected void assertPersistedOrderStatusHistoryToMatchUpdatableProperties(OrderStatusHistory expectedOrderStatusHistory) {
        assertOrderStatusHistoryAllUpdatablePropertiesEquals(
            expectedOrderStatusHistory,
            getPersistedOrderStatusHistory(expectedOrderStatusHistory)
        );
    }
}
