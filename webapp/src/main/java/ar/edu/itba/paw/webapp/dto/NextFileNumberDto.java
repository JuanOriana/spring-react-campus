package ar.edu.itba.paw.webapp.dto;

public class NextFileNumberDto {

    private Integer nextFileNumber;

    public static NextFileNumberDto fromNextFileNumber(Integer number){
        if (number == null){
            return null;
        }

        NextFileNumberDto dto = new NextFileNumberDto();
        dto.nextFileNumber = number;
        return dto;
    }

    public Integer getNextFileNumber() {
        return nextFileNumber;
    }

    public void setNextFileNumber(Integer nextFileNumber) {
        this.nextFileNumber = nextFileNumber;
    }
}
