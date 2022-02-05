import { isConstructorDeclaration } from "typescript";
import { paths } from "../common/constants";
import { getFetch } from "../scripts/getFetch";
import { Result } from "../types";

export class TimeTableService {
  private readonly basePath = paths.BASE_URL + paths.TIME_TABLE;

  //   public async getTimeTables(): Promise<Result<Map<string, number[]>>> {
  //     // return getFetch<Map<string, number[]>>(this.basePath);
  //   }
}
