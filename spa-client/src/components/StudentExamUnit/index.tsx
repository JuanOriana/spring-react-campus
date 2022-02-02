import React from "react";
import PropTypes, { InferProps } from "prop-types";
import {
  FileImg,
  FileName,
  FileUnitWrapper,
  MediumIcon,
} from "../FileUnit/styles";
import { Link } from "react-router-dom";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

interface StudentExamUnitProps {
  isCorrected?: boolean;
  answer: {
    answerId: number;
    student: {
      name: string;
      surname: string;
    };
    score: number;
    deliveredDate: Date;
  };
  examId: number;
}

function StudentExamUnit({
  examId,
  isCorrected,
  answer,
}: StudentExamUnitProps) {
  const { t } = useTranslation();
  return (
    <FileUnitWrapper>
      <div style={{ display: "flex", alignItems: "center" }}>
        <FileImg
          src="/images/test.png"
          alt={answer.student.name ? answer.student.name : ""}
        />
        <FileName
          style={{
            paddingRight: "15px",
            marginRight: "5px",
            borderRight: "3px solid white",
          }}
        >
          {answer.student.name} {answer.student.surname}
        </FileName>
        {!answer.deliveredDate && (
          <FileName style={{ color: "red" }}>
            {t("StudentExamUnit.notHandedIn")}
          </FileName>
        )}
        {answer.deliveredDate && <FileName>{answer.deliveredDate}</FileName>}
      </div>
      {!isCorrected && (
        <Link
          to={`answer/${answer.answerId}/correct`}
          style={{ display: "flex", alignItems: "center" }}
        >
          <MediumIcon
            src="/images/check.png"
            alt={t("StudentExamUnit.alt.check")}
          />
        </Link>
      )}
      {isCorrected && (
        <div style={{ display: "flex", alignItems: "center" }}>
          <FileName style={{ marginRight: "10px" }}>{answer.score}</FileName>
          <button style={{ background: "none", border: "none" }} type="button">
            <MediumIcon
              src="/images/x.png"
              alt={t("StudentExamUnit.alt.check")}
            />
          </button>
        </div>
      )}
    </FileUnitWrapper>
  );
}

export default StudentExamUnit;
