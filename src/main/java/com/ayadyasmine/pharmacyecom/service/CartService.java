package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.Cart;
import com.ayadyasmine.pharmacyecom.domain.CartItem;
import com.ayadyasmine.pharmacyecom.domain.Customer;
import com.ayadyasmine.pharmacyecom.domain.ProductVariant;
import com.ayadyasmine.pharmacyecom.repository.CartItemRepository;
import com.ayadyasmine.pharmacyecom.repository.CartRepository;
import com.ayadyasmine.pharmacyecom.repository.CustomerRepository;
import com.ayadyasmine.pharmacyecom.repository.ProductVariantRepository;
import com.ayadyasmine.pharmacyecom.service.dto.CartDTO;
import com.ayadyasmine.pharmacyecom.service.mapper.CartMapper;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.ayadyasmine.pharmacyecom.domain.Cart}.
 */
@Service
@Transactional
public class CartService {

    private static final Logger LOG = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CustomerRepository customerRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       ProductVariantRepository productVariantRepository, CustomerRepository customerRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productVariantRepository = productVariantRepository;
        this.customerRepository = customerRepository;
        this.cartMapper = cartMapper;
    }

    /**
     * Save a cart.
     *
     * @param cartDTO the entity to save.
     * @return the persisted entity.
     */
    public CartDTO save(CartDTO cartDTO) {
        LOG.debug("Request to save Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    /**
     * Update a cart.
     *
     * @param cartDTO the entity to save.
     * @return the persisted entity.
     */
    public CartDTO update(CartDTO cartDTO) {
        LOG.debug("Request to update Cart : {}", cartDTO);
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    /**
     * Partially update a cart.
     *
     * @param cartDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CartDTO> partialUpdate(CartDTO cartDTO) {
        LOG.debug("Request to partially update Cart : {}", cartDTO);

        return cartRepository
            .findById(cartDTO.getId())
            .map(existingCart -> {
                cartMapper.partialUpdate(existingCart, cartDTO);

                return existingCart;
            })
            .map(cartRepository::save)
            .map(cartMapper::toDto);
    }

    /**
     * Get all the carts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CartDTO> findAll() {
        LOG.debug("Request to get all Carts");
        return cartRepository.findAll().stream().map(cartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the carts where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CartDTO> findAllWhereCustomerIsNull() {
        LOG.debug("Request to get all carts where Customer is null");
        return StreamSupport.stream(cartRepository.findAll().spliterator(), false)
            .filter(cart -> cart.getCustomer() == null)
            .map(cartMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cart by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CartDTO> findOne(Long id) {
        LOG.debug("Request to get Cart : {}", id);
        return cartRepository.findById(id).map(cartMapper::toDto);
    }

    /**
     * Delete the cart by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Cart : {}", id);
        cartRepository.deleteById(id);
    }

    public void addVariantToCart(Long variantId, Integer quantity, String login) {
        // 1. Vérifier la variante et le stock
        ProductVariant variant = productVariantRepository.findById(variantId)
            .orElseThrow(() -> new RuntimeException("Variante non trouvée"));

        if (variant.getStock() < quantity) {
            throw new RuntimeException("Stock insuffisant !");
        }

        // 2. LOGIQUE INTELLIGENTE : Récupérer le panier actif OU en créer un nouveau
        Cart cart = cartRepository.findByCustomerUserLoginAndActiveTrue(login)
            .orElseGet(() -> {
                // Si aucun panier actif n'existe (après un achat par exemple)
                Customer customer = customerRepository.findByUserLogin(login)
                    .orElseThrow(() -> new RuntimeException("Profil client introuvable pour : " + login));

                Cart newCart = new Cart();
                newCart.setCustomer(customer);
                newCart.setActive(true);
                newCart.setCreatedAt(Instant.now());
                newCart.setUpdatedAt(Instant.now());
                return cartRepository.save(newCart);
            });

        // 3. Vérifier si l'article est déjà dans le panier
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndVariantId(cart, variantId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setProduct(variant.getProduct());
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }

        cart.setUpdatedAt(Instant.now());
        cartRepository.save(cart);
    }
}
