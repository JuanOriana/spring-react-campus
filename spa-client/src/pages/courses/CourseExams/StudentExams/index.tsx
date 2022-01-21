import {
  BigWrapper,
  SectionHeading,
} from "../../../../components/generalStyles/utils";
import ExamUnit from "../../../../components/ExamUnit";
import React from "react";

function StudentExams() {
  const unresolvedExams = [{ examId: 1, title: "Examen" }];
  const answerMarks = [
    {
      score: 8,
      corrections: "Muy bueno!",
      exam: { examId: 1, title: "Examen" },
    },
  ];
  const average = 9.2;
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Examenes
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>
          "course-exams.comment" no me acuerdo que era
        </h3>
        {unresolvedExams.length === 0 && <p>No hay examenes</p>}
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
              <h3 style={{ margin: "10px 0" }}>Examenes enviados</h3>
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
