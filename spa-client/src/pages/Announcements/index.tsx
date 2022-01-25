import React, { useState, useEffect } from "react";
import { SectionHeading } from "../../components/generalStyles/utils";
import { AnnouncementTitle } from "../../components/AnnouncementUnit/styles";
import AnnouncementUnit from "../../components/AnnouncementUnit";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../components/generalStyles/pagination";
import { Link } from "react-router-dom";
import { usePagination } from "../../hooks/usePagination";
import BasicPagination from "../../components/BasicPagination";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

function Announcements() {
  const { t } = useTranslation();
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
      <SectionHeading>{t('Announcements.title')}</SectionHeading>
      {announcements.length === 0 && (
        <AnnouncementTitle style={{ width: "100%", textAlign: "center" }}>
          {t('Announcements.noAnnouncements')}
        </AnnouncementTitle>
      )}
      {announcements.map((announcement) => (
        <AnnouncementUnit
          key={announcement.announcementId}
          course={{ courseId: 1 }}
          announcement={announcement}
          isGlobal={true}
        />
      ))}

      <BasicPagination
        currentPage={currentPage}
        pageSize={pageSize}
        maxPage={maxPage}
        baseURL={"/announcements"}
      />
    </>
  );
}

export default Announcements;
