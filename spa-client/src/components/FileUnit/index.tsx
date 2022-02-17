import React from "react";
import { Link } from "react-router-dom";

import {
  FileName,
  FileUnitWrapper,
  FileImg,
  FileCategoryName,
  MediumIcon,
} from "./styles";
import { FileModel } from "../../types";
import { saveAs } from "file-saver";
import { fileService } from "../../services";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";

//

interface FileUnitProps {
  isGlobal?: boolean;
  isTeacher?: boolean;
  isMinimal?: boolean;
  onDelete?: (id: number) => void;
  file: FileModel;
}

function FileUnit({
  isGlobal,
  isMinimal,
  isTeacher,
  file,
  onDelete,
}: FileUnitProps) {
  const { t } = useTranslation();

  function downloadFile() {
    fileService.getFileById(file.fileId).then((blob) => {
      if (!blob.hasFailed()) saveAs(blob.getData(), file.fileName);
    });
  }
  return (
    <FileUnitWrapper>
      <div style={{ display: "flex", alignItems: "center" }}>
        <div
          style={{
            display: "flex",
            marginLeft: "10px",
            alignItems: "center",
            cursor: "pointer",
          }}
          onClick={() => downloadFile()}
        >
          <FileImg
            src={`/images/extensions/${file?.extension!.fileExtensionName}.png`}
            alt={
              file && file.fileName ? file.fileName! : t("FileUnit.alt.file")
            }
          />
          <FileName>{file.fileName}</FileName>
        </div>
        {!isMinimal && file.fileCategory && (
          <FileCategoryName key={file.fileCategory.categoryId}>
            {t("Category." + file.fileCategory.categoryName)}
          </FileCategoryName>
        )}
      </div>
      <div style={{ display: "flex", alignItems: "center" }}>
        {!isMinimal && (
          <FileName>
            {t("FileUnit.downloads", { downloads: file.downloads })}
          </FileName>
        )}
        {isTeacher && (
          <MediumIcon
            src="./images/trash.png"
            alt={t("FileUnit.alt.delete")}
            onClick={() => onDelete!(file.fileId)}
          />
        )}
        {isGlobal && (
          <div
            style={{
              paddingLeft: "5px",
              marginLeft: "15px",
              borderLeft: "3px solid white",
            }}
          >
            <Link to={`/course/${file.course.courseId}`}>
              <FileName>{file.course.subject.name}</FileName>
            </Link>
          </div>
        )}
      </div>
    </FileUnitWrapper>
  );
}

export default FileUnit;
