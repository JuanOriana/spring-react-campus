package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "files")
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_fileid_seq")
    @SequenceGenerator(name = "files_fileid_seq", sequenceName = "files_fileid_seq", allocationSize = 1)
    private Long fileId;

    @Column(name="filesize")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "fileExtensionId")
    private FileExtension extension;

    @Column(name="filename")
    private String fileName;

    @Column
    private LocalDateTime fileDate;

    @Column
    @Basic(fetch = FetchType.LAZY) // Pull the blob only when necessary!!
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @Column
    private Long downloads;

    @Column
    private Boolean hidden;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_file_relationship",
            joinColumns = @JoinColumn(name = "fileId"),
            inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    private List<FileCategory> fileCategories;

    /* default */ FileModel() {
        //For Hibernate
    }

    public static class Builder {
        private Long fileId;
        private Long size;
        private FileExtension extension;
        private String fileName;
        private LocalDateTime fileDate;
        private byte[] file;
        private Course course;
        private Long downloads;
        private List<FileCategory> fileCategories;
        private Boolean hidden = false;

        public Builder() {
        }

        Builder(Long fileId, Long size, FileExtension extension, String fileName, LocalDateTime fileDate, byte[] file,
                Course course, Long downloads, List<FileCategory> fileCategories, Boolean hidden) {
            this.fileId = fileId;
            this.size = size;
            this.extension = extension;
            this.fileName = fileName;
            this.fileDate = fileDate;
            this.file = file;
            this.course = course;
            this.downloads = downloads;
            this.fileCategories = fileCategories;
            this.hidden = hidden;
        }

        public Builder withFileId(Long fileId){
            this.fileId = fileId;
            return Builder.this;
        }

        public Builder withSize(Long size){
            this.size = size;
            return Builder.this;
        }

        public Builder withExtension(FileExtension extension){
            this.extension = extension;
            return Builder.this;
        }

        public Builder withName(String fileName){
            this.fileName = fileName;
            return Builder.this;
        }

        public Builder withDate(LocalDateTime fileDate){
            this.fileDate = fileDate;
            return Builder.this;
        }

        public Builder withFile(byte[] file){
            this.file = file;
            return Builder.this;
        }

        public Builder withCourse(Course course){
            this.course = course;
            return Builder.this;
        }

        public Builder withDownloads(Long downloads) {
            this.downloads = downloads;
            return Builder.this;
        }

        public Builder withCategories(List<FileCategory> fileCategories) {
            this.fileCategories = fileCategories;
            return Builder.this;
        }

        public Builder isHidden(Boolean hidden) {
            this.hidden = hidden;
            return Builder.this;
        }

        public Builder withoutFileData() {
            this.file = null;
            return Builder.this;
        }

        public FileModel build() {
            if(this.size == null){
                throw new NullPointerException("The property \"size\" is null. "
                        + "Please set the value by \"size()\". "
                        + "The properties \"size\", \"extension\", \"fileName\", \"fileDate\" and \"course\" are required.");
            }
            if(this.extension == null){
                throw new NullPointerException("The property \"extension\" is null. "
                        + "Please set the value by \"extension()\". "
                        + "The properties \"size\", \"extension\", \"fileName\", \"fileDate\" and \"course\" are required.");
            }
            if(this.fileName == null){
                throw new NullPointerException("The property \"fileName\" is null. "
                        + "Please set the value by \"fileName()\". "
                        + "The properties \"size\", \"extension\", \"fileName\", \"fileDate\" and \"course\" are required.");
            }
            if(this.fileDate == null){
                throw new NullPointerException("The property \"fileDate\" is null. "
                        + "Please set the value by \"fileDate()\". "
                        + "The properties \"size\", \"extension\", \"fileName\", \"fileDate\" and \"course\" are required.");
            }
            if(this.course == null){
                throw new NullPointerException("The property \"course\" is null. "
                        + "Please set the value by \"course()\". "
                        + "The properties \"size\", \"extension\", \"fileName\", \"fileDate\" and \"course\" are required.");
            }

            return new FileModel(this);
        }
    }

    private FileModel(Builder builder) {
        this.fileId = builder.fileId;
        this.size = builder.size;
        this.extension = builder.extension;
        this.fileName = builder.fileName;
        this.fileDate = builder.fileDate;
        this.file = builder.file;
        this.course = builder.course;
        this.downloads = builder.downloads;
        this.fileCategories = builder.fileCategories;
        this.hidden = builder.hidden;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public FileExtension getExtension() {
        return extension;
    }

    public void setExtension(FileExtension extension) {
        this.extension = extension;
    }

    public String getName() {
        return fileName;
    }

    public void setName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getDate() {
        return fileDate;
    }

    public void setDate(LocalDateTime fileDate) {
        this.fileDate = fileDate;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
    }

    public List<FileCategory> getCategories() {
        return fileCategories;
    }

    public void setCategories(List<FileCategory> fileCategories) {
        this.fileCategories = fileCategories;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public void merge(FileModel fileModel) {
        this.fileName = this.fileName.equals(fileModel.getName())  ? this.fileName : fileModel.getName();
        this.extension = this.extension.equals(fileModel.getExtension()) ? this.extension : fileModel.extension;
        this.size = this.size.equals(fileModel.getSize()) ? this.size : fileModel.size;
        this.fileDate = this.fileDate.equals(fileModel.getDate()) ? this.fileDate : fileModel.fileDate;
        this.file = Arrays.equals(this.file, fileModel.getFile()) ? this.file : fileModel.file;
        this.course = this.course.equals(fileModel.getCourse()) ? this.course : fileModel.course;
        this.downloads = this.downloads.equals(fileModel.getDownloads()) ? this.downloads : fileModel.downloads;
        this.fileCategories = this.fileCategories.equals(fileModel.getCategories()) ? this.fileCategories : fileModel.fileCategories;
        this.hidden = this.hidden.equals(fileModel.isHidden()) ? this.hidden : fileModel.hidden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileModel fileModel = (FileModel) o;
        return fileId.equals(fileModel.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId);
    }
}
