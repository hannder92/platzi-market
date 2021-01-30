package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    @ApiOperation(value = "Get all supermarket products", authorizations = {@Authorization(value = "JWT")})
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Search a product by ID", authorizations = {@Authorization(value = "JWT")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found")
    })

    public ResponseEntity<Product> getProduct(@ApiParam(value = "The id of the product", required = true, example = "7") @PathVariable(name = "id") long productId) {
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{id}")
    @ApiOperation(value = "Search products by category ID", authorizations = {@Authorization(value = "JWT")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Products not found")
    })
    public ResponseEntity<List<Product>> getByCategory(@PathVariable(name = "id") int categoryId) {
        return productService.getByCateogory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    @ApiOperation(value = "Create new product", authorizations = {@Authorization(value = "JWT")})
    @ApiResponse(code = 201, message = "CREATED")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a product by ID", authorizations = {@Authorization(value = "JWT")})
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    public ResponseEntity delete(@PathVariable(name = "id") long productId) {
        if (productService.delete(productId)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
