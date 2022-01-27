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

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../../common/i18n/index";
//

function TeacherCourseExamStandalone() {
  const { t } = useTranslation();
  const Course = useCourseData();
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
      score: null,
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
              {t('TeacherCourseExamStandalone.filteredBy')}
            </FileSelectLabel>
            <FileSelect name="filter-by" id="filter-by">
              <option value="all" selected={filterBy === "all"}>
                {t('TeacherCourseExamStandalone.filters.all')}
              </option>
              <option value="corrected" selected={filterBy === "corrected"}>
                {t('TeacherCourseExamStandalone.filters.corrected')}
              </option>
              <option
                value="not corrected"
                selected={filterBy === "not-corrected"}
              >
                {t('TeacherCourseExamStandalone.filters.notCorrected')}
              </option>
            </FileSelect>
            <FormButton style={{ alignSelf: "end" }}>{t('TeacherCourseExamStandalone.filterButton')}</FormButton>
          </FileQueryContainer>
        </FileQueryContainer>
        <SectionHeading style={{ marginLeft: "10px" }}>
          {average}
        </SectionHeading>
        {answers.length === 0 && <>{t('TeacherCourseExamStandalone.noExams')}</>}
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
            to={`/course/${Course.courseId}/exam/${exam.examId}?page=${
              currentPage - 1
            }&pageSize=${pageSize}&filter-by=${filterBy}`}
          >
            <PaginationArrow
              xRotated={true}
              src="/images/page-arrow.png"
              alt={t('BasicPagination.alt.beforePage')}
            />
          </Link>
        )}
        {t('BasicPagination.message', {currentPage: currentPage, maxPage: maxPage})}
        {currentPage < maxPage && (
          <Link
            to={`/course/${Course.courseId}/exam/${exam.examId}?page=${
              currentPage + 1
            }&pageSize=${pageSize}&filter-by=${filterBy}`}
          >
            <PaginationArrow
              src="/images/page-arrow.png"
              alt={t('BasicPagination.alt.nextPage')}
            />
          </Link>
        )}
      </PaginationWrapper>
    </>
  );
}

export default TeacherCourseExamStandalone;
