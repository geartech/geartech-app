package com.geartech.app.models.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.geartech.app.dtos.model.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
@DynamicUpdate
@DynamicInsert
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "personalNumber", nullable = false, length = 10)
	private String personalNumber;

	@Column(name = "name", nullable = false, length = 60)
	private String name;

	@Column(name = "lastName", nullable = false, length = 20)
	private String lastName;

	@Column(name = "mail", nullable = false, length = 200)
	private String mail;

	@Column(name = "phone", length = 20)
	private String phone;

	@Column(name = "password", nullable = false, length = 20)
	private String password;

	@Column(name = "expiration", nullable = true)
	private LocalDateTime expiration;

	@Column(name = "resetPassword", nullable = true)
	private Boolean resetPassword;

	@Column(name = "active", nullable = false)
	private Boolean active;

	public UserDTO getDTO() {
		return new UserDTO(id, personalNumber, name, lastName, mail, phone, password, expiration,
				resetPassword, active, getDthrCreate(), mail, getDthrLastUpdate(), lastName,
				getVersion());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public Boolean getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(Boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
