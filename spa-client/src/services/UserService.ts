import { paths } from "../common/constants";
import { getBlobFetch } from "../scripts/getFetch";
import {
  CourseModel,
  ErrorResponse,
  PagedContent,
  PostResponse,
  Result,
  UserModel,
} from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";
import { resultFetch } from "../scripts/resultFetch";

export class UserService {
  private readonly basePath = paths.BASE_URL + paths.USERS;

  public async getUsers(): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(this.basePath);
  }

  public async getUserById(userId: number): Promise<Result<UserModel>> {
    return resultFetch<UserModel>(this.basePath + "/" + userId, {
      method: "GET",
    });
  }

  public async getLastFileNumber(): Promise<Result<number>> {
    return resultFetch<number>(this.basePath + "/file-number/last", {
      method: "GET",
    });
  }

  public async getUserProfileImage(userId: number): Promise<Result<Blob>> {
    return getBlobFetch(this.basePath + "/" + userId + "/image");
  }

  public async getUsersCourses(
    userId: number,
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<CourseModel[]>>> {
    let url = pageUrlMaker(
      this.basePath + "/" + userId + "/courses",
      page,
      pageSize
    );
    return getPagedFetch(url.toString());
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
        "Content-Type": "application/vnd.campus.api.v1+json",
      },
      body: newUser,
    });
  }

  public async sendEmail(userId: number, title: string, content: string) {
    const email = JSON.stringify({
      title: title,
      content: content,
    });

    return resultFetch<PostResponse>(this.basePath + "/" + userId + "/email", {
      method: "POST",
      headers: {
        "Content-Type": "application/vnd.campus.api.v1+json",
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

  public async updateUserProfileImage(userId: number, file: File) {
    const formData = new FormData();

    formData.append("file", file, file.name);
    return resultFetch(this.basePath + "/" + userId + "/image", {
      method: "PUT",
      headers: {
        "Content-Type": "multipart/form-data",
      },
      body: formData,
    });
  }
}
