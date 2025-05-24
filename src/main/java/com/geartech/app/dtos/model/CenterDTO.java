package com.geartech.app.dtos.model;

import java.time.LocalDateTime;

import com.geartech.app.enums.ManagementType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CenterDTO {
    private Long id;
    private Long parentId;
    private Integer code;
    private String codeExternal;
    private String name;
    private String description;
    private ManagementType managementType;
    private Boolean active;
    private LocalDateTime dthrCreate;
    private String codUserCreate;
    private LocalDateTime dthrLastUpdate;
    private String codUserLastUpdate;
    private Integer version;

}
