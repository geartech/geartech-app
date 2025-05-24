package com.geartech.app.models.entities;

import java.time.LocalDateTime;
import java.util.function.Function;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.geartech.app.components.AuditListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class BaseEntity {

    protected abstract Long getId();

    @CreatedDate
    @Column(name = "dthrCreate")
    private LocalDateTime dthrCreate;

    @CreatedBy
    @Column(name = "codUserCreate")
    private String codUserCreate;

    @CreatedDate
    @LastModifiedDate
    @Column(name = "dthrLastUpdate")
    private LocalDateTime dthrLastUpdate;

    @CreatedBy
    @LastModifiedBy
    @Column(name = "codUserLastUpdate")
    private String codUserLastUpdate;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;


    public LocalDateTime getDthrCreate() {
        return dthrCreate;
    }

    public void setDthrCreate(LocalDateTime dthrCreate) {
        this.dthrCreate = dthrCreate;
    }

    public String getCodUserCreate() {
        return codUserCreate;
    }

    public void setCodUserCreate(String codUserCreate) {
        this.codUserCreate = codUserCreate;
    }

    public LocalDateTime getDthrLastUpdate() {
        return dthrLastUpdate;
    }

    public void setDthrLastUpdate(LocalDateTime dthrLastUpdate) {
        this.dthrLastUpdate = dthrLastUpdate;
    }

    public String getCodUserLastUpdate() {
        return codUserLastUpdate;
    }

    public void setCodUserLastUpdate(String codUserLastUpdate) {
        this.codUserLastUpdate = codUserLastUpdate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Returns the entity or null
     * 
     * @param <T>
     * @param <R>
     * @param entity
     * @param mapper
     * @return
     */
    public static <T, R> R mapEntity(T entity, Function<T, R> mapper) {
        return entity != null ? mapper.apply(entity) : null;
    }

    /**
     * Return id entity if it exists
     * 
     * @param entity
     * @return
     */
    public Long id(BaseEntity entity) {
        if (entity != null) {
            return entity.getId();
        }
        return null;
    }

}
