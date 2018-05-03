package org.hibernate.bugs;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "organizations")
public class Organization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_user_id", referencedColumnName = "id")
    private User primaryUser;

    @Basic(fetch = FetchType.LAZY)
    @Column(length = 102400)
    private byte[] logo;
    
    @OneToMany(mappedBy = "organization", cascade=CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private Set<User> users = new HashSet<>();

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "orgOwnerId", nullable = true)
    private User orgOwner;

    public Organization() {
    }

    public Organization(int orgId) {
        this.id = orgId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public User getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(User primaryUser) {
        this.primaryUser = primaryUser;
    }

    public byte[] getLogo() {
        return logo != null ? logo.clone() : null;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo != null ? logo.clone() : null;
    }

    public User getOrgOwner() {
        return orgOwner;
    }

    public void setOrgOwner(User orgOwner) {
        this.orgOwner = orgOwner;
    }
}
