import React, { useEffect, useState } from "react";
import { usePagination } from "../../../hooks/usePagination";
import {
  GeneralTitle,
  SectionHeading,
} from "../../../components/generalStyles/utils";
import AnnouncementUnit from "../../../components/AnnouncementUnit";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import BasicPagination from "../../../components/BasicPagination";

function CourseAnnouncements() {
  const { course, isTeacher } = useCourseData();
  const [announcements, setAnnouncements] = useState(new Array(0));
  const maxPage = 3;
  const [currentPage, pageSize] = usePagination(10);

  useEffect(() => {
    setAnnouncements([
      {
        announcementId: 1,
        title: "Hola",
        content:
          "xAAdddxAAdddxAAdddxAAdddxAAdddxAAdddxAdvxAAdddxAAdddxAAdddxAAdddAdddxAAdddxAAddd",
        author: { name: "juan", surname: "oriana" },
        date: "hoy",
      },
    ]);
  }, []);

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Anuncios
      </SectionHeading>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        {announcements.length === 0 && (
          <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
            No hay anuncios
          </GeneralTitle>
        )}

        {announcements.map((announcement) => (
          <AnnouncementUnit course={course} announcement={announcement} />
        ))}

        <BasicPagination
          currentPage={currentPage}
          pageSize={pageSize}
          maxPage={maxPage}
          baseURL={`/course/${course.courseId}/announcements`}
        />
      </div>
    </>
  );
}

export default CourseAnnouncements;
