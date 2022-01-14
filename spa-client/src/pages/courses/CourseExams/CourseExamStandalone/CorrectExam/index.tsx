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

type FormData = {
  mark: number;
  comments?: string;
};

function CorrectExam() {
  const { course } = useCourseData();
  const exam = {
    examId: 1,
    title: "Examen",
    examFile: {
      fileId: 1,
      name: "xd",
      extension: {
        fileExtensionName: ".doc",
      },
      course: {
        courseId: 1,
        subject: {
          name: "PAW",
        },
      },
      categories: [],
      downloads: 2,
    },
    description: "hola\nxd",
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
      name: "xd",
      extension: {
        fileExtensionName: ".doc",
      },
      course: {
        courseId: 1,
        subject: {
          name: "PAW",
        },
      },
      categories: [],
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
        <CommentTitle>
          "correct.specific.exam.description" no me acuerdo que era
        </CommentTitle>
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
        <CommentTitle>"correct.specific.exam.solution"</CommentTitle>
        {answer.deliveredDate && (
          <FileUnit file={answer.answerFile} isMinimal={true} />
        )}
        {answer.deliveredDate && <p>El examen aun no fue realizado</p>}
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
            Corregir examen
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
            <ErrorMessage>La nota es requerida</ErrorMessage>
          )}
          {errors.mark?.type === "max" && (
            <ErrorMessage>La nota maxima es 10</ErrorMessage>
          )}
          {errors.mark?.type === "min" && (
            <ErrorMessage>La nota minima es 0</ErrorMessage>
          )}
          <FormLabel htmlFor="comments">Anadir comentarios</FormLabel>
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
              El largo de los commentarios no puede exceder los 50 caracteres
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
              to={`/course/${course.courseId}/exam/${exam.examId}`}
            >
              Cancelar correccion
            </LinkButton>
            <FormButton>Corregir</FormButton>
          </div>
        </form>
      </BigWrapper>
    </>
  );
}

export default CorrectExam;
