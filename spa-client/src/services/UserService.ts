import { paths } from "../common/constants";
import { getBlobFetch, getFetch } from "../scripts/getFetch";
import { CourseModel, PagedContent, Result, UserModel } from "../types";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";
import { postFetch } from "../scripts/postFetch";

export class UserService {
  private readonly basePath = paths.BASE_URL + paths.USERS;

  public async getUsers(): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(this.basePath);
  }

  public async getUserById(userId: number): Promise<Result<UserModel>> {
    return getFetch<UserModel>(this.basePath + "/" + userId);
  }

  public async getLastFileNumber(): Promise<Result<number>> {
    return getFetch<number>(this.basePath + "/file-number/last");
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
  ) {
    const newUser = JSON.stringify({
      fileNumber: fileNumber,
      name: name,
      surname: surname,
      username: username,
      email: email,
      password: password,
      confirmPassword: confirmPassword,
    });

    return postFetch(
      this.basePath,
      "application/vnd.campus.api.v1+json",
      newUser
    );
  }

  public async sendEmail(userId: number, title: string, content: string) {
    const email = JSON.stringify({
      title: title,
      content: content,
    });

    return postFetch(
      this.basePath + "/" + userId + "email",
      "application/vnd.campus.api.v1+json",
      email
    );
  }
}
