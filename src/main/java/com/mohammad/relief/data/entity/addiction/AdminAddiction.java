package com.mohammad.relief.data.entity.addiction;

import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class AdminAddiction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_type", nullable = false)
    private CategoryType categoryType;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
