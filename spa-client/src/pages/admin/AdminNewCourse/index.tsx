import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  ErrorMessage,
  FormButton,
  FormInput,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { FormText } from "../AdminAllCourses/styles";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import React, { useState } from "react";
import { useForm } from "react-hook-form";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

const days: string[] = [
  "Lunes",
  "Martes",
  "Miercoles",
  "Jueves",
  "Viernes",
  "Sabado",
];

type FormData = {
  subjectId: number;
  quarter: number;
  year: number;
  board: string;
};

function AdminNewCourse() {
  const { t } = useTranslation();
  const subjects = [{ subjectId: 1, name: "PAW" }];
  const [isCourseDuplicated, setIsCourseDuplicated] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setError,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });

  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        method="post"
        acceptCharset="utf-8"
        style={{ margin: "0px 40px 40px 40px" }}
        onSubmit={onSubmit}
      >
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t('AdminNewCourse.newCourse')}
        </GeneralTitle>
        <FormLabel htmlFor="subjectId">{t('AdminNewCourse.form.subject')}</FormLabel>
        <FormSelect style={{ fontSize: "26px" }} {...register("subjectId", {})}>
          {subjects.map((subject) => (
            <option value={subject.subjectId}>{subject.name}</option>
          ))}
        </FormSelect>
        <FormLabel htmlFor="quarter">{t('AdminNewCourse.form.quarter')}</FormLabel>
        <div
          style={{
            display: "flex",
            width: "200px",
            alignItems: "center",
            justifyContent: "space-between",
            marginLeft: "20px",
            fontSize: "20px",
          }}
        >
          <div>
            <input
              type="radio"
              value="1"
              style={{ marginRight: "5px" }}
              {...register("quarter", {})}
            />{" "}
            1
          </div>
          <div>
            <input
              type="radio"
              value="2"
              style={{ marginRight: "5px" }}
              {...register("quarter", {})}
            />{" "}
            2
          </div>
        </div>
        <FormLabel htmlFor="year">{t('AdminNewCourse.form.year')}</FormLabel>
        <FormInput
          type="number"
          style={{ fontSize: "26px" }}
          {...register("year", {})}
        />
        <FormLabel htmlFor="board">{t('AdminNewCourse.form.board')}</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("board", {})}
        />
        {isCourseDuplicated && (
          <ErrorMessage>{t('AdminNewCourse.error.alreadyExist')}</ErrorMessage>
        )}
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "400px 400px",
            margin: "20px 20px 0 20px",
          }}
        >
          {days.map((day) => (
            <div style={{ display: "flex", flexDirection: "column" }}>
              <FormText>{t('AdminNewCourse.days.' + day)}</FormText>
              <div style={{ display: "flex" }}>
                <input
                  style={{ width: "3em" }}
                  type="number"
                  min="8"
                  max="22"
                />
                <p>:00 ---- </p>
                <input
                  style={{ width: "3em" }}
                  type="number"
                  min="8"
                  max="22"
                />
                <p>:00</p>
              </div>
            </div>
          ))}
        </div>
        <FormButton>{t('AdminNewCourse.form.createButton')}</FormButton>
      </FormWrapper>
    </>
  );
}

export default AdminNewCourse;
