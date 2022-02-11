import {
  BigWrapper,
  SectionHeading,
} from "../../../../components/generalStyles/utils";
import ExamUnit from "../../../../components/ExamUnit";
import React, { useEffect, useState } from "react";
import { courseService } from "../../../../services";
import { handleService } from "../../../../scripts/handleService";
import { useCourseData } from "../../../../components/layouts/CourseLayout";
import { useNavigate } from "react-router-dom";
import LoadableData from "../../../../components/LoadableData";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../common/i18n/index";
import { SolvedExamModel } from "../../../../types";
//

function StudentExams() {
  const { t } = useTranslation();
  const course = useCourseData();
  const navigate = useNavigate();
  const [average, setAverage] = useState<number | undefined>(undefined);
  const [unresolvedExams, setUnresolvedExams] = useState(new Array(0));
  const [isLoading, setIsLoading] = useState(false);
  const [answerMarks, setAnswerMarks] = useState(new Array(0));

  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getUnsolvedExams(course.courseId),
      navigate,
      (examData) => {
        setUnresolvedExams(examData ? examData.getContent() : []);
      },
      () => {
        setIsLoading(false);
      }
    );

    handleService(
      courseService.getSolvedExams(course.courseId),
      navigate,
      (examData) => {
        setAnswerMarks(examData ? examData.getContent() : []);
      },
      () => {
        setIsLoading(false);
      }
    );

    handleService(
      courseService.getExamsAverage(course.courseId),
      navigate,
      (averageData) => {
        setAverage(averageData.average);
      },
      () => {
        setIsLoading(false);
      }
    );
  }, []);
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t("StudentExams.title")}
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>{t("StudentExams.toDo")}</h3>
        <LoadableData isLoading={isLoading}>
          {unresolvedExams.length === 0 && <p>{t("StudentExams.noExams")}</p>}
          {unresolvedExams.map((exam) => (
            <ExamUnit exam={exam} />
          ))}
          {answerMarks.length !== 0 && (
            <>
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "space-between",
                }}
              >
                <h3 style={{ margin: "10px 0" }}>
                  {t("StudentExams.sentExams")}
                </h3>
                <SectionHeading
                  style={{ fontSize: "20px", marginRight: "10px" }}
                >
                  {average}
                </SectionHeading>
              </div>
              {answerMarks.map((answer: SolvedExamModel) => (
                <ExamUnit
                  exam={answer.exam}
                  answer={answer.answer}
                  isDelivered={true}
                />
              ))}
            </>
          )}
        </LoadableData>
      </BigWrapper>
    </>
  );
}

export default StudentExams;
