package com.ecommerce.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name")) //Thể hiện tính ràng buộc trên bảng categories là field "name" là duy nhất
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;
    private boolean is_deleted;
    private boolean is_activated;

    public Category(String name) {
        this.name = name;
        this.is_deleted = false;
        this.is_activated = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_activated() {
        return is_activated;
    }

    public void setIs_activated(boolean is_activated) {
        this.is_activated = is_activated;
    }
}
