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
import React, { useEffect, useState } from "react";
import { courseService, examsService } from "../../../../services";
import { renderToast } from "../../../../scripts/renderToast";
import { useCourseData } from "../../../../components/layouts/CourseLayout";
import { useNavigate } from "react-router-dom";
import { handleService } from "../../../../scripts/handleService";
import ExamStatsModel from "../../../../types/ExamStatsModel";
import LoadableData from "../../../../components/LoadableData";
// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../common/i18n/index";
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
  const [examsStats, setExamsStats] = useState<ExamStatsModel[]>(new Array(1));
  const [isLoading, setIsLoading] = useState(false);
  const course = useCourseData();
  const navigate = useNavigate();
  const [reload, setReload] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getExams(course.courseId),
      navigate,
      (examStatsData) => setExamsStats(examStatsData),
      () => setIsLoading(false)
    );
  }, []);

  const [isBefore, setIsBefore] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    setIsBefore(false);
    if (data.endTime <= data.startTime) {
      setIsBefore(true);
      return;
    }
    courseService
      .newExam(
        course.courseId,
        data.title,
        data.content,
        data.file[0],
        data.startTime,
        data.endTime
      )
      .then((response) => {
        if (!response.hasFailed()) {
          renderToast(t('TeacherExams.toast.message.createdCorrectly'), "success");
          navigate(`/course/${course.courseId}/exams`);
          setReload(!reload);
          reset();
          reset();
        } else {
          renderToast(t('TeacherExams.toast.error.notCreated'), "error");
        }
      })
      .catch(() =>
        renderToast(t('TeacherExams.toast.error.notCreated'), "error")
      );
  });

  function onDelete(id: number) {
    if (!window.confirm(t('TeacherExams.alert.deleteExam'))) return;
    examsService
      .deleteExam(id)
      .then(() => {
        renderToast(t('TeacherExams.toast.message.removedCorrectly'), "success");
        setExamsStats((oldExamStats) =>
          oldExamStats.filter((exam: ExamStatsModel) => exam.exam.examId !== id)
        );
      })
      .catch(() =>
        renderToast(t('TeacherExams.toast.error.notRemoved'), "error")
      );
  }

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t("TeacherExams.title")}
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

      <BigWrapper style={{ alignItems: isLoading ? "center" : "stretch" }}>
        <LoadableData isLoading={isLoading}>
          <h3 style={{ margin: "10px 0" }}>{t("TeacherExams.recentExams")}</h3>
          {examsStats.length === 0 && <p>{t("TeacherExams.noExams")}</p>}
          {examsStats.map((examData) => (
            <ExamUnit
              key={examData.exam.examId}
              exam={examData.exam}
              isTeacher={true}
              examsSolved={examData.corrected.length}
              userCount={
                examData.corrected.length + examData.notCorrected.length
              }
              average={
                examData.average && Math.round(examData.average * 100) / 100
              }
              onDelete={onDelete}
            />
          ))}
        </LoadableData>
      </BigWrapper>
    </>
  );
}

export default TeacherExams;
