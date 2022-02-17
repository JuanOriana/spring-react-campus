import {
  BigWrapper,
  SectionHeading,
} from "../../../../../components/generalStyles/utils";
import {
  FileQueryContainer,
  FileSelectLabel,
  FileSelect,
} from "../../../../../components/FileSearcher/syles";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../../../components/generalStyles/pagination";
import { useCourseData } from "../../../../../components/layouts/CourseLayout";
import { usePagination } from "../../../../../hooks/usePagination";
import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { handleService } from "../../../../../scripts/handleService";
import { answersService, examsService } from "../../../../../services";
import { ExamModel } from "../../../../../types";
import StudentExamUnit from "../../../../../components/StudentExamUnit";
import LoadableData from "../../../../../components/LoadableData";
import { getQueryOrDefault, useQuery } from "../../../../../hooks/useQuery";
// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../../common/i18n/index";
import { renderToast } from "../../../../../scripts/renderToast";
import AnswerModel from "../../../../../types/AnswerModel";

//

function TeacherCourseExamStandalone() {
  const { t } = useTranslation();
  const course = useCourseData();
  const navigate = useNavigate();
  const query = useQuery();
  const { examId } = useParams();
  const [currentPage, pageSize] = usePagination(10);
  const filterBy = getQueryOrDefault(query, "filter-by", "all");
  const [exam, setExam] = useState<ExamModel | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const [answers, setAnswers] = useState(new Array(1));
  const [maxPage, setMaxPage] = useState(1);
  useEffect(() => {
    if (examId) {
      setIsLoading(true);
      handleService(
        examsService.getExamById(parseInt(examId)),
        navigate,
        (examData) => {
          setExam(examData);
        },
        () => {}
      );

      handleService(
        examsService.getExamAnswers(
          parseInt(examId),
          currentPage,
          pageSize,
          filterBy
        ),
        navigate,
        (answersData) => {
          setAnswers(answersData ? answersData.getContent() : []);
          setMaxPage(answersData ? answersData.getMaxPage() : 1);
        },
        () => {
          setIsLoading(false);
        }
      );
    }
  }, [examId, currentPage, pageSize, filterBy]);

  function uncorrectExam(id: number) {
    if (
      !window.confirm(t("CourseAnnouncements.teacher.alert.deleteAnnouncement"))
    )
      return;
    answersService
      .correctAnswer(id, undefined, undefined)
      .then((result) => {
        if (result.hasFailed() && result.getError().getCode() !== 204) {
          renderToast(
            t("TeacherCourseExamStandalone.toast.error.notUndoCorrection"),
            "error"
          );
          return;
        }
        renderToast(
          t("TeacherCourseExamStandalone.toast.message.undoCorrectly"),
          "success"
        );
        setAnswers((oldAnswers) =>
          oldAnswers.map((answer: AnswerModel) =>
            answer.answerId === id ? { ...answer, score: undefined } : answer
          )
        );
      })
      .catch(() =>
        renderToast(
          t("TeacherCourseExamStandalone.toast.error.notUndoCorrection"),
          "error"
        )
      );
  }
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {exam && exam.title}
      </SectionHeading>
      <LoadableData isLoading={isLoading}>
        <BigWrapper>
          <FileQueryContainer
            method="get"
            id="filter-container"
            style={{ display: "flex" }}
          >
            <FileSelectLabel
              htmlFor="filter-by"
              style={{ marginBottom: "4px" }}
            >
              {t("TeacherCourseExamStandalone.filteredBy")}
            </FileSelectLabel>
            <FileSelect
              name="filter-by"
              id="filter-by"
              defaultValue={filterBy}
              onChange={(event) => {
                navigate(`?page=1&filter-by=${event.target.value}`);
              }}
            >
              <option value="all">
                {t("TeacherCourseExamStandalone.filters.all")}
              </option>
              <option value="corrected">
                {t("TeacherCourseExamStandalone.filters.corrected")}
              </option>
              <option defaultValue="not-corrected">
                {t("TeacherCourseExamStandalone.filters.notCorrected")}
              </option>
            </FileSelect>
          </FileQueryContainer>
          {answers.length === 0 && (
            <>{t("TeacherCourseExamStandalone.noExams")}</>
          )}
          {answers.map((answer) => (
            <StudentExamUnit
              key={answer.answerId}
              answer={answer}
              onDelete={uncorrectExam}
            />
          ))}
        </BigWrapper>
        <PaginationWrapper style={{ alignSelf: "center" }}>
          {currentPage > 1 && (
            <Link
              to={`/course/${course.courseId}/exam/${examId}?page=${
                currentPage - 1
              }&pageSize=${pageSize}&filter-by=${filterBy}`}
            >
              <PaginationArrow
                xRotated={true}
                src="./images/page-arrow.png"
                alt={t("BasicPagination.alt.beforePage")}
              />
            </Link>
          )}
          {t("BasicPagination.message", {
            currentPage: currentPage,
            maxPage: maxPage,
          })}
          {currentPage < maxPage && (
            <Link
              to={`/course/${course.courseId}/exam/${examId}?page=${
                currentPage + 1
              }&pageSize=${pageSize}&filter-by=${filterBy}`}
            >
              <PaginationArrow
                src="./images/page-arrow.png"
                alt={t("BasicPagination.alt.nextPage")}
              />
            </Link>
          )}
        </PaginationWrapper>
      </LoadableData>
    </>
  );
}

export default TeacherCourseExamStandalone;
