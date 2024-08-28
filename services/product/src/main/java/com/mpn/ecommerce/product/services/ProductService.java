package com.mpn.ecommerce.product.services;

import com.mpn.ecommerce.product.dto.ProductPurchaseRequest;
import com.mpn.ecommerce.product.dto.ProductPurchaseResponse;
import com.mpn.ecommerce.product.dto.ProductRequest;
import com.mpn.ecommerce.product.dto.ProductResponse;
import com.mpn.ecommerce.product.exception.ProductPurchaseException;
import com.mpn.ecommerce.product.mapper.ProductMapper;
import com.mpn.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products doesn't exist");
        }

        var storedRequest = request.stream()
                .sorted((Comparator.comparing(ProductPurchaseRequest::productId)))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i=0 ; i < storedProducts.size() ; i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient quantity for product with id::" + product.getId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product , productRequest.quantity()));
        }


        return  purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the id: " + productId));
    }

    public List<ProductResponse> findAll() {
        return  repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
