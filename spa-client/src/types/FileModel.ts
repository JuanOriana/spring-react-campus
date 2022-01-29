import Course from "./Course";
import FileExtension from "./FileExtension";

export default interface FileModel {
  fileId: number;
  size: number;
  extension: FileExtension;
  fileName: string;
  fileDate: Date;
  course: Course;
  downoalds: number;
  hidden: boolean;
  fileUri: string;
  file: File;
}
