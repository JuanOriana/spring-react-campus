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

function Announcements() {
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
      <SectionHeading>Anuncios</SectionHeading>
      {announcements.length === 0 && (
        <AnnouncementTitle style={{ width: "100%", textAlign: "center" }}>
          No hay anuncios!
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

      <PaginationWrapper>
        {currentPage > 1 && (
          <Link
            to={`/announcements?page=${currentPage - 1}&pageSize=${pageSize}`}
          >
            <PaginationArrow
              src="/resources/images/page-arrow.png"
              alt="Pagina previa"
            />
          </Link>
        )}
        Pagina {currentPage} de {maxPage}
        {currentPage < maxPage && (
          <a
            href={`/announcements?page=${currentPage + 1}&pageSize=${pageSize}`}
          >
            <PaginationArrow
              src="/resources/images/page-arrow.png"
              alt="Siguiente pagina"
            />
          </a>
        )}
      </PaginationWrapper>
    </>
  );
}

export default Announcements;
