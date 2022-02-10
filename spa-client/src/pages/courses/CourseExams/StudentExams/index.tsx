import {
  BigWrapper,
  SectionHeading,
} from "../../../../components/generalStyles/utils";
import ExamUnit from "../../../../components/ExamUnit";
import React, { useEffect, useState } from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../common/i18n/index";
import { courseService, examsService } from "../../../../services";
import { handleService } from "../../../../scripts/handleService";
import { useCourseData } from "../../../../components/layouts/CourseLayout";
import { useNavigate } from "react-router-dom";
//
const exampleExam = {
  examId: 1,
  title: "Examen",
  description: "adssada",
  endTime: new Date(),
  startTime: new Date(),
  examFile: undefined,
  average: 9,
  course: {
    courseId: 1,
    courseUrl: "asdad",
    board: "asdasd",
    quarter: 1,
    year: 2022,
    isTeacher: true,
    subject: { subjectId: 1, code: "F", name: "PAW" },
  },
  url: "xd",
};

function StudentExams() {
  const { t } = useTranslation();
  const course = useCourseData();
  const navigate = useNavigate();
  const average = 9.2;
  const [unresolvedExams, setUnresolvedExams] = useState(new Array(0));
  const [answerMarks, setAnswerMarks] = useState(new Array(0));

  useEffect(() => {
    handleService(
      courseService.getSolvedExams(course.courseId),
      navigate,
      (examData) => {
        setUnresolvedExams(examData ? examData.getContent() : []);
      },
      () => {}
    );

    setAnswerMarks([
      {
        score: 8,
        corrections: "Muy bueno!",
        exam: exampleExam,
      },
    ]);
  }, []);
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t("StudentExams.title")}
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>{t("StudentExams.toDo")}</h3>
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
              <SectionHeading style={{ fontSize: "20px", marginRight: "10px" }}>
                {average}
              </SectionHeading>
            </div>
            {answerMarks.map((answer) => (
              <ExamUnit exam={answer.exam} answer={answer} isDelivered={true} />
            ))}
          </>
        )}
      </BigWrapper>
    </>
  );
}

export default StudentExams;
