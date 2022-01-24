import React from "react";
import PropTypes, { InferProps } from "prop-types";
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
//

FileUnit.propTypes = {
  isGlobal: PropTypes.bool,
  isTeacher: PropTypes.bool,
  isMinimal: PropTypes.bool,
  file: PropTypes.shape({
    fileId: PropTypes.number,
    name: PropTypes.string,
    extension: PropTypes.shape({
      fileExtensionName: PropTypes.string,
    }).isRequired,
    course: PropTypes.shape({
      courseId: PropTypes.number,
      subject: PropTypes.shape({
        name: PropTypes.string,
      }).isRequired,
    }).isRequired,
    categories: PropTypes.array,
    downloads: PropTypes.number,
  }).isRequired,
};

function FileUnit({
  isGlobal,
  isMinimal,
  isTeacher,
  file,
}: InferProps<typeof FileUnit.propTypes>) {
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
            src={`/images/extensions/${file?.extension!.fileExtensionName}.png`}
            alt={file && file.name ? file.name! : t('FileUnit.alt.file') }
          />
          <FileName>{file.name}</FileName>
        </Link>
        {!isMinimal &&
          file?.categories?.map((category) => (
            <FileCategoryName key={category.name}>
              {t('Category.' + category.name)}
            </FileCategoryName>
          ))}
      </div>
      <div style={{ display: "flex", alignItems: "center" }}>
        {!isMinimal && <FileName>{file.downloads}</FileName>}
        {isTeacher && <MediumIcon src="/images/trash.png" alt={t('FileUnit.alt.delete')} />}
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
