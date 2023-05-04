package com.example.springclient.apiUtils;

import java.util.List;

public class FoodFactsResponse {
    private int count;
    private int page;
    private int page_count;
    private int page_size;
    private List<ProdottoResponse> products;

    public FoodFactsResponse(int count, int page, int page_count, int page_size, List<ProdottoResponse> products) {
        this.count = count;
        this.page = page;
        this.page_count = page_count;
        this.page_size = page_size;
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<ProdottoResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProdottoResponse> products) {
        this.products = products;
    }
}
