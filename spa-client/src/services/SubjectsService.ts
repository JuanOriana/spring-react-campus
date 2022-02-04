import { paths } from "../common/constants";
import { getFetch } from "../scripts/getFetch";
import { PagedContent, Result, SubjectModel } from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";

export class SubjectsService {
  private readonly basePath = paths.BASE_URL + paths.SUBJECTS;

  public async getSubjects(): Promise<Result<PagedContent<SubjectModel[]>>> {
    return getPagedFetch<SubjectModel[]>(this.basePath);
  }

  public async getSubjectById(subjectId: number) {
    return getFetch<SubjectModel>(this.basePath + "/" + subjectId);
  }
}
