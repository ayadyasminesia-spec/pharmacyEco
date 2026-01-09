package com.ayadyasmine.pharmacyecom.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.ayadyasmine.pharmacyecom.domain.Product} entity. This class is used
 * in {@link com.ayadyasmine.pharmacyecom.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter slug;

    private BigDecimalFilter price;

    private BigDecimalFilter oldPrice;

    private IntegerFilter stock;

    private StringFilter sku;

    private BooleanFilter active;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private StringFilter metaTitle;

    private StringFilter metaDescription;

    private LongFilter viewsCount;

    private LongFilter categoryId;

    private LongFilter brandId;

    private LongFilter promotionsId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.slug = other.optionalSlug().map(StringFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(BigDecimalFilter::copy).orElse(null);
        this.oldPrice = other.optionalOldPrice().map(BigDecimalFilter::copy).orElse(null);
        this.stock = other.optionalStock().map(IntegerFilter::copy).orElse(null);
        this.sku = other.optionalSku().map(StringFilter::copy).orElse(null);
        this.active = other.optionalActive().map(BooleanFilter::copy).orElse(null);
        this.createdAt = other.optionalCreatedAt().map(InstantFilter::copy).orElse(null);
        this.updatedAt = other.optionalUpdatedAt().map(InstantFilter::copy).orElse(null);
        this.metaTitle = other.optionalMetaTitle().map(StringFilter::copy).orElse(null);
        this.metaDescription = other.optionalMetaDescription().map(StringFilter::copy).orElse(null);
        this.viewsCount = other.optionalViewsCount().map(LongFilter::copy).orElse(null);
        this.categoryId = other.optionalCategoryId().map(LongFilter::copy).orElse(null);
        this.brandId = other.optionalBrandId().map(LongFilter::copy).orElse(null);
        this.promotionsId = other.optionalPromotionsId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSlug() {
        return slug;
    }

    public Optional<StringFilter> optionalSlug() {
        return Optional.ofNullable(slug);
    }

    public StringFilter slug() {
        if (slug == null) {
            setSlug(new StringFilter());
        }
        return slug;
    }

    public void setSlug(StringFilter slug) {
        this.slug = slug;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public Optional<BigDecimalFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public BigDecimalFilter price() {
        if (price == null) {
            setPrice(new BigDecimalFilter());
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public BigDecimalFilter getOldPrice() {
        return oldPrice;
    }

    public Optional<BigDecimalFilter> optionalOldPrice() {
        return Optional.ofNullable(oldPrice);
    }

    public BigDecimalFilter oldPrice() {
        if (oldPrice == null) {
            setOldPrice(new BigDecimalFilter());
        }
        return oldPrice;
    }

    public void setOldPrice(BigDecimalFilter oldPrice) {
        this.oldPrice = oldPrice;
    }

    public IntegerFilter getStock() {
        return stock;
    }

    public Optional<IntegerFilter> optionalStock() {
        return Optional.ofNullable(stock);
    }

    public IntegerFilter stock() {
        if (stock == null) {
            setStock(new IntegerFilter());
        }
        return stock;
    }

    public void setStock(IntegerFilter stock) {
        this.stock = stock;
    }

    public StringFilter getSku() {
        return sku;
    }

    public Optional<StringFilter> optionalSku() {
        return Optional.ofNullable(sku);
    }

    public StringFilter sku() {
        if (sku == null) {
            setSku(new StringFilter());
        }
        return sku;
    }

    public void setSku(StringFilter sku) {
        this.sku = sku;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public Optional<BooleanFilter> optionalActive() {
        return Optional.ofNullable(active);
    }

    public BooleanFilter active() {
        if (active == null) {
            setActive(new BooleanFilter());
        }
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public Optional<InstantFilter> optionalCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            setCreatedAt(new InstantFilter());
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public Optional<InstantFilter> optionalUpdatedAt() {
        return Optional.ofNullable(updatedAt);
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            setUpdatedAt(new InstantFilter());
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StringFilter getMetaTitle() {
        return metaTitle;
    }

    public Optional<StringFilter> optionalMetaTitle() {
        return Optional.ofNullable(metaTitle);
    }

    public StringFilter metaTitle() {
        if (metaTitle == null) {
            setMetaTitle(new StringFilter());
        }
        return metaTitle;
    }

    public void setMetaTitle(StringFilter metaTitle) {
        this.metaTitle = metaTitle;
    }

    public StringFilter getMetaDescription() {
        return metaDescription;
    }

    public Optional<StringFilter> optionalMetaDescription() {
        return Optional.ofNullable(metaDescription);
    }

    public StringFilter metaDescription() {
        if (metaDescription == null) {
            setMetaDescription(new StringFilter());
        }
        return metaDescription;
    }

    public void setMetaDescription(StringFilter metaDescription) {
        this.metaDescription = metaDescription;
    }

    public LongFilter getViewsCount() {
        return viewsCount;
    }

    public Optional<LongFilter> optionalViewsCount() {
        return Optional.ofNullable(viewsCount);
    }

    public LongFilter viewsCount() {
        if (viewsCount == null) {
            setViewsCount(new LongFilter());
        }
        return viewsCount;
    }

    public void setViewsCount(LongFilter viewsCount) {
        this.viewsCount = viewsCount;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public Optional<LongFilter> optionalCategoryId() {
        return Optional.ofNullable(categoryId);
    }

    public LongFilter categoryId() {
        if (categoryId == null) {
            setCategoryId(new LongFilter());
        }
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getBrandId() {
        return brandId;
    }

    public Optional<LongFilter> optionalBrandId() {
        return Optional.ofNullable(brandId);
    }

    public LongFilter brandId() {
        if (brandId == null) {
            setBrandId(new LongFilter());
        }
        return brandId;
    }

    public void setBrandId(LongFilter brandId) {
        this.brandId = brandId;
    }

    public LongFilter getPromotionsId() {
        return promotionsId;
    }

    public Optional<LongFilter> optionalPromotionsId() {
        return Optional.ofNullable(promotionsId);
    }

    public LongFilter promotionsId() {
        if (promotionsId == null) {
            setPromotionsId(new LongFilter());
        }
        return promotionsId;
    }

    public void setPromotionsId(LongFilter promotionsId) {
        this.promotionsId = promotionsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(price, that.price) &&
            Objects.equals(oldPrice, that.oldPrice) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(sku, that.sku) &&
            Objects.equals(active, that.active) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(metaTitle, that.metaTitle) &&
            Objects.equals(metaDescription, that.metaDescription) &&
            Objects.equals(viewsCount, that.viewsCount) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(brandId, that.brandId) &&
            Objects.equals(promotionsId, that.promotionsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            slug,
            price,
            oldPrice,
            stock,
            sku,
            active,
            createdAt,
            updatedAt,
            metaTitle,
            metaDescription,
            viewsCount,
            categoryId,
            brandId,
            promotionsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalSlug().map(f -> "slug=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalOldPrice().map(f -> "oldPrice=" + f + ", ").orElse("") +
            optionalStock().map(f -> "stock=" + f + ", ").orElse("") +
            optionalSku().map(f -> "sku=" + f + ", ").orElse("") +
            optionalActive().map(f -> "active=" + f + ", ").orElse("") +
            optionalCreatedAt().map(f -> "createdAt=" + f + ", ").orElse("") +
            optionalUpdatedAt().map(f -> "updatedAt=" + f + ", ").orElse("") +
            optionalMetaTitle().map(f -> "metaTitle=" + f + ", ").orElse("") +
            optionalMetaDescription().map(f -> "metaDescription=" + f + ", ").orElse("") +
            optionalViewsCount().map(f -> "viewsCount=" + f + ", ").orElse("") +
            optionalCategoryId().map(f -> "categoryId=" + f + ", ").orElse("") +
            optionalBrandId().map(f -> "brandId=" + f + ", ").orElse("") +
            optionalPromotionsId().map(f -> "promotionsId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
