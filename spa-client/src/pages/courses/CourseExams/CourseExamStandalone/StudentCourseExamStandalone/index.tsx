import {
  BigWrapper,
  SectionHeading,
} from "../../../../../components/generalStyles/utils";
import FileUnit from "../../../../../components/FileUnit";
import { useCourseData } from "../../../../../components/layouts/CourseLayout";
import { CommentTitle, LinkButton } from "./styles";
import {
  FormInput,
  FormLabel,
  FormButton,
  ErrorMessage,
} from "../../../../../components/generalStyles/form";
import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { number } from "prop-types";
import { useForm } from "react-hook-form";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../../common/i18n/index";
//

type FormData = {
  file: FileList;
};

function StudentCourseExamStandalone() {
  const { t } = useTranslation();
  const Course = useCourseData();
  const navigate = useNavigate();
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
  };

  const endDate = new Date("1/13/2022");

  const calculateTimeLeft = () => {
    let difference = +endDate - +new Date();

    let timeLeft = {
      days: number,
      hours: number,
      minutes: number,
      seconds: number,
    };

    if (difference > 0) {
      timeLeft = {
        // @ts-ignore
        days: Math.floor(difference / (1000 * 60 * 60 * 24)),
        // @ts-ignore
        hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
        // @ts-ignore
        minutes: Math.floor((difference / 1000 / 60) % 60),
        // @ts-ignore
        seconds: Math.floor((difference / 1000) % 60),
      };

      if (difference < 3000) {
        navigate(`courses/${Course.courseId}/exams`);
      }
    }

    return timeLeft;
  };

  const [timeLeft, setTimeLeft] = useState(calculateTimeLeft());

  useEffect(() => {
    const timer = setTimeout(() => {
      setTimeLeft(calculateTimeLeft());
    }, 1000);
  });

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
        <h3 style={{ alignSelf: "center" }}>
          {t("StudentCourseExamStandalone.timeLeft", {
            days: timeLeft.days,
            hours: timeLeft.hours,
            minutes: timeLeft.minutes,
            seconds: timeLeft.seconds,
          })}
        </h3>
        <CommentTitle>
          {t("StudentCourseExamStandalone.examDescriptionTitle")}
        </CommentTitle>
        <p style={{ margin: "10px 0 10px 10px" }}>{exam.description}</p>
        <FileUnit file={exam.examFile} isMinimal={true} />

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
          <FormLabel style={{ margin: "0 0 5px 0" }} htmlFor="exam">
            {t("StudentCourseExamStandalone.form.solutionTitle")}
          </FormLabel>
          <FormInput
            type="file"
            accept="application/pdf, application/msword"
            style={{ fontSize: "18px" }}
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
              {t("StudentCourseExamStandalone.error.file.isRequired")}
            </ErrorMessage>
          )}
          {errors.file?.type === "size" && (
            <ErrorMessage>
              {t("StudentCourseExamStandalone.error.file.size")}
            </ErrorMessage>
          )}
          <div
            style={{
              display: "flex",
              marginTop: "5px",
              justifyContent: "center",
            }}
          >
            <LinkButton to={`/course/${Course.courseId}/exams`}>
              {t("StudentCourseExamStandalone.form.cancelSend")}
            </LinkButton>
            <FormButton>
              {t("StudentCourseExamStandalone.form.send")}
            </FormButton>
          </div>
        </form>
      </BigWrapper>
    </>
  );
}

export default StudentCourseExamStandalone;
