package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CategoryRepository;
import com.mycompany.myapp.service.CategoryService;
import com.mycompany.myapp.service.dto.CategoryDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Category}.
 */
@RestController
@RequestMapping("/api")
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private static final String ENTITY_NAME = "category";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    public CategoryResource(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@code POST  /categories} : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryDTO, or with status {@code 400 (Bad Request)} if the category has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) throws URISyntaxException {
        log.debug("REST request to save Category : {}", categoryDTO);
        if (categoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new category cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity
            .created(new URI("/api/categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories/:id} : Updates an existing category.
     *
     * @param id the id of the categoryDTO to save.
     * @param categoryDTO the categoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryDTO,
     * or with status {@code 400 (Bad Request)} if the categoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CategoryDTO categoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Category : {}, {}", id, categoryDTO);
        if (categoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categories/:id} : Partial updates given fields of an existing category, field will ignore if it is null
     *
     * @param id the id of the categoryDTO to save.
     * @param categoryDTO the categoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryDTO,
     * or with status {@code 400 (Bad Request)} if the categoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategoryDTO> partialUpdateCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CategoryDTO categoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Category partially : {}, {}", id, categoryDTO);
        if (categoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategoryDTO> result = categoryService.partialUpdate(categoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categories} : get all the categories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categories in body.
     */
    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategories() {
        log.debug("REST request to get all Categories");
        return categoryService.findAll();
    }

    /**
     * {@code GET  /categories/:id} : get the "id" category.
     *
     * @param id the id of the categoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        log.debug("REST request to get Category : {}", id);
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryDTO);
    }

    /**
     * {@code DELETE  /categories/:id} : delete the "id" category.
     *
     * @param id the id of the categoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        log.debug("REST request to delete Category : {}", id);
        categoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
