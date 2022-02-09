import { paths } from "../common/constants";
import { PagedContent, PostResponse, Result, SubjectModel } from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { resultFetch } from "../scripts/resultFetch";

export class SubjectsService {
  private readonly basePath = paths.BASE_URL + paths.SUBJECTS;

  public async getSubjects(): Promise<Result<PagedContent<SubjectModel[]>>> {
    return getPagedFetch<SubjectModel[]>(this.basePath);
  }

  public async getSubjectById(subjectId: number) {
    return resultFetch<SubjectModel>(this.basePath + "/" + subjectId, {
      method: "GET",
    });
  }

  public async newSubject(
    code: string,
    name: string
  ): Promise<Result<PostResponse>> {
    const newSubject = JSON.stringify({
      code: code,
      name: name,
    });

    return resultFetch<PostResponse>(this.basePath, {
      method: "POST",
      headers: {
        "Content-Type": "application/vnd.campus.api.v1+json",
      },
      body: newSubject,
    });
  }
}
