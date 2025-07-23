package org.maslov.bot.app.model.tag;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = Tag.TABLE_NAME)
public class Tag {

    public static final String TABLE_NAME = "translation_tag";
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "parentTag")
    private List<TagToTagLink> toParentLinks = new ArrayList<>();


    @OneToMany(mappedBy = "childrenTag")
    private List<TagToTagLink> toChildrenLinks = new ArrayList<>();

    @OneToMany(mappedBy = "tag", orphanRemoval = true)
    private Set<TranslationToTagLink> translationToTagLinks = new LinkedHashSet<>();

    public Set<TranslationToTagLink> getTranslationToTagLinks() {
        return translationToTagLinks;
    }

    public void setTranslationToTagLinks(Set<TranslationToTagLink> translationToTagLinks) {
        this.translationToTagLinks = translationToTagLinks;
    }

    public List<TagToTagLink> getToParentLinks() {
        return toParentLinks;
    }

    public void setToParentLinks(List<TagToTagLink> toParentLinks) {
        this.toParentLinks = toParentLinks;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TagToTagLink> getToChildrenLinks() {
        return toChildrenLinks;
    }

    public void setToChildrenLinks(List<TagToTagLink> toChildrenLinks) {
        this.toChildrenLinks = toChildrenLinks;
    }
}
