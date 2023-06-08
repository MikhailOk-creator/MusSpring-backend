package ru.rtu_mirea.musspringbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDTO {
    private String newEmail;
    private String newUsername;
    private String newRealName;
    private String newSurname;
}
