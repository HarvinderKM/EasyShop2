package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;

// add the annotations to make this a REST controller - LINE 13
@RestController
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories - LINE 16
@RequestMapping("/categories") //makes url slash /categories
// add annotation to allow cross site origin requests  - LINE 18
@CrossOrigin //makes front end work with back end !!!!!yen

public class CategoriesController {
    //autowire - LINE 21
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {  //CREATED CONSTRUCTORS HERE
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    private CategoryDao categoryDao;
    private ProductDao productDao;
//constructor goes here b/c of inversion of control dependencies - without constructor program doesn't know what to do with what

    // create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired

    // add the appropriate annotation for a get action
    @GetMapping()
    public List<Category> getAll()
    {
        // find and return all categories

        return categoryDao.getAllCategories(); //12/16
    }

    // add the appropriate annotation for a get action
    public Category getById(@PathVariable int id)
    {
        // get the category by id
        return categoryDao.getById(id); //12/16
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        // get a list of product by categoryId
        try {
            var products = productDao.listByCategoryId(categoryId);

            return products;
        }
catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");

        }
    }
    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category addCategory(@RequestBody Category category)
    {
        // insert the category
        return categoryDao.create(category);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id
        categoryDao.update(id,category);

    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id)
    {
        // delete the category by id
        categoryDao.delete(id);
    }
}
