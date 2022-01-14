import {
  BigWrapper,
  SectionHeading,
} from "../../../../../components/generalStyles/utils";
import {
  FileQueryContainer,
  FileSelectLabel,
  FileSelect,
} from "../../../../../components/FileSearcher/syles";
import { FormButton } from "../../../../../components/generalStyles/form";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../../../components/generalStyles/pagination";
import { useCourseData } from "../../../../../components/layouts/CourseLayout";
import { usePagination } from "../../../../../hooks/usePagination";
import React from "react";
import { Link } from "react-router-dom";
import StudentExamUnit from "../../../../../components/StudentExamUnit";
function TeacherCourseExamStandalone() {
  const { course } = useCourseData();
  const maxPage = 3;
  const [currentPage, pageSize] = usePagination(10);
  const exam = {
    examId: 1,
    title: "Examen",
    examFile: {
      fileId: 1,
      name: "xd",
      extension: {
        fileExtensionName: ".doc",
      },
      course: {
        courseId: 1,
        subject: {
          name: "PAW",
        },
      },
      categories: [],
      downloads: 2,
    },
    description: "hola\nxd",
  };

  let filterBy;
  filterBy = "all";
  const average = 6.9;
  const answers = [
    {
      answerId: 1,
      student: {
        name: "juan",
        surname: "doe",
      },
      score: 10,
      deliveredDate: new Date().toDateString(),
    },
  ];
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {exam.title}
      </SectionHeading>
      <BigWrapper>
        <FileQueryContainer method="get">
          <FileQueryContainer id="filter-container" style={{ display: "flex" }}>
            <FileSelectLabel
              htmlFor="filter-by"
              style={{ marginBottom: "4px" }}
            >
              Filtrar por
            </FileSelectLabel>
            <FileSelect name="filter-by" id="filter-by">
              <option value="all" selected={filterBy === "all"}>
                Todos
              </option>
              <option value="corrected" selected={filterBy === "corrected"}>
                Corregidos
              </option>
              <option
                value="not corrected"
                selected={filterBy === "not-corrected"}
              >
                No corregidos
              </option>
            </FileSelect>
            <FormButton style={{ alignSelf: "end" }}>Filtrar</FormButton>
          </FileQueryContainer>
        </FileQueryContainer>
        <SectionHeading style={{ marginLeft: "10px" }}>
          {average}
        </SectionHeading>
        {answers.length === 0 && <>No hay examenes que cumplan el criterio</>}
        {answers.map((answer) => (
          <StudentExamUnit
            answer={answer}
            isCorrected={answer.score !== null}
            examId={exam.examId}
          />
        ))}
      </BigWrapper>
      <PaginationWrapper style={{ alignSelf: "center" }}>
        {currentPage > 1 && (
          <Link
            to={`/course/${course.courseId}/exam/${exam.examId}?page=${
              currentPage - 1
            }&pageSize=${pageSize}&filter-by=${filterBy}`}
          >
            <PaginationArrow
              xRotated={true}
              src="/images/page-arrow.png"
              alt="Pagina previa"
            />
          </Link>
        )}
        Pagina {currentPage} de {maxPage}
        {currentPage < maxPage && (
          <Link
            to={`/course/${course.courseId}/exam/${exam.examId}?page=${
              currentPage + 1
            }&pageSize=${pageSize}&filter-by=${filterBy}`}
          >
            <PaginationArrow
              src="/images/page-arrow.png"
              alt="Siguiente pagina"
            />
          </Link>
        )}
      </PaginationWrapper>
    </>
  );
}

export default TeacherCourseExamStandalone;
