package ru.borsch.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Disc {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1024)
    private String description;

    private String posterURL;

    @ManyToOne
    private User owner;

    @OneToOne
    @JoinTable(name = "Disk_User",
            joinColumns = @JoinColumn(name = "diskId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
    private User discUser;


    public Disc(String name, String description, User owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    Disc(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public User getDiscUser() {
        return discUser;
    }

    public void setDiscUser(User discUser) {
        this.discUser = discUser;
    }
}
