![Capstone_3__Easy_Shop (1).png](src/main/java/Capstone_3__Easy_Shop%20%281%29.png)
![Screenshot 2024-12-19 at 1.19.51 PM.png](src/main/java/Screenshot%202024-12-19%20at%201.19.51%E2%80%AFPM.png)
### Project Overview:
This capstone project is an e-commerce application for a company named **EasyShop**. I assumed the role of a backend developer to enhance an existing online store. 

## The application was developed using the following: 
- SpringBoot for backend server 
- MySQL for database storage 
- Visual Code Studio for front end 
- Front end website was provided as a starter code 
- Postman 2 test all API endpoints 

## Backlog for tracking progress:![Screenshot 2024-12-19 at 1.15.48 PM.png](src/main/java/Screenshot%202024-12-19%20at%201.15.48%E2%80%AFPM.png)

## Fixing Bugs:
One of the Bugs fixed were changing one of the "Minimum's" to "Maximum" in the search bar.
![Screenshot 2024-12-19 at 1.20.37 PM.png](src/main/java/Screenshot%202024-12-19%20at%201.20.37%E2%80%AFPM.png)
###
### Did this by changing the HTML in Visual Code Studio
![Screenshot 2024-12-20 at 8.52.06 AM.png](src/main/java/Screenshot%202024-12-20%20at%208.52.06%E2%80%AFAM.png)
## Phase 1: Categories Controller 
Adding annotations into the categories controller was the first phase of the project. 
``` Java
@RestController
@RequestMapping("/categories") 
@CrossOrigin 

public class CategoriesController {
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {  //CREATED CONSTRUCTORS HERE
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    private CategoryDao categoryDao;
    private ProductDao productDao;
```
###
## Phase 2: Fix Bugs 
Products Controller Page: The update was creating a product instead of updating. 
```Java
@PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProduct(@PathVariable int id, @RequestBody Product product) // search
    {
        try
        {
            productDao.update(id, product); //fix bugs - updates product (changed create to update)
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
```
###
```Java
@RestController
@RequestMapping("products")
@CrossOrigin
public class ProductsController {
    private ProductDao productDao;
    @Autowired
    public ProductsController(ProductDao productDao)
    {this.productDao = productDao;}
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Product> search(@RequestParam(name="cat", required = false) Integer categoryId,
                                @RequestParam(name="minPrice", required = false) BigDecimal minPrice,
                                @RequestParam(name="maxPrice", required = false) BigDecimal maxPrice,
                                @RequestParam(name="color", required = false) String color)
    {
        try
        {
            return productDao.search(categoryId, minPrice, maxPrice, color);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

```
### Favorite/Interesting Code: 
```Java
    private Category mapRow(ResultSet row) throws SQLException
{
    int categoryId = row.getInt("category_id");
    String name = row.getString("name");
    String description = row.getString("description");

    Category category = new Category()
    {{
        setCategoryId(categoryId);
        setName(name);
        setDescription(description);
    }};

    return category;
}
```


## Phase 4: User Profile 
Used the ProfileDAO and MySQLProfileDao to be able to edit profile. 
![Screenshot 2024-12-19 at 1.32.41 PM.png](src/main/java/Screenshot%202024-12-19%20at%201.32.41%E2%80%AFPM.png)

### Thank you for Watching ! 
#### Harvinder Kaur 