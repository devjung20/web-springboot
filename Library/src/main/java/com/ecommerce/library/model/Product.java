package com.ecommerce.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "image"}))
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    @Lob //có kiểu dữ liệu như văn bản dài, dữ liệu nhị phân lớn (blob), hoặc hình ảnh lớn (DL lớn).
    @Column(columnDefinition = "MEDIUMBLOB") //KDL trả về của image
    private String image;

    //cascade được sử dụng để chỉ định các hoạt động mà Hibernate sẽ
    //tự động lưu chủ (owner) của mối quan hệ khi thực hiện các hoạt động liên quan
    //CascadeType.ALL cho phép các hoạt động lưu chủ (owner) của mối quan hệ,
    // bao gồm cả tạo mới (persist), cập nhật (update), xóa (delete) và merge.
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //Hibernate sẽ tự động truy xuất dữ liệu của mối quan hệ này khi tải đối tượng chứa nó
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;
    private boolean is_activated;
    private boolean is_deleted;
}
