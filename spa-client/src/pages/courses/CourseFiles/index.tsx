import {
  BigWrapper,
  GeneralTitle,
  SectionHeading,
} from "../../../components/generalStyles/utils";
import { getQueryOrDefault, useQuery } from "../../../hooks/useQuery";
import { useNavigate } from "react-router-dom";
import { usePagination } from "../../../hooks/usePagination";
import React, { useState } from "react";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import FileSearcher from "../../../components/FileSearcher";
import { FileGrid } from "../../Files/styles";
import FileUnit from "../../../components/FileUnit";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../components/generalStyles/pagination";

function CourseFiles() {
  const { course, isTeacher } = useCourseData();
  const query = useQuery();
  const navigate = useNavigate();
  const [currentPage] = usePagination(10);
  const [files, setFiles] = useState(new Array(0));
  const orderDirection = getQueryOrDefault(query, "order-direction", "desc");
  const orderProperty = getQueryOrDefault(query, "order-property", "date");
  const maxPage = 3;
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Archivos
      </SectionHeading>
      <BigWrapper style={{ display: "flex", flexDirection: "column" }}>
        <FileSearcher
          orderDirection={orderDirection}
          orderProperty={orderProperty}
          categoryType={[1]}
          categories={[
            { categoryName: "Hola", categoryId: 1 },
            { categoryName: "Dos", categoryId: 2 },
          ]}
          extensionType={[2]}
          extensions={[
            { fileExtensionName: "Otros", fileExtensionId: 1 },
            { fileExtensionName: "Hola", fileExtensionId: 2 },
            { fileExtensionName: "Dos", fileExtensionId: 3 },
          ]}
        />
        <FileGrid>
          {files.length === 0 && (
            <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
              No hay resultados!
            </GeneralTitle>
          )}
          {files.map((file) => (
            <FileUnit key={file.fileId} isTeacher={isTeacher} file={file} />
          ))}
        </FileGrid>
      </BigWrapper>
      <PaginationWrapper style={{ alignSelf: "center" }}>
        {currentPage > 1 && (
          <button
            onClick={() => {
              query.set("page", String(currentPage - 1));
              navigate(`/course/${course.courseId}/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              xRotated={true}
              src="/images/page-arrow.png"
              alt="Pagina previa"
            />
          </button>
        )}
        Pagina {currentPage} de {maxPage}
        {currentPage < maxPage && (
          <button
            onClick={() => {
              query.set("page", String(currentPage + 1));
              navigate(`/course/${course.courseId}/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              src="/images/page-arrow.png"
              alt="Pagina siguiente"
            />
          </button>
        )}
      </PaginationWrapper>
    </>
  );
}

export default CourseFiles;
