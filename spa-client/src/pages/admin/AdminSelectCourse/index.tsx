import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  FormButton,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

function AdminSelectCourse() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [courses, setCourses] = useState(new Array(0));

  useEffect(() => {
    setCourses([
      {
        courseId: 1,
        subject: { name: "PAW" },
        board: "F",
        year: 2020,
        quarter: 2,
      },
    ]);
  }, []);
  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        onSubmit={() => navigate("/admin/course/enroll")}
        style={{ margin: "0px 40px 40px 40px", alignSelf: "start" }}
      >
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t('AdminSelectCourse.title')}
        </GeneralTitle>
        <FormLabel htmlFor="courseId">{t('AdminSelectCourse.form.course')}</FormLabel>
        <FormSelect name="courseId" id="courseId" style={{ fontSize: "26px" }}>
          {courses.map((course) => (
            <option value={course.courseId}>
              {`${course.subject.name}[${course.board}]${course.year}-${course.quarter}Q`}
            </option>
          ))}
        </FormSelect>
        <FormButton>{t('AdminSelectCourse.form.selectButton')}</FormButton>
      </FormWrapper>
    </>
  );
}

export default AdminSelectCourse;
