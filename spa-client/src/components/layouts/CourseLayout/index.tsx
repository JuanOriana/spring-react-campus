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
import { useAuth } from "../../../contexts/AuthContext";

function CourseLayout() {
  const [course, setCourse] = useState<CourseModel | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { user } = useAuth();
  const { courseId } = useParams();
  useEffect(() => {
    if (!user) return;
    setIsLoading(true);
    handleService(
      courseService.getCourseById(parseInt(courseId ? courseId : "-1")),
      navigate,
      (courseData) => {
        handleService(
          courseService.getTeachers(parseInt(courseId ? courseId : "-1")),
          navigate,
          (users) => {
            let isTeacher = false;
            if (users) {
              for (const iterUser of users.getContent()) {
                if (iterUser.userId === user!.userId) {
                  isTeacher = true;
                  break;
                }
              }
            }
            setCourse({ ...courseData!, isTeacher: isTeacher });
          },
          () => {
            return;
          }
        );
      },
      () => setIsLoading(false)
    );
  }, [user]);

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
