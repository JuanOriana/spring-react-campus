import React from "react";
import PropTypes, { InferProps } from "prop-types";
import type Section from "../../../types/Section";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  CourseSectionsColWrapper,
  CourseSectionsColTitle,
  CourseSectionsItem,
} from "./styles";

CourseSectionsCol.propTypes = {
  courseId: PropTypes.number.isRequired,
  courseName: PropTypes.string.isRequired,
  year: PropTypes.number.isRequired,
  quarter: PropTypes.number.isRequired,
  code: PropTypes.string.isRequired,
  board: PropTypes.string.isRequired,
};

function CourseSectionsCol({
  courseId,
  courseName,
  year,
  quarter,
  code,
  board,
}: InferProps<typeof CourseSectionsCol.propTypes>) {
  const location = useLocation();
  const pathname = location?.pathname;
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
            <Link to={completePath}>{`› ${section.name}`}</Link>
          </CourseSectionsItem>
        );
      })}
    </CourseSectionsColWrapper>
  );
}

export default CourseSectionsCol;
