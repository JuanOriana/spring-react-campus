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
import { handleService } from "../../../scripts/handleService";

function CourseLayout() {
  const [course, setCourse] = useState<CourseModel | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { courseId } = useParams();
  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getCourseById(parseInt(courseId ? courseId : "-1")),
      navigate,
      (courseData) => {
        setCourse(courseData);
      },
      () => setIsLoading(false)
    );
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
                <Outlet context={course} />
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
