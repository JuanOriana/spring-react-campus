import React, { useState, useEffect } from "react";
import { Outlet, useOutletContext } from "react-router-dom";
import type CourseData from "../../../../types/CourseData";
import CourseSectionsCol from "../../CourseSectionsCol";
import {
  CourseSectionName,
  CoursePageWrapper,
  CourseDataContainer,
} from "./styles";

function CourseLayout() {
  const [course, setCourse] = useState({
    courseId: 1,
    year: 2022,
    quarter: 2,
    board: "F",
    subject: { code: "97.01", name: "PAW" },
  });
  const [isTeacher, setIsTeacher] = useState(false);
  useEffect(() => {
    setCourse({
      courseId: 1,
      year: 2022,
      quarter: 2,
      board: "F",
      subject: { code: "97.01", name: "PAW" },
    });
    setIsTeacher(true);
  }, []);
  return (
    <>
      <CourseSectionName>{course && course.subject.name}</CourseSectionName>
      <CoursePageWrapper>
        <CourseSectionsCol
          courseId={course.courseId}
          courseName={course.subject.name}
          year={course.year}
          quarter={course.quarter}
          code={course.subject.code}
          board={course.board}
        />
        <CourseDataContainer>
          <Outlet context={{ course: course, isTeacher: isTeacher }} />
        </CourseDataContainer>
      </CoursePageWrapper>
    </>
  );
}

export function useCourseData() {
  return useOutletContext<CourseData>();
}

export default CourseLayout;
