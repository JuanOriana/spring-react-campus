import React from "react";
import { Link } from "react-router-dom";

import {
  FileName,
  FileUnitWrapper,
  FileImg,
  FileCategoryName,
  MediumIcon,
} from "./styles";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { FileModel } from "../../types";
//

interface FileUnitProps {
  isGlobal?: boolean;
  isTeacher?: boolean;
  isMinimal?: boolean;
  file: FileModel;
}

function FileUnit({ isGlobal, isMinimal, isTeacher, file }: FileUnitProps) {
  const { t } = useTranslation();
  return (
    <FileUnitWrapper>
      <div style={{ display: "flex", alignItems: "center" }}>
        <Link
          to={`/file/${file.fileId}`}
          target={"_blank"}
          style={{
            display: "flex",
            marginLeft: "10px",
            alignItems: "center",
          }}
        >
          <FileImg
            src={`/images/extensions/${file?.extension!.fileExtension}.png`}
            alt={
              file && file.fileName ? file.fileName! : t("FileUnit.alt.file")
            }
          />
          <FileName>{file.fileName}</FileName>
        </Link>
        {!isMinimal &&
          file?.categories?.map((category) => (
            <FileCategoryName key={category.categoryId}>
              {t("Category." + category.categoryName)}
            </FileCategoryName>
          ))}
      </div>
      <div style={{ display: "flex", alignItems: "center" }}>
        {!isMinimal && <FileName>{file.downloads}</FileName>}
        {isTeacher && (
          <MediumIcon src="/images/trash.png" alt={t("FileUnit.alt.delete")} />
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
