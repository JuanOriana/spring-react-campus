import {
  BigWrapper,
  GeneralTitle,
  SectionHeading,
  Separator,
} from "../../../../components/generalStyles/utils";
import {
  FormWrapper,
  FormInput,
  FormLabel,
  FormArea,
  FormButton,
  ErrorMessage,
} from "../../../../components/generalStyles/form";

import ExamUnit from "../../../../components/ExamUnit";
import { useForm } from "react-hook-form";
import React, { useState } from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../common/i18n/index";
import { ExamModel } from "../../../../types";
//

type FormData = {
  title: string;
  content: string;
  file: FileList;
  startTime: Date;
  endTime: Date;
};
function TeacherExams() {
  const { t } = useTranslation();
  const exams: ExamModel[] = [
    {
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
    },
  ];
  const [isBefore, setIsBefore] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    setIsBefore(false);
    if (data.endTime < data.startTime) setIsBefore(true);
    reset();
  });

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Examenes
      </SectionHeading>
      <FormWrapper
        reduced={true}
        encType="multipart/form-data"
        acceptCharset="utf-8"
        onSubmit={onSubmit}
      >
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t("TeacherExams.title")}
        </GeneralTitle>
        <FormLabel htmlFor="title">
          {t("TeacherExams.form.examTitle")}
        </FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("title", {
            required: true,
            validate: {
              length: (content) => content.length > 2 && content.length < 50,
            },
          })}
        />
        {errors.title?.type === "required" && (
          <ErrorMessage>
            {t("TeacherExams.error.examTitle.isRequired")}
          </ErrorMessage>
        )}
        {errors.title?.type === "length" && (
          <ErrorMessage>
            {t("TeacherExams.error.examTitle.length")}
          </ErrorMessage>
        )}
        <FormLabel htmlFor="content">
          {t("TeacherExams.form.examInstructions")}
        </FormLabel>
        <FormArea
          style={{ width: "95%", resize: "none" }}
          cols={50}
          rows={10}
          {...register("content", {
            required: true,
            validate: {
              length: (content) => content.length > 2 && content.length < 50,
            },
          })}
        ></FormArea>
        {errors.content?.type === "required" && (
          <ErrorMessage>
            {t("TeacherExams.error.examInstructions.isRequired")}
          </ErrorMessage>
        )}
        {errors.content?.type === "length" && (
          <ErrorMessage>
            {t("TeacherExams.error.examInstructions.length")}
          </ErrorMessage>
        )}
        <FormLabel htmlFor="file">{t("TeacherExams.form.examFile")}</FormLabel>
        <FormInput
          type="file"
          style={{ fontSize: "26px" }}
          {...register("file", {
            validate: {
              required: (file) => file !== undefined && file[0] !== undefined,

              size: (file) =>
                file && file[0] && file[0].size / (1024 * 1024) < 50,
            },
          })}
        />
        {errors.file?.type === "required" && (
          <ErrorMessage>
            {t("TeacherExams.error.examFile.isRequired")}
          </ErrorMessage>
        )}
        {errors.file?.type === "size" && (
          <ErrorMessage>{t("TeacherExams.error.examFile.size")}</ErrorMessage>
        )}
        <FormLabel htmlFor="startTime">
          {t("TeacherExams.form.examStart")}
        </FormLabel>
        <FormInput
          type="datetime-local"
          style={{ fontSize: "26px" }}
          min={Date.now()}
          {...register("startTime", {
            required: true,
          })}
        />
        {errors.startTime?.type === "required" && (
          <ErrorMessage>
            {t("TeacherExams.error.examStart.isRequired")}
          </ErrorMessage>
        )}
        <FormLabel htmlFor="endTime">
          {t("TeacherExams.form.examEnd")}
        </FormLabel>
        <FormInput
          type="datetime-local"
          style={{ fontSize: "26px" }}
          min={Date.now()}
          {...register("endTime", {
            required: true,
          })}
        />
        {errors.endTime?.type === "required" && (
          <ErrorMessage>
            {t("TeacherExams.error.examEnd.isRequired")}
          </ErrorMessage>
        )}
        {isBefore && (
          <ErrorMessage>
            {t("TeacherExams.error.examStart.badDate")}
          </ErrorMessage>
        )}
        <FormButton>{t("TeacherExams.form.createButton")}</FormButton>
      </FormWrapper>
      <Separator reduced={true}>.</Separator>

      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>{t("TeacherExams.recentExams")}</h3>
        {exams.length === 0 && <p>{t("TeacherExams.noExams")}</p>}
        {exams.map((exam) => (
          //EXAMS SOLVED Y USERCOUNT SALE DE QUE EXAMS EN VERDAD ES UN MAPA
          <ExamUnit
            exam={exam}
            isTeacher={true}
            examsSolved={7}
            userCount={30}
            average={8}
          />
        ))}
      </BigWrapper>
    </>
  );
}

export default TeacherExams;
