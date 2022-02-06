import React, { useEffect, useState } from "react";
import { SectionHeading } from "../../components/generalStyles/utils";
import { usePagination } from "../../hooks/usePagination";
import { Link } from "react-router-dom";
import AnnouncementUnit from "../../components/AnnouncementUnit";
import {
  CoursesContainer,
  Course,
  PortalAnnouncements,
  CourseName,
} from "./styles";
import BasicPagination from "../../components/BasicPagination";
import { userService, announcementsService } from "../../services";
import { useNavigate } from "react-router-dom";
import LoadableData from "../../components/LoadableData";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { useAuth } from "../../contexts/AuthContext";
import { handleService } from "../../scripts/handleService";
//

function Portal() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [courses, setCourses] = useState(new Array(0));
  const [isCourseLoading, setIsCourseLoading] = useState(false);
  const [announcements, setAnnouncements] = useState(new Array(0));
  const [isAnnouncementLoading, setIsAnnouncementLoading] = useState(false);
  const [maxPage, setMaxPage] = useState(1);
  const [currentPage, pageSize] = usePagination(10);
  const { user } = useAuth();

  useEffect(() => {
    setIsCourseLoading(true);
    if (user) {
      handleService(
        userService.getUsersCourses(user!.userId, currentPage, pageSize),
        navigate,
        (coursesData) => {
          setCourses(coursesData.getContent());
          setMaxPage(coursesData.getMaxPage());
        },
        () => setIsCourseLoading(false)
      );
    }
  }, [user, currentPage, pageSize]);

  useEffect(() => {
    setIsAnnouncementLoading(true);
    handleService(
      announcementsService.getAnnouncements(1, 3),
      navigate,
      (announcementsData) => {
        setAnnouncements(announcementsData.getContent());
      },
      () => setIsAnnouncementLoading(false)
    );
  }, []);

  return (
    <>
      <SectionHeading>{t("Portal.title")}</SectionHeading>
      <CoursesContainer>
        <LoadableData isLoading={isCourseLoading} spinnerMultiplier={1}>
          {courses.map((course) => (
            // TODO: AGREGAR CHEQUEO DE SI ES CONTENIDO POR CURRENT COURESE O NO Y SI ES ESTUDIANTE O NO
            <Course isOld={false} key={course.courseId}>
              <CourseName style={{ display: "flex", alignItems: "center" }}>
                <Link to={`/course/${course.courseId}`}>
                  {`${course.subject.name} [${course.board}]`}
                </Link>
                {/*<c:if test="${!coursesAsStudent.contains(courseItem)}">*/}
                {/*    <img src="<c:url value="/resources/images/graduation-hat.png"/>"*/}
                {/*         alt="<spring:message code=" img.alt.teacher.icon" />" style="margin-left: 10px"*/}
                {/*         width="28px"/>*/}
                {/*</c:if>*/}
              </CourseName>
              <CourseName>
                {course.quarter}Q-{course.year}
              </CourseName>
            </Course>
          ))}
          <BasicPagination
            currentPage={currentPage}
            pageSize={pageSize}
            maxPage={maxPage}
            baseURL={`/portal`}
            style={{ fontSize: "18px" }}
          />
        </LoadableData>
      </CoursesContainer>

      <PortalAnnouncements>
        <SectionHeading>{t("Portal.lastAnnouncements")}</SectionHeading>
        <LoadableData isLoading={isAnnouncementLoading} spinnerMultiplier={2}>
          {announcements.length == 0 && <p>No hay anuncios!</p>}

          {announcements.map((announcement) => (
            <AnnouncementUnit
              key={announcement.announcementId}
              announcement={announcement}
              isGlobal={true}
            />
          ))}
        </LoadableData>
      </PortalAnnouncements>
    </>
  );
}

export default Portal;
