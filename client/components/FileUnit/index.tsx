import React from "react";
import PropTypes, { InferProps } from "prop-types";
import Link from "next/link";

import {
  FileName,
  FileUnitWrapper,
  FileImg,
  FileCategoryName,
  MediumIcon,
} from "./styles";

FileUnit.propTypes = {
  isGlobal: PropTypes.bool,
  isTeacher: PropTypes.bool,
  isMinimal: PropTypes.bool,
  file: PropTypes.shape({
    fileId: PropTypes.number,
    name: PropTypes.string,
    extension: PropTypes.shape({
      fileExtensionName: PropTypes.string,
    }),
    course: PropTypes.shape({
      courseId: PropTypes.number,
      subject: PropTypes.shape({
        name: PropTypes.string,
      }),
    }),
    categories: PropTypes.array,
    downloads: PropTypes.number,
  }),
};

function FileUnit({
  isGlobal,
  isMinimal,
  isTeacher,
  file,
}: InferProps<typeof FileUnit.propTypes>) {
  return (
    <FileUnitWrapper>
      <div style={{ display: "flex", alignItems: "center" }}>
        <Link href={`/file/${file?.fileId}`} passHref={true}>
          <a
            target="_blank"
            style={{
              display: "flex",
              marginLeft: "10px",
              alignItems: "center",
            }}
          >
            <FileImg
              src={`/resources/images/extensions/${
                file?.extension!.fileExtensionName
              }.png`}
              alt={file && file.name ? file!.name! : "file"}
            />
            <FileName>{file?.name}</FileName>
          </a>
        </Link>
        {!isMinimal &&
          file?.categories?.map((category) => (
            <FileCategoryName key={category.name}>
              {category.name}
            </FileCategoryName>
          ))}
      </div>
      <div style={{ display: "flex", alignItems: "center" }}>
        {!isMinimal && <FileName>{file?.downloads}</FileName>}
        {isTeacher && (
          <MediumIcon src="/resources/images/trash.png" alt="delete" />
        )}
        {isGlobal && (
          <div
            style={{
              paddingLeft: "5px",
              marginLeft: "15px",
              borderLeft: "3px solid white",
            }}
          >
            <Link href={`/course/${file?.course!.courseId}`}>
              <FileName>{file?.course!.subject!.name}</FileName>
            </Link>
          </div>
        )}
      </div>
    </FileUnitWrapper>
  );
}

export default FileUnit;
