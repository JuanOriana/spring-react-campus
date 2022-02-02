import { paths } from "../common/constants";
import { authedFetch } from "../scripts/authedFetch";
import { getFetch } from "../scripts/getFetch";
import { Result, UserModel } from "../types";

export class UserService {
  private readonly basePath = paths.BASE_URL + paths.USERS;

  public async getUsers(): Promise<Result<UserModel[]>> {
    return getFetch<UserModel[]>(this.basePath);
  }

  public async getUserById(userId: number): Promise<Result<UserModel>> {
    return getFetch<UserModel>(this.basePath + userId);
  }

  public async getLastFileNumber(): Promise<Result<number>> {
    return getFetch<number>(this.basePath + "last/file-number");
  }

  public async getUserProfileImage(userId: number): Promise<Result<File>> {
    return getFetch<File>(this.basePath + userId + "/profile-image");
  }

  public async newUSer(
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

    const headers = new Headers();
    headers.append("Content-Type", "application/json");

    authedFetch(this.basePath, {
      method: "POST",
      headers: headers,
      body: newUser,
    });
  }
}
