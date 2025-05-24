package com.geartech.app.dtos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String personalNumber;
    private String name;
    private String lastName;
    private String mail;
    private String phone;
    private String password;
    private LocalDateTime expiration;
    private Boolean resetPassword;
    private Boolean active;
    private LocalDateTime dthrCreate;
    private String codUserCreate;
    private LocalDateTime dthrLastUpdate;
    private String codUserLastUpdate;
    private Integer version;

}
