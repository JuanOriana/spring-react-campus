import CourseModel from "./CourseModel";
import FileExtension from "./FileExtensionModel";
import FileCategoryModel from "./FileCategoryModel";

export default interface FileModel {
  fileId: number;
  size?: number;
  extension: FileExtension;
  fileCategory?: FileCategoryModel;
  fileName?: string;
  fileDate?: Date;
  course: CourseModel;
  downloads: number;
  hidden?: boolean;
  link: [{ href: string }];
  file?: File;
}
