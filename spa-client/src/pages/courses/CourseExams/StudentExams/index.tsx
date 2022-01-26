import {
  BigWrapper,
  SectionHeading,
} from "../../../../components/generalStyles/utils";
import ExamUnit from "../../../../components/ExamUnit";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../common/i18n/index";
//

function StudentExams() {
  const { t } = useTranslation();
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
        {t('StudentExams.title')}
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>
          {t('StudentExams.toDo')}
        </h3>
        {unresolvedExams.length === 0 && <p>{t('StudentExams.noExams')}</p>}
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
              <h3 style={{ margin: "10px 0" }}>{t('StudentExams.sentExams')}</h3>
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
