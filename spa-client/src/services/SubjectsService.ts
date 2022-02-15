import { paths } from "../common/constants";
import { PagedContent, PostResponse, Result, SubjectModel } from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { resultFetch } from "../scripts/resultFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";

export class SubjectsService {
  private readonly basePath = paths.BASE_URL + paths.SUBJECTS;

  public async getSubjects(
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<SubjectModel[]>>> {
    let url = pageUrlMaker(this.basePath, page, pageSize);
    return getPagedFetch<SubjectModel[]>(url.toString());
  }

  public async getSubjectsUnpaged(
    batchSize: number
  ): Promise<Result<SubjectModel[]>> {
    let currentPage = 1;
    let maxPage = 1;
    const allResults: SubjectModel[] = [];
    while (currentPage <= maxPage) {
      const batch = await this.getSubjects(currentPage, batchSize);
      if (batch.hasFailed()) return Result.failed(batch.getError());
      allResults.push(...batch.getData().getContent());
      maxPage = batch.getData().getMaxPage();
      currentPage++;
    }
    return Result.ok(allResults);
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
