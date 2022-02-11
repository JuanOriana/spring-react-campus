import { FileModel, PagedContent, Result } from "../types";

export function parseFileResponse(
  files: Result<PagedContent<FileModel[]>>
): Result<PagedContent<FileModel[]>> {
  if (!files.hasFailed()) {
    files
      .getData()
      .getContent()
      .forEach(
        (file) => (file.fileDate = new Date(file.fileDate ? file.fileDate : ""))
      );
  }

  return files;
}
