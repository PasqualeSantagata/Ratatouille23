package com.example.springclient.apiUtils;

public class ProdottoResponse {
    private String product_name;
    private String brands;
    private String generic_name;
    private String allergens;

    public ProdottoResponse(String product_name, String brands, String generic_name, String allergens) {
        this.product_name = product_name;
        this.brands = brands;
        this.generic_name = generic_name;
        this.allergens = allergens;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getGeneric_name() {
        return generic_name;
    }

    public void setGeneric_name(String generic_name) {
        this.generic_name = generic_name;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
}
