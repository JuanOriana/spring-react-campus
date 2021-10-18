package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "file_categories")
public class FileCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filecategory_categoryid_seq")
    @SequenceGenerator(name = "filecategory_categoryid_seq", sequenceName = "filecategory_categoryid_seq", allocationSize = 1)
    private long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    /* Default */ FileCategory() {
        // Just for Hibernate
    }

    public FileCategory(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public FileCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
