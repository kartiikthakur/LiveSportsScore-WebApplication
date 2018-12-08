package com.example.demo;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="login")
@EntityListeners(AuditingEntityListener.class)
public class Login {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String fbid;
		private String name;


    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
    }
    
    public String getFbid() {
		return fbid;
	}

	public void setFbid(String fbid) {
		this.fbid = fbid;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}