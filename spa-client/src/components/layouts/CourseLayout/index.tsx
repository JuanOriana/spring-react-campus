import React, { useState, useEffect } from "react";
import { Outlet, useOutletContext } from "react-router-dom";
import type CourseModel from "../../../types/CourseModel";
import CourseSectionsCol from "../../CourseSectionsCol";
import {
  CourseSectionName,
  CoursePageWrapper,
  CourseDataContainer,
} from "./styles";
import { courseService } from "../../../services";
import { useNavigate, useParams } from "react-router-dom";
import LoadableData from "../../LoadableData";

function CourseLayout() {
  const [course, setCourse] = useState<CourseModel | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { courseId } = useParams();
  useEffect(() => {
    setIsLoading(true);
    courseService
      .getCourseById(parseInt(courseId ? courseId : "-1"))
      .then((course) => {
        if (course.hasFailed()) {
          navigate(`/error?code=${course.getError().getCode()}`);
        } else {
          setCourse(course.getData());
        }
      })
      .catch(() => navigate("/error?code=500"))
      .finally(() => setIsLoading(false));
  }, []);
  return (
    <>
      <LoadableData isLoading={isLoading}>
        {course && (
          <>
            <CourseSectionName>{course!.subject.name}</CourseSectionName>
            <CoursePageWrapper>
              <CourseSectionsCol
                courseId={course!.courseId}
                courseName={course!.subject.name}
                year={course!.year}
                quarter={course!.quarter}
                code={course!.subject.code}
                board={course!.board}
              />
              <CourseDataContainer>
                <Outlet
                  context={{ course: course!, isTeacher: course!.isTeacher }}
                />
              </CourseDataContainer>
            </CoursePageWrapper>
          </>
        )}
      </LoadableData>
    </>
  );
}

export function useCourseData() {
  return useOutletContext<CourseModel>();
}

export default CourseLayout;
