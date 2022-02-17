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
import { useNavigate, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { handleService } from "../../../../../scripts/handleService";
import { examsService } from "../../../../../services";
import { ExamModel } from "../../../../../types";
import { renderToast } from "../../../../../scripts/renderToast";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../../../common/i18n/index";
//

type FormData = {
  file: FileList;
};

function StudentCourseExamStandalone() {
  const { t } = useTranslation();
  const course = useCourseData();
  const navigate = useNavigate();
  const { examId } = useParams();
  const [exam, setExam] = useState<ExamModel | undefined>(undefined);

  useEffect(() => {
    examId &&
      handleService(
        examsService.getExamById(parseInt(examId ? examId : "-1")),
        navigate,
        (examData) => {
          setExam(examData);
        },
        () => {}
      );
  }, [examId]);

  const calculateTimeLeft = () => {
    let difference = +exam?.endTime! - +new Date();

    let newTimeLeft = undefined;
    if (difference > 0) {
      newTimeLeft = {
        days: Math.floor(difference / (1000 * 60 * 60 * 24)),
        hours: Math.floor((difference / (1000 * 60 * 60)) % 24),
        minutes: Math.floor((difference / 1000 / 60) % 60),
        seconds: Math.floor((difference / 1000) % 60),
      };
      if (difference < 3000) {
        navigate(`courses/${course.courseId}/exams`);
      }
    }
    return newTimeLeft;
  };

  const [timeLeft, setTimeLeft] = useState<
    | {
        days: number;
        hours: number;
        minutes: number;
        seconds: number;
      }
    | undefined
  >();

  useEffect(() => {
    let timer: NodeJS.Timeout;
    if (exam?.endTime) {
      timer = setTimeout(() => {
        console.log("ADSA");
        setTimeLeft(calculateTimeLeft());
      }, 1000);
    }
    return () => clearTimeout(timer);
  }, [exam, timeLeft]);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    examsService
      .editAnswerByStudent(parseInt(examId ? examId : "-1"), data.file[0])
      .then((result) => {
        if (result.hasFailed()) {
          renderToast(
            t("StudentCourseExamStandalone.toast.error.notSent"),
            "error"
          );
          return;
        }
        navigate(`/course/${course.courseId}/exams`);
        renderToast(
          t("StudentCourseExamStandalone.toast.message.sentCorrectly"),
          "success"
        );
      })
      .catch(() =>
        renderToast(
          t("StudentCourseExamStandalone.toast.error.notSent"),
          "error"
        )
      );
  });
  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {exam?.title}
      </SectionHeading>
      <BigWrapper>
        <h3 style={{ alignSelf: "center" }}>
          {t("StudentCourseExamStandalone.timeLeft", {
            days: timeLeft?.days,
            hours: timeLeft?.hours,
            minutes: timeLeft?.minutes,
            seconds: timeLeft?.seconds,
          })}
        </h3>
        <CommentTitle>
          {t("StudentCourseExamStandalone.examDescriptionTitle")}
        </CommentTitle>
        <p style={{ margin: "10px 0 10px 10px" }}>{exam?.description}</p>
        {exam && <FileUnit file={exam?.examFile!} isMinimal={true} />}

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
            <LinkButton to={`/course/${course.courseId}/exams`}>
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
