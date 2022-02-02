import { paths } from "../common/constants";
import { getFetch } from "../scripts/getFetch";
import { Result, SubjectModel } from "../types";

export class SubjectsService {
  private readonly basePath = paths.BASE_URL + paths.SUBJECTS;
  public async getSubjects(): Promise<Result<SubjectModel[]>> {
    return getFetch<SubjectModel[]>(this.basePath);
  }

  public async getSubjectById(subjectId: number) {
    return getFetch<SubjectModel>(this.basePath + subjectId);
  }
}
