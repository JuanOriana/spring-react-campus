export default interface UserModel {
  userId: number;
  name: string;
  surname: string;
  username: string;
  email: string;
  fileNumber: number;
  token?: string;
  url?:string;
  isAdmin:boolean;
}
