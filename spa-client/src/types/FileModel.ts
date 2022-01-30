import CourseModel from "./CourseModel";
import FileExtension from "./FileExtensionModel";

export default interface FileModel {
  fileId: number;
  size: number;
  extension: FileExtension;
  fileName: string;
  fileDate: Date;
  course: CourseModel;
  downoalds: number;
  hidden: boolean;
  fileUri: string;
  file: File;
}
