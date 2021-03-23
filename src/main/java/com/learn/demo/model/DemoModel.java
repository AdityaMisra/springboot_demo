package com.learn.demo.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


/**
 * Created on 15/3/21, 9:52 PM
 * DemoModel.java
 *
 * @author aditya.misra
 */


@Entity
@Table(name = "demo")
public class DemoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "demo_id")
    private String demoId;

    @Column(name = "demo_name")
    private String demoName;

    @Column(name = "demo_user")
    private String demoUser;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_modified")
    private Date dateModified;
}
