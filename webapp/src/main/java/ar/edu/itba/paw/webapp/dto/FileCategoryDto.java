package ar.edu.itba.paw.webapp.dto;


import ar.edu.itba.paw.models.FileCategory;

public class FileCategoryDto {

    private long categoryId;
    private String categoryName;

    public static FileCategoryDto fromFileCategory(FileCategory fileCategory){
        if (fileCategory == null){
            return null;
        }

        final FileCategoryDto dto = new FileCategoryDto();
        dto.categoryId = fileCategory.getCategoryId();
        dto.categoryName = fileCategory.getCategoryName();
        return dto;

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
