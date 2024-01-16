package ru.rtu_mirea.musspringbackend.entity;

import lombok.Data;

import javax.persistence.*;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_SUPER_ADMIN
}
