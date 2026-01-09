package com.ayadyasmine.pharmacyecom.service;

import com.ayadyasmine.pharmacyecom.domain.Product;
import com.ayadyasmine.pharmacyecom.domain.ProductImage;
import com.ayadyasmine.pharmacyecom.repository.ProductImageRepository;
import com.ayadyasmine.pharmacyecom.repository.ProductRepository;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for handling file uploads.
 */
@Service
@Transactional
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    public FileService(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    public void uploadProductImage(Long productId, MultipartFile file) throws IOException {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setImageData(file.getBytes());
        productImage.setImageDataContentType(file.getContentType());
        // On peut définir la position automatiquement (ex: dernier + 1)
        productImage.setPosition(0); 

        productImageRepository.save(productImage);
    }
}