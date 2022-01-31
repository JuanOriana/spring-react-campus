import CourseModel from "./CourseModel";
import FileExtension from "./FileExtensionModel";
import FileCategoryModel from "./FileCategoryModel";

export default interface FileModel {
  fileId: number;
  size?: number;
  extension: FileExtension;
  categories: FileCategoryModel[];
  fileName?: string;
  fileDate?: Date;
  course: CourseModel;
  downloads: number;
  hidden?: boolean;
  fileUri?: string;
  file?: File;
}
