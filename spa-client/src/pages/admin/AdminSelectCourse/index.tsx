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
import { handleService } from "../../../scripts/handleService";
import { courseService } from "../../../services";
import LoadableData from "../../../components/LoadableData";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

function AdminSelectCourse() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [courses, setCourses] = useState(new Array(0));
  const [isLoading, setIsLoading] = useState(false);
  const [currentCourseId, setCurrentCourseId] = useState<number>(-1);

  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getCourses(1, 10),
      navigate,
      (coursesData) => {
        setCourses(coursesData ? coursesData.getContent() : []);
        setCurrentCourseId(
          coursesData ? coursesData.getContent()[0].courseId : -1
        );
      },
      () => {
        setIsLoading(false);
      }
    );
  }, []);

  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        onSubmit={() => navigate(`/admin/course/${currentCourseId}/enroll`)}
        style={{ margin: "0px 40px 40px 40px", alignSelf: "start" }}
      >
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t("AdminSelectCourse.title")}
        </GeneralTitle>
        <FormLabel htmlFor="courseId">
          {t("AdminSelectCourse.form.course")}
        </FormLabel>
        <div
          style={{ justifyContent: "center", display: "flex", width: "100%" }}
        >
          <LoadableData isLoading={isLoading}>
            <FormSelect
              name="courseId"
              id="courseId"
              style={{ fontSize: "26px", width: "100%" }}
              value={currentCourseId}
              onChange={(event) =>
                setCurrentCourseId(parseInt(event.target.value))
              }
            >
              {courses.map((course) => (
                <option value={course.courseId}>
                  {`${course.subject.name}[${course.board}]${course.year}-${course.quarter}Q`}
                </option>
              ))}
            </FormSelect>
          </LoadableData>
        </div>
        <FormButton>{t("AdminSelectCourse.form.selectButton")}</FormButton>
      </FormWrapper>
    </>
  );
}

export default AdminSelectCourse;
