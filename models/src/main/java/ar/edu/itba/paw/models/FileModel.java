package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@SecondaryTables({
        @SecondaryTable(name = "file_extensions", pkJoinColumns = @PrimaryKeyJoinColumn(name = "fileExtensionId")),
        @SecondaryTable(name = "courses", pkJoinColumns = @PrimaryKeyJoinColumn(name = "courseId")),
        @SecondaryTable(name = "file_categories", pkJoinColumns = @PrimaryKeyJoinColumn(name = "categoryId")),
})
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "files_fileid_seq")
    @SequenceGenerator(name = "files_fileid_seq", sequenceName = "files_fileid_seq", allocationSize = 1)
    private Long fileId;

    @Column(name="filesize")
    private Long size;

    @ManyToOne
    @JoinColumn(name = "fileextensionId", insertable = false, updatable = false)
    private FileExtension extension;

    @Column(name="filename")
    private String name;

    @Column(name="filedate")
    private LocalDateTime date;

    @Column
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "courseId", insertable = false, updatable = false)
    private Course course;

    @Column
    private Long downloads;

    @ManyToOne
    @JoinColumn(name = "file_categories", insertable = false, updatable = false)
    private FileCategory fileCategory;

    public static class Builder {
        private Long fileId;
        private Long size;
        private FileExtension extension;
        private String name;
        private LocalDateTime date;
        private byte[] file;
        private Course course;
        private Long downloads;
        private FileCategory fileCategory;
        public Builder() {
        }

        Builder(Long fileId, Long size, FileExtension extension, String name, LocalDateTime date, byte[] file,
                Course course, Long downloads, FileCategory fileCategory) {
            this.fileId = fileId;
            this.size = size;
            this.extension = extension;
            this.name = name;
            this.date = date;
            this.file = file;
            this.course = course;
            this.downloads = downloads;
            this.fileCategory = fileCategory;
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

        public Builder withName(String name){
            this.name = name;
            return Builder.this;
        }

        public Builder withDate(LocalDateTime date){
            this.date = date;
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

        public Builder withCategory(FileCategory fileCategory) {
            this.fileCategory = fileCategory;
            return Builder.this;
        }

        public Builder withoutFileData() {
            this.file = null;
            return Builder.this;
        }

        public FileModel build() {
            if(this.fileId == null){
                throw new NullPointerException("The property \"fileId\" is null. "
                        + "Please set the value by \"fileId()\". "
                        + "The properties \"fileId\", \"size\", \"extension\", \"name\", \"date\" and \"course\" are required.");
            }
            if(this.size == null){
                throw new NullPointerException("The property \"size\" is null. "
                        + "Please set the value by \"size()\". "
                        + "The properties \"fileId\", \"size\", \"extension\", \"name\", \"date\" and \"course\" are required.");
            }
            if(this.extension == null){
                throw new NullPointerException("The property \"extension\" is null. "
                        + "Please set the value by \"extension()\". "
                        + "The properties \"fileId\", \"size\", \"extension\", \"name\", \"date\" and \"course\" are required.");
            }
            if(this.name == null){
                throw new NullPointerException("The property \"name\" is null. "
                        + "Please set the value by \"name()\". "
                        + "The properties \"fileId\", \"size\", \"extension\", \"name\", \"date\" and \"course\" are required.");
            }
            if(this.date == null){
                throw new NullPointerException("The property \"date\" is null. "
                        + "Please set the value by \"date()\". "
                        + "The properties \"fileId\", \"size\", \"extension\", \"name\", \"date\" and \"course\" are required.");
            }
            if(this.course == null){
                throw new NullPointerException("The property \"course\" is null. "
                        + "Please set the value by \"course()\". "
                        + "The properties \"fileId\", \"size\", \"extension\", \"name\", \"date\" and \"course\" are required.");
            }

            return new FileModel(this);
        }
    }

    private FileModel(Builder builder) {
        this.fileId = builder.fileId;
        this.size = builder.size;
        this.extension = builder.extension;
        this.name = builder.name;
        this.date = builder.date;
        this.file = builder.file;
        this.course = builder.course;
        this.downloads = builder.downloads;
        this.fileCategory = builder.fileCategory;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public FileCategory getCategory() {
        return fileCategory;
    }

    public void setCategory(FileCategory fileCategory) {
        this.fileCategory = fileCategory;
    }
}
