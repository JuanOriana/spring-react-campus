import { APPLICATION_V1_JSON_TYPE, paths } from "../common/constants";
import { getBlobFetch } from "../scripts/getBlobFetch";
import {
  CourseModel,
  ErrorResponse,
  PagedContent,
  PostResponse,
  PutResponse,
  Result,
  RoleModel,
  UserCourseModel,
  UserModel,
} from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";
import { resultFetch } from "../scripts/resultFetch";

export class UserService {
  private readonly basePath = paths.BASE_URL + paths.USERS;

  public async getUsers(
    page?: number,
    pageSize?: number,
    excludeCourseId?: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    const url = pageUrlMaker(this.basePath, page, pageSize);
    if (typeof excludeCourseId !== "undefined") {
      url.searchParams.append("directive", "exclude");
      url.searchParams.append("courseId", excludeCourseId.toString());
    }
    return getPagedFetch<UserModel[]>(url.toString());
  }

  public async getUsersUnpaged(
    batchSize: number,
    excludeCourseId?: number
  ): Promise<Result<UserModel[]>> {
    let currentPage = 1;
    let maxPage = 1;
    const allResults: UserModel[] = [];
    while (currentPage <= maxPage) {
      const batch = await this.getUsers(
        currentPage,
        batchSize,
        excludeCourseId
      );
      if (batch.hasFailed()) return Result.failed(batch.getError());
      allResults.push(...batch.getData().getContent());
      maxPage = batch.getData().getMaxPage();
      currentPage++;
    }
    return Result.ok(allResults);
  }

  public async getUserById(userId: number): Promise<Result<UserModel>> {
    return resultFetch<UserModel>(this.basePath + "/" + userId, {
      method: "GET",
    });
  }

  public async getLastFileNumber(): Promise<
    Result<{ nextFileNumber: number }>
  > {
    return resultFetch<{ nextFileNumber: number }>(
      this.basePath + "/file-number/last",
      {
        method: "GET",
      }
    );
  }

  public async getUserProfileImage(userId: number): Promise<Result<Blob>> {
    return getBlobFetch(this.basePath + "/" + userId + "/image");
  }

  public async getUserCourses(
    userId: number,
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<UserCourseModel[]>>> {
    let url = pageUrlMaker(
      this.basePath + "/" + userId + "/courses",
      page,
      pageSize
    );
    return getPagedFetch<UserCourseModel[]>(url.toString());
  }

  public async newUser(
    fileNumber: number,
    name: string,
    surname: string,
    username: string,
    email: string,
    password: string,
    confirmPassword: string
  ): Promise<Result<PostResponse>> {
    const newUser = JSON.stringify({
      fileNumber: fileNumber,
      name: name,
      surname: surname,
      username: username,
      email: email,
      password: password,
      confirmPassword: confirmPassword,
    });

    if (confirmPassword !== password) {
      return Result.failed(
        new ErrorResponse(422, "Confirm password must match with password")
      );
    }

    return resultFetch<PostResponse>(this.basePath, {
      method: "POST",
      headers: {
        "Content-Type": APPLICATION_V1_JSON_TYPE,
      },
      body: newUser,
    });
  }

  public async sendEmail(
    userId: number,
    courseId: number,
    title: string,
    content: string
  ) {
    if (title.length < 1 || content.length < 1) {
      return Result.failed(
        new ErrorResponse(
          422,
          "Title and content must have more than 1 character"
        )
      );
    }
    const email = JSON.stringify({
      title: title,
      content: content,
      courseId: courseId,
    });

    return resultFetch<PostResponse>(this.basePath + "/" + userId + "/mail", {
      method: "POST",
      headers: {
        "Content-Type": APPLICATION_V1_JSON_TYPE,
      },
      body: email,
    });
  }

  public async getTimeTable(userId: number): Promise<Result<CourseModel[][]>> {
    return resultFetch<CourseModel[][]>(
      this.basePath + "/" + userId + "/timetable",
      { method: "GET" }
    );
  }

  public async updateUserProfileImage(
    userId: number,
    file: File
  ): Promise<Result<PutResponse>> {
    const formData = new FormData();
    formData.append("file", file, file.name);
    return resultFetch<PutResponse>(this.basePath + "/" + userId + "/image", {
      method: "PUT",
      headers: {},
      body: formData,
    });
  }

  public async getRoles(): Promise<Result<RoleModel[]>> {
    return resultFetch<RoleModel[]>(this.basePath + "/roles", {
      method: "GET",
    });
  }
}
