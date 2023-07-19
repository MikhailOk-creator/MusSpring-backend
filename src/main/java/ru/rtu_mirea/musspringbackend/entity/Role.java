package ru.rtu_mirea.musspringbackend.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role_t")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_name")
    private String roleName;
}
