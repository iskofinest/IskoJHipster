package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.IskoJHipsterApp;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.Supplier;
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.service.ProductService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.ProductCriteria;
import com.mycompany.myapp.service.ProductQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IskoJHipsterApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PRODUCT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRODUCT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_ORIGINAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORIGINAL_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SELLING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELLING_PRICE = new BigDecimal(2);

    private static final String DEFAULT_AGENT = "AAAAAAAAAA";
    private static final String UPDATED_AGENT = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource(productService, productQueryService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .reference(DEFAULT_REFERENCE)
            .category(DEFAULT_CATEGORY)
            .description(DEFAULT_DESCRIPTION)
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .quantity(DEFAULT_QUANTITY)
            .unit(DEFAULT_UNIT)
            .product_date(DEFAULT_PRODUCT_DATE)
            .originalPrice(DEFAULT_ORIGINAL_PRICE)
            .sellingPrice(DEFAULT_SELLING_PRICE)
            .agent(DEFAULT_AGENT);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testProduct.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProduct.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testProduct.getProduct_date()).isEqualTo(DEFAULT_PRODUCT_DATE);
        assertThat(testProduct.getOriginalPrice()).isEqualTo(DEFAULT_ORIGINAL_PRICE);
        assertThat(testProduct.getSellingPrice()).isEqualTo(DEFAULT_SELLING_PRICE);
        assertThat(testProduct.getAgent()).isEqualTo(DEFAULT_AGENT);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setName(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setReference(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setQuantity(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setUnit(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProduct_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setProduct_date(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOriginalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setOriginalPrice(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSellingPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSellingPrice(null);

        // Create the Product, which fails.

        restProductMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].product_date").value(hasItem(DEFAULT_PRODUCT_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].agent").value(hasItem(DEFAULT_AGENT.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.product_date").value(DEFAULT_PRODUCT_DATE.toString()))
            .andExpect(jsonPath("$.originalPrice").value(DEFAULT_ORIGINAL_PRICE.intValue()))
            .andExpect(jsonPath("$.sellingPrice").value(DEFAULT_SELLING_PRICE.intValue()))
            .andExpect(jsonPath("$.agent").value(DEFAULT_AGENT.toString()));
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name equals to DEFAULT_NAME
        defaultProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name is not null
        defaultProductShouldBeFound("name.specified=true");

        // Get all the productList where name is null
        defaultProductShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reference equals to DEFAULT_REFERENCE
        defaultProductShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the productList where reference equals to UPDATED_REFERENCE
        defaultProductShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProductsByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultProductShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the productList where reference equals to UPDATED_REFERENCE
        defaultProductShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllProductsByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reference is not null
        defaultProductShouldBeFound("reference.specified=true");

        // Get all the productList where reference is null
        defaultProductShouldNotBeFound("reference.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category equals to DEFAULT_CATEGORY
        defaultProductShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the productList where category equals to UPDATED_CATEGORY
        defaultProductShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllProductsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultProductShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the productList where category equals to UPDATED_CATEGORY
        defaultProductShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllProductsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where category is not null
        defaultProductShouldBeFound("category.specified=true");

        // Get all the productList where category is null
        defaultProductShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description equals to DEFAULT_DESCRIPTION
        defaultProductShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description equals to UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the productList where description equals to UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description is not null
        defaultProductShouldBeFound("description.specified=true");

        // Get all the productList where description is null
        defaultProductShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brand equals to DEFAULT_BRAND
        defaultProductShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the productList where brand equals to UPDATED_BRAND
        defaultProductShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultProductShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the productList where brand equals to UPDATED_BRAND
        defaultProductShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brand is not null
        defaultProductShouldBeFound("brand.specified=true");

        // Get all the productList where brand is null
        defaultProductShouldNotBeFound("brand.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where model equals to DEFAULT_MODEL
        defaultProductShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the productList where model equals to UPDATED_MODEL
        defaultProductShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllProductsByModelIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultProductShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the productList where model equals to UPDATED_MODEL
        defaultProductShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllProductsByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where model is not null
        defaultProductShouldBeFound("model.specified=true");

        // Get all the productList where model is null
        defaultProductShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity equals to DEFAULT_QUANTITY
        defaultProductShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity equals to UPDATED_QUANTITY
        defaultProductShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultProductShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the productList where quantity equals to UPDATED_QUANTITY
        defaultProductShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity is not null
        defaultProductShouldBeFound("quantity.specified=true");

        // Get all the productList where quantity is null
        defaultProductShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where unit equals to DEFAULT_UNIT
        defaultProductShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the productList where unit equals to UPDATED_UNIT
        defaultProductShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllProductsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultProductShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the productList where unit equals to UPDATED_UNIT
        defaultProductShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllProductsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where unit is not null
        defaultProductShouldBeFound("unit.specified=true");

        // Get all the productList where unit is null
        defaultProductShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProduct_dateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where product_date equals to DEFAULT_PRODUCT_DATE
        defaultProductShouldBeFound("product_date.equals=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where product_date equals to UPDATED_PRODUCT_DATE
        defaultProductShouldNotBeFound("product_date.equals=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProduct_dateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where product_date in DEFAULT_PRODUCT_DATE or UPDATED_PRODUCT_DATE
        defaultProductShouldBeFound("product_date.in=" + DEFAULT_PRODUCT_DATE + "," + UPDATED_PRODUCT_DATE);

        // Get all the productList where product_date equals to UPDATED_PRODUCT_DATE
        defaultProductShouldNotBeFound("product_date.in=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProduct_dateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where product_date is not null
        defaultProductShouldBeFound("product_date.specified=true");

        // Get all the productList where product_date is null
        defaultProductShouldNotBeFound("product_date.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProduct_dateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where product_date greater than or equals to DEFAULT_PRODUCT_DATE
        defaultProductShouldBeFound("product_date.greaterOrEqualThan=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where product_date greater than or equals to UPDATED_PRODUCT_DATE
        defaultProductShouldNotBeFound("product_date.greaterOrEqualThan=" + UPDATED_PRODUCT_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByProduct_dateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where product_date less than or equals to DEFAULT_PRODUCT_DATE
        defaultProductShouldNotBeFound("product_date.lessThan=" + DEFAULT_PRODUCT_DATE);

        // Get all the productList where product_date less than or equals to UPDATED_PRODUCT_DATE
        defaultProductShouldBeFound("product_date.lessThan=" + UPDATED_PRODUCT_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsByOriginalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalPrice equals to DEFAULT_ORIGINAL_PRICE
        defaultProductShouldBeFound("originalPrice.equals=" + DEFAULT_ORIGINAL_PRICE);

        // Get all the productList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultProductShouldNotBeFound("originalPrice.equals=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByOriginalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalPrice in DEFAULT_ORIGINAL_PRICE or UPDATED_ORIGINAL_PRICE
        defaultProductShouldBeFound("originalPrice.in=" + DEFAULT_ORIGINAL_PRICE + "," + UPDATED_ORIGINAL_PRICE);

        // Get all the productList where originalPrice equals to UPDATED_ORIGINAL_PRICE
        defaultProductShouldNotBeFound("originalPrice.in=" + UPDATED_ORIGINAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByOriginalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalPrice is not null
        defaultProductShouldBeFound("originalPrice.specified=true");

        // Get all the productList where originalPrice is null
        defaultProductShouldNotBeFound("originalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellingPrice equals to DEFAULT_SELLING_PRICE
        defaultProductShouldBeFound("sellingPrice.equals=" + DEFAULT_SELLING_PRICE);

        // Get all the productList where sellingPrice equals to UPDATED_SELLING_PRICE
        defaultProductShouldNotBeFound("sellingPrice.equals=" + UPDATED_SELLING_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellingPrice in DEFAULT_SELLING_PRICE or UPDATED_SELLING_PRICE
        defaultProductShouldBeFound("sellingPrice.in=" + DEFAULT_SELLING_PRICE + "," + UPDATED_SELLING_PRICE);

        // Get all the productList where sellingPrice equals to UPDATED_SELLING_PRICE
        defaultProductShouldNotBeFound("sellingPrice.in=" + UPDATED_SELLING_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsBySellingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellingPrice is not null
        defaultProductShouldBeFound("sellingPrice.specified=true");

        // Get all the productList where sellingPrice is null
        defaultProductShouldNotBeFound("sellingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByAgentIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where agent equals to DEFAULT_AGENT
        defaultProductShouldBeFound("agent.equals=" + DEFAULT_AGENT);

        // Get all the productList where agent equals to UPDATED_AGENT
        defaultProductShouldNotBeFound("agent.equals=" + UPDATED_AGENT);
    }

    @Test
    @Transactional
    public void getAllProductsByAgentIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where agent in DEFAULT_AGENT or UPDATED_AGENT
        defaultProductShouldBeFound("agent.in=" + DEFAULT_AGENT + "," + UPDATED_AGENT);

        // Get all the productList where agent equals to UPDATED_AGENT
        defaultProductShouldNotBeFound("agent.in=" + UPDATED_AGENT);
    }

    @Test
    @Transactional
    public void getAllProductsByAgentIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where agent is not null
        defaultProductShouldBeFound("agent.specified=true");

        // Get all the productList where agent is null
        defaultProductShouldNotBeFound("agent.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySuppliersIsEqualToSomething() throws Exception {
        // Initialize the database
        Supplier suppliers = SupplierResourceIntTest.createEntity(em);
        em.persist(suppliers);
        em.flush();
        product.addSuppliers(suppliers);
        productRepository.saveAndFlush(product);
        Long suppliersId = suppliers.getId();

        // Get all the productList where suppliers equals to suppliersId
        defaultProductShouldBeFound("suppliersId.equals=" + suppliersId);

        // Get all the productList where suppliers equals to suppliersId + 1
        defaultProductShouldNotBeFound("suppliersId.equals=" + (suppliersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].product_date").value(hasItem(DEFAULT_PRODUCT_DATE.toString())))
            .andExpect(jsonPath("$.[*].originalPrice").value(hasItem(DEFAULT_ORIGINAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].sellingPrice").value(hasItem(DEFAULT_SELLING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].agent").value(hasItem(DEFAULT_AGENT.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .reference(UPDATED_REFERENCE)
            .category(UPDATED_CATEGORY)
            .description(UPDATED_DESCRIPTION)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .quantity(UPDATED_QUANTITY)
            .unit(UPDATED_UNIT)
            .product_date(UPDATED_PRODUCT_DATE)
            .originalPrice(UPDATED_ORIGINAL_PRICE)
            .sellingPrice(UPDATED_SELLING_PRICE)
            .agent(UPDATED_AGENT);

        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testProduct.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testProduct.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testProduct.getProduct_date()).isEqualTo(UPDATED_PRODUCT_DATE);
        assertThat(testProduct.getOriginalPrice()).isEqualTo(UPDATED_ORIGINAL_PRICE);
        assertThat(testProduct.getSellingPrice()).isEqualTo(UPDATED_SELLING_PRICE);
        assertThat(testProduct.getAgent()).isEqualTo(UPDATED_AGENT);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Create the Product

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProductMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);
        product2.setId(2L);
        assertThat(product1).isNotEqualTo(product2);
        product1.setId(null);
        assertThat(product1).isNotEqualTo(product2);
    }
}
