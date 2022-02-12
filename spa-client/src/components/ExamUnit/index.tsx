import React, { useState } from "react";
import { FileImg, FileName, FileUnitWrapper } from "../FileUnit/styles";
import {
  DeliveredLink,
  ExamComment,
  MediumIcon,
  PaginationArrow,
} from "./styles";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { ExamModel } from "../../types";
import AnswerModel from "../../types/AnswerModel";
//

interface ExamUnitProps {
  exam: ExamModel;
  average?: number;
  examsSolved?: number;
  userCount?: number;
  isDelivered?: boolean;
  isTeacher?: boolean;
  answer?: AnswerModel;
  onDelete?: (id: number) => void;
}

function ExamUnit({
  exam,
  isDelivered,
  isTeacher,
  answer,
  examsSolved,
  userCount,
  average,
  onDelete,
}: ExamUnitProps) {
  const { t } = useTranslation();
  const [showCorrections, setShowCorrections] = useState(false);
  return (
    <FileUnitWrapper
      id={`exam-${exam.examId}`}
      style={{ flexDirection: "column", justifyContent: "center" }}
    >
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          width: "100%",
        }}
      >
        <div style={{ display: "flex", alignItems: "center" }}>
          <DeliveredLink
            delivered={isDelivered ? "true" : "false"}
            to={`../exam/${exam.examId}`}
          >
            <FileImg
              src="/images/test.png"
              alt={exam.title ? exam.title : `${t("ExamUnit.alt.exam")}`}
            />
            <FileName>{exam.title}</FileName>
          </DeliveredLink>
        </div>
        <div style={{ display: "flex", alignItems: "center" }}>
          {isDelivered && !isTeacher && (
            <>
              <FileName>
                {t("ExamUnit.grade", {
                  grade: answer!.score ? answer!.score : "--",
                })}
              </FileName>
              {answer!.corrections && (
                <PaginationArrow
                  src="/images/outline-arrow.png"
                  style={{ transform: "rotate(90deg)", marginLeft: "10px" }}
                  alt={`${t("ExamUnit.alt.seeCorrections")}`}
                  onClick={() => setShowCorrections((lastVal) => !lastVal)}
                />
              )}
            </>
          )}
          {isTeacher && (
            <>
              <FileName>{average}</FileName>
              <FileName
                style={{
                  paddingLeft: "5px",
                  marginLeft: "15px",
                  borderLeft: "3px solid white",
                }}
              >
                {t("ExamUnit.correctedOf", {
                  examsSolved: examsSolved,
                  userCount: userCount,
                })}
              </FileName>
              <MediumIcon
                src="/images/trash.png"
                alt={`${t("ExamUnit.alt.delete")}`}
                onClick={() => onDelete!(exam.examId)}
              />
            </>
          )}
        </div>
      </div>
      {isDelivered && !isTeacher && answer!.corrections && showCorrections && (
        <ExamComment>
          {answer!.corrections ? answer!.corrections : ""}
        </ExamComment>
      )}
    </FileUnitWrapper>
  );
}

export default ExamUnit;
