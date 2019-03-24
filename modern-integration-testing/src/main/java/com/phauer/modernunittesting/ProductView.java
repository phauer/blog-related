package com.phauer.modernunittesting;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Hello Vaadin")
public class ProductView extends VerticalLayout {

    private ProductDAO dao;

    private Grid<ProductModel> grid;

    public ProductView(ProductDAO dao) {
        this.dao = dao;
        initView();
    }

    private void initView() {
        Button button = new Button("Load Products");
        button.addClickListener(this::loadButtonHandler);

        this.grid = new Grid<>(ProductModel.class);

        add(button, grid);
    }

    private void loadButtonHandler(ClickEvent event) {
        var products = dao.findProducts();
        grid.setItems(toModel(products));
    }

    private List<ProductModel> toModel(List<ProductEntity> products) {
        return products.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private ProductModel toModel(ProductEntity entity) {
        return new ProductModel()
                .setId(entity.getId())
                .setName(entity.getName());
    }
}
