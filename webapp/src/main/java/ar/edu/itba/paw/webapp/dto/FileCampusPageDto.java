package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.FileModel;

import java.util.List;
import java.util.stream.Collectors;

public class FileCampusPageDto {

    List<FileModelDto> files;
    int size;
    int total;
    int page;

    public static FileCampusPageDto fromCampusPage(CampusPage<FileModel> fileModelCampusPage){
        if (fileModelCampusPage == null){
            return null;
        }

        FileCampusPageDto dto = new FileCampusPageDto();
        dto.files = fileModelCampusPage.getContent().stream().map(FileModelDto::fromFile).collect(Collectors.toList());
        dto.size = fileModelCampusPage.getSize();
        dto.total = fileModelCampusPage.getTotal();
        dto.page = fileModelCampusPage.getPage();
        return dto;
    }

    public List<FileModelDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileModelDto> files) {
        this.files = files;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
