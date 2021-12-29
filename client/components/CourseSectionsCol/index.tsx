import React from "react";
import PropTypes, { InferProps } from "prop-types";
import { Section } from "../../types";
import { useRouter } from "next/router";
import Link from "next/link";
import {
  CourseSectionsColWrapper,
  CourseSectionsColTitle,
  CourseSectionsItem,
} from "./styles";

CourseSectionsCol.propTypes = {
  courseId: PropTypes.number,
  courseName: PropTypes.string,
  year: PropTypes.number,
  quarter: PropTypes.number,
  code: PropTypes.string,
  board: PropTypes.string,
};

function CourseSectionsCol({
  courseId,
  courseName,
  year,
  quarter,
  code,
  board,
}: InferProps<typeof CourseSectionsCol.propTypes>) {
  const router = useRouter();
  const pathname = router?.pathname;
  const sections: Section[] = [
    { path: "/announcements", name: "Anuncios" },
    { path: "/teachers", name: "Profesores" },
    { path: "/files", name: "Archivos" },
    { path: "/exams", name: "Examenes" },
    { path: "/schedule", name: "Horarios" },
  ];
  return (
    <CourseSectionsColWrapper>
      <CourseSectionsColTitle>
        {`${courseName}: ${year} ${quarter} ${code} ${board}`}
      </CourseSectionsColTitle>
      {sections.map((section) => {
        const completePath = "/course/" + courseId + section.path;
        return (
          <CourseSectionsItem
            isActive={pathname === completePath}
            key={section.path}
          >
            <Link href={completePath}>{`› ${section.name}`}</Link>
          </CourseSectionsItem>
        );
      })}
    </CourseSectionsColWrapper>
  );
}

export default CourseSectionsCol;
