import {
  BigWrapper,
  SectionHeading,
} from "../../../../../components/generalStyles/utils";
import {
  CommentTitle,
  LinkButton,
} from "../StudentCourseExamStandalone/styles";
import {
  ErrorMessage,
  FormArea,
  FormButton,
  FormLabel,
} from "../../../../../components/generalStyles/form";
import FileUnit from "../../../../../components/FileUnit";
import { useCourseData } from "../../../../../components/layouts/CourseLayout";
import { useForm } from "react-hook-form";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../../common/i18n/index";
//

type FormData = {
  mark: number;
  comments?: string;
};

function CorrectExam() {
  const { t } = useTranslation();
  const Course = useCourseData();
  const exam = {
    examId: 1,
    title: "Examen",
    examFile: {
      fileId: 1,
      size: 10,
      fileName: "xd",
      extension: {
        fileExtensionName: ".doc",
        fileExtensionId: 12,
      },
      course: {
        courseId: 1,
        year: 2021,
        quarter: 2,
        board: "A",
        subject: {
          subjectId: 1,
          code: "a",
          name: "PAW",
        },
        courseUrl: "urlcurso",
        isTeacher: true,
      },
      fileCategory: undefined,
      downloads: 2,
    },
    description: "hola\nxd",
    average: 2,
    url: "hola",
  };

  const answer = {
    answerId: 1,
    student: {
      name: "juan",
      surname: "doe",
    },
    score: 10,
    deliveredDate: new Date().toDateString(),
    answerFile: {
      fileId: 1,
      size: 10,
      fileName: "xd",
      extension: {
        fileExtensionName: ".doc",
        fileExtensionId: 12,
      },
      course: {
        courseId: 1,
        year: 2021,
        quarter: 2,
        board: "A",
        subject: {
          subjectId: 1,
          code: "a",
          name: "PAW",
        },
        courseUrl: "urlcurso",
        isTeacher: true,
      },
      fileCategory: undefined,
      downloads: 2,
    },
  };

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {exam.title}
      </SectionHeading>
      <BigWrapper>
        <CommentTitle>{t("CorrectExam.descriptionTitle")}</CommentTitle>
        <p
          style={{
            marginLeft: "30px",
            marginTop: "10px",
            marginBottom: "10px",
          }}
        >
          {exam.description}
        </p>
        <FileUnit file={exam.examFile} isMinimal={true} />
        <CommentTitle>{t("CorrectExam.solutionTitle")}</CommentTitle>
        {answer.deliveredDate && (
          <FileUnit file={answer.answerFile} isMinimal={true} />
        )}
        {answer.deliveredDate && <p>{t("CorrectExam.examNotDone")}</p>}
        <form
          encType="multipart/form-data"
          acceptCharset="utf-8"
          style={{
            margin: "30px 0",
            display: "flex",
            padding: "10px",
            flexDirection: "column",
            border: "2px solid #2EC4B6",
            borderRadius: "12px",
          }}
          onSubmit={onSubmit}
        >
          <FormLabel htmlFor="mark" style={{ margin: "0 0 5px 0" }}>
            {t("CorrectExam.form.title")}
          </FormLabel>
          <input
            type="number"
            min="0"
            max="10"
            step=".01"
            style={{
              fontSize: "24px",
              marginLeft: "20px",
              width: "80px",
              padding: "3px",
            }}
            {...register("mark", {
              required: true,
              max: 10,
              min: 0,
            })}
          />
          {errors.mark?.type === "required" && (
            <ErrorMessage>
              {t("CorrectExam.error.grade.isRequired")}
            </ErrorMessage>
          )}
          {errors.mark?.type === "max" && (
            <ErrorMessage>{t("CorrectExam.error.grade.minGrade")}</ErrorMessage>
          )}
          {errors.mark?.type === "min" && (
            <ErrorMessage>{t("CorrectExam.error.grade.maxGrade")}</ErrorMessage>
          )}
          <FormLabel htmlFor="comments">
            {t("CorrectExam.form.comments")}
          </FormLabel>
          <FormArea
            style={{ width: "95%", resize: "none" }}
            cols={50}
            rows={5}
            {...register("comments", {
              validate: {
                length: (comments) => !comments || comments.length < 50,
              },
            })}
          ></FormArea>
          {errors.comments?.type === "length" && (
            <ErrorMessage>
              {t("CorrectExam.error.comments.length")}
            </ErrorMessage>
          )}
          <div
            style={{
              display: "flex",
              marginTop: "5px",
              justifyContent: "center",
            }}
          >
            <LinkButton
              style={{
                marginRight: "15px",
                background: "#a80011",
                textAlign: "center",
              }}
              to={`/course/${Course.courseId}/exam/${exam.examId}`}
            >
              {t("CorrectExam.form.cancelCorrectionButton")}
            </LinkButton>
            <FormButton>{t("CorrectExam.form.submitButton")}</FormButton>
          </div>
        </form>
      </BigWrapper>
    </>
  );
}

export default CorrectExam;
