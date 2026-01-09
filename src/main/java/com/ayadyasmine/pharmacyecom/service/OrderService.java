package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.Cart;
import com.ayadyasmine.pharmacyecom.domain.CartItem;
import com.ayadyasmine.pharmacyecom.domain.Order;
import com.ayadyasmine.pharmacyecom.domain.OrderItem;
import com.ayadyasmine.pharmacyecom.domain.OrderStatusHistory;
import com.ayadyasmine.pharmacyecom.domain.ProductVariant;
import com.ayadyasmine.pharmacyecom.domain.enumeration.OrderStatus;
import com.ayadyasmine.pharmacyecom.domain.enumeration.PaymentMethod;
import com.ayadyasmine.pharmacyecom.repository.CartItemRepository;
import com.ayadyasmine.pharmacyecom.repository.CartRepository;
import com.ayadyasmine.pharmacyecom.repository.OrderItemRepository;
import com.ayadyasmine.pharmacyecom.repository.OrderRepository;
import com.ayadyasmine.pharmacyecom.repository.OrderStatusHistoryRepository;
import com.ayadyasmine.pharmacyecom.repository.ProductVariantRepository;
import com.ayadyasmine.pharmacyecom.service.dto.OrderDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.OrderMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ayadyasmine.pharmacyecom.domain.Order}.
 */
@Service
@Transactional
public class OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final PromotionService promotionService;

    public OrderService(
        OrderRepository orderRepository,
        OrderMapper orderMapper,
        CartRepository cartRepository,
        CartItemRepository cartItemRepository,
        OrderItemRepository orderItemRepository,
        ProductVariantRepository productVariantRepository,
        OrderStatusHistoryRepository orderStatusHistoryRepository,
        PromotionService promotionService
    ) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.promotionService = promotionService;
    }

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO save(OrderDTO orderDTO) {
        LOG.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Update a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderDTO update(OrderDTO orderDTO) {
        LOG.debug("Request to update Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Partially update a order.
     *
     * @param orderDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        LOG.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        LOG.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderDTO checkout(String login, String deliveryAddress, PaymentMethod paymentMethod) {
        // 1. Récupérer le panier
        Cart cart = cartRepository.findByCustomerUserLoginAndActiveTrue(login)
            .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        // 2. MODIFICATION ICI : Au lieu de cart.getItems(), on demande directement au Repository
        List<CartItem> items = cartItemRepository.findByCart(cart);

        if (items.isEmpty()) {
            throw new RuntimeException("Le panier est vide");
        }

        // 3. Créer la commande
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryAddress(deliveryAddress);

        BigDecimal total = BigDecimal.ZERO;

        // 4. Transformer les CartItems en OrderItems + Maj Stock
        for (CartItem item : items) {
            ProductVariant variant = item.getVariant();

            // Vérification ultime du stock avant de figer la commande
            if (variant.getStock() < item.getQuantity()) {
                throw new RuntimeException("Stock épuisé pour : " + variant.getLabel());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVariant(variant);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());

            // Get the discounted price for the product (using the main product, not the variant)
            BigDecimal currentPrice = promotionService.getDiscountedPrice(item.getProduct());
            orderItem.setUnitPrice(currentPrice); // ON FIGE LE PRIX ICI

            total = total.add(currentPrice.multiply(new BigDecimal(item.getQuantity())));

            // Déduire le stock
            variant.setStock(variant.getStock() - item.getQuantity());
            productVariantRepository.save(variant);

            order.addItems(orderItem); // Méthode générée par JHipster
        }

        order.setTotalPrice(total);
        // Simulation frais de port (Exemple: 600 DA fixe ou gratuit si > 10.000 DA)
        order.setShippingFee(total.compareTo(new BigDecimal(10000)) > 0 ? BigDecimal.ZERO : new BigDecimal(600));

        // 5. Sauvegarder la commande
        Order savedOrder = orderRepository.save(order);

        // 6. Créer l'historique
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(savedOrder);
        history.setStatus(OrderStatus.PENDING);
        history.setChangedAt(Instant.now());
        history.setNotes("Commande créée par le client");
        orderStatusHistoryRepository.save(history);

        // 7. Désactiver le panier
        cart.setActive(false);
        cartRepository.save(cart);

        // Convertir en DTO et retourner
        return orderMapper.toDto(savedOrder);
    }
}
