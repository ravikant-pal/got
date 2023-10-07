package org.house.got.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "character_image_url")
    private String characterImageUrl;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "favorite",columnDefinition = "boolean default false")
    private boolean favorite;

    @ManyToMany(mappedBy = "characters")
    private List<House> houses = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_id")
    private Character parent;

    @OneToMany(mappedBy = "parent")
    private List<Character> children = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "killed_characters",
            joinColumns = @JoinColumn(name = "killer_id"),
            inverseJoinColumns = @JoinColumn(name = "victim_id")
    )
    private List<Character> killed = new ArrayList<>();
}

