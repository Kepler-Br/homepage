package ru.keplerbr.homepage.data.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern.Flag;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Pattern;

@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    @Pattern(regexp = "(?U)\\w+")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles;

    public Tag() {
    }

    public Tag(@NonNull String name) {
        this.name = name;
    }
}
