package za.co.momentum.exception;

import lombok.Getter;

@Getter
public enum ProductType {
    RETIREMENT(1, "401k Retirement", "Pension Fund"),
    SAVINGS(2, "Super Savings", "Savings account at 10% interest p/a");

    private final int productTypeId;
    private final String name;
    private final String description;

    ProductType(int productTypeId, String name, String description) {
        this.productTypeId = productTypeId;
        this.name = name;
        this.description = description;
    }
}
