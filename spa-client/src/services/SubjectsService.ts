import { paths } from "../common/constants";
import { getFetch } from "../scripts/getFetch";
import { PagedContent, Result, SubjectModel } from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { postFetch } from "../scripts/postFetch";

export class SubjectsService {
  private readonly basePath = paths.BASE_URL + paths.SUBJECTS;

  public async getSubjects(): Promise<Result<PagedContent<SubjectModel[]>>> {
    return getPagedFetch<SubjectModel[]>(this.basePath);
  }

  public async getSubjectById(subjectId: number) {
    return getFetch<SubjectModel>(this.basePath + "/" + subjectId);
  }

  public async newSubject(code: string, name: string) {
    const newSubject = JSON.stringify({
      code: code,
      name: name,
    });

    return postFetch(
      this.basePath,
      "application/vnd.campus.api.v1+json",
      newSubject
    );
  }
}
