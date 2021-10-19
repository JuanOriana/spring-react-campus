package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

@Entity
@Table(name = "category_file_relationship")
public class CategoryFileRelationship{

    @EmbeddedId
    private CategoryFileRelationshipId categoryFileRelationshipId;

    @ManyToOne(targetEntity = FileCategory.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private FileCategory fileCategory;

    @ManyToOne(targetEntity=FileModel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "fileId")
    private FileModel fileModel;

    /*Default */ CategoryFileRelationship() {
        //Just for hibernate
    }

    public CategoryFileRelationship(FileCategory fileCategory, FileModel fileModel) {
        this.fileCategory = fileCategory;
        this.fileModel = fileModel;
    }

    public FileCategory getFileCategory() {
        return fileCategory;
    }

    public void setFileCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    private static class CategoryFileRelationshipId implements Serializable{

        private FileCategory fileCategory;
        private FileModel fileModel;

        public FileCategory getFileCategory() {
            return fileCategory;
        }

        public void setFileCategory(FileCategory fileCategory) {
            this.fileCategory = fileCategory;
        }

        public FileModel getFileModel() {
            return fileModel;
        }

        public void setFileModel(FileModel fileModel) {
            this.fileModel = fileModel;
        }
    }
}
