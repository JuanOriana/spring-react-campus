import React, { useState, useEffect } from "react";
import { SectionHeading } from "../../components/generalStyles/utils";
import { AnnouncementTitle } from "../../components/AnnouncementUnit/styles";
import AnnouncementUnit from "../../components/AnnouncementUnit";
import { usePagination } from "../../hooks/usePagination";
import BasicPagination from "../../components/BasicPagination";
import { announcementsService } from "../../services";
import { useNavigate } from "react-router-dom";
import LoadableData from "../../components/LoadableData";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { handleService } from "../../scripts/handleService";
//

function Announcements() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [announcements, setAnnouncements] = useState(new Array(0));
  const [isLoading, setIsLoading] = useState(false);
  const [maxPage, setMaxPage] = useState(1);
  const [currentPage, pageSize] = usePagination(10);

  useEffect(() => {
    setIsLoading(true);
    handleService(
      announcementsService.getAnnouncements(currentPage, pageSize),
      navigate,
      (announcementsData) => {
        setAnnouncements(announcementsData.getContent());
        setMaxPage(announcementsData.getMaxPage());
      },
      () => setIsLoading(false)
    );
  }, [currentPage, pageSize]);
  return (
    <>
      <SectionHeading>{t("Announcements.title")}</SectionHeading>
      <LoadableData spinnerMultiplier={2} isLoading={isLoading}>
        {announcements.length === 0 && (
          <AnnouncementTitle style={{ width: "100%", textAlign: "center" }}>
            {t("Announcements.noAnnouncements")}
          </AnnouncementTitle>
        )}
        {announcements.map((announcement) => (
          <AnnouncementUnit
            key={announcement.announcementId}
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
      </LoadableData>
    </>
  );
}

export default Announcements;
