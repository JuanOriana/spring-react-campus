import AdminSectionsCol from "../../../components/AdminSectionsCol";
import { SectionHeading } from "../../../components/generalStyles/utils";
import {
  FormButton,
  FormLabel,
  FormSelect,
} from "../../../components/generalStyles/form";
import { Link, useNavigate } from "react-router-dom";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../components/generalStyles/pagination";
import React, { useEffect, useState } from "react";
import { usePagination } from "../../../hooks/usePagination";
import {
  CourseTableForm,
  FormText,
  CourseTable,
  CourseTableHeader,
} from "./styles";
import { getQueryOrDefault, useQuery } from "../../../hooks/useQuery";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
import { courseService } from "../../../services";
import { handleService } from "../../../scripts/handleService";
import LoadableData from "../../../components/LoadableData";
import { CourseModel } from "../../../types";
//

function AdminAllCourses() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [courses, setCourses] = useState(new Array(1));
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, pageSize] = usePagination(10);
  const [maxPage, setMaxPage] = useState(1);
  const query = useQuery();
  const year = getQueryOrDefault(
    query,
    "year",
    new Date().getFullYear().toString()
  );
  const quarter = getQueryOrDefault(query, "quarter", "1");
  const [allYears, setAllYears] = useState([parseInt(year)]);

  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getCourses(currentPage, pageSize),
      navigate,
      (coursesData) => {
        setCourses(coursesData.getContent());
        setMaxPage(coursesData.getMaxPage());
      },
      () => setIsLoading(false)
    );
  }, [currentPage, pageSize, quarter, year]);

  useEffect(
    () =>
      handleService(
        courseService.getAvailableYears(),
        navigate,
        (receivedYears) => {
          setAllYears([2021]);
        },
        () => {
          return;
        }
      ),
    []
  );

  return (
    <>
      <AdminSectionsCol isSmall={true} />
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          flex: 1,
        }}
      >
        <SectionHeading>
          {t("AdminAllCourses.allCoursesFrom", {
            year: year,
            quarter: quarter,
          })}
        </SectionHeading>
        <CourseTableForm method="get">
          <div style={{ display: "flex" }}>
            <div style={{ display: "flex", flexDirection: "column" }}>
              <FormLabel htmlFor="year">
                {t("AdminAllCourses.form.year")}
              </FormLabel>
              <FormSelect name="year" id="year" defaultValue={year}>
                {allYears.map((optionYear) => (
                  <option key={optionYear} value={optionYear}>
                    {optionYear}
                  </option>
                ))}
              </FormSelect>
            </div>
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
            >
              <FormText>{t("AdminAllCourses.form.quarter")}</FormText>
              <div
                style={{
                  display: "flex",
                  width: "70%",
                  justifyContent: "space-between",
                }}
              >
                <FormLabel style={{ margin: 0 }}>
                  <input
                    type="radio"
                    value="1"
                    name="quarter"
                    defaultChecked={quarter === "1"}
                  />
                  <span>1Q</span>
                </FormLabel>
                <FormLabel style={{ margin: 0 }}>
                  <input
                    type="radio"
                    value="2"
                    name="quarter"
                    defaultChecked={quarter === "2"}
                  />
                  <span>2Q</span>
                </FormLabel>
              </div>
            </div>
          </div>
          <FormButton>{t("AdminAllCourses.form.searchButton")}</FormButton>
        </CourseTableForm>
        <LoadableData isLoading={isLoading}>
          {courses.length === 0 && (
            <h2 style={{ marginTop: "20px" }}>
              {t("AdminAllCourses.noCourses")}
            </h2>
          )}
          {courses.length !== 0 && (
            <>
              <CourseTable>
                <CourseTableHeader>
                  <th>{t("AdminAllCourses.table.code")}</th>
                  <th style={{ width: "300px" }}>
                    {t("AdminAllCourses.table.name")}
                  </th>
                  <th>{t("AdminAllCourses.table.board")}</th>
                </CourseTableHeader>
                {courses.map((course: CourseModel) => (
                  <tr key={course.courseId}>
                    <td>{course.subject.code} </td>
                    <td>
                      <Link
                        to={`/admin/course/enroll?courseId=${course.courseId}`}
                      >
                        {course.subject.name}
                      </Link>
                    </td>
                    <td>{course.board}</td>
                  </tr>
                ))}
              </CourseTable>
              <PaginationWrapper>
                {currentPage > 1 && (
                  <Link
                    to={`/admin/course/all?year=${year}&quarter=${quarter}&page=${
                      currentPage - 1
                    }&pageSize=${pageSize}`}
                  >
                    <PaginationArrow
                      xRotated={true}
                      src="/images/page-arrow.png"
                      alt={t("BasicPagination.alt.beforePage")}
                    />
                  </Link>
                )}
                {t("BasicPagination.message", {
                  currentPage: currentPage,
                  maxPage: maxPage,
                })}
                {currentPage < maxPage && (
                  <Link
                    to={`/admin/course/all?year=${year}&quarter=${quarter}&page=${
                      currentPage + 1
                    }&pageSize=${pageSize}`}
                  >
                    <PaginationArrow
                      src="/images/page-arrow.png"
                      alt={t("BasicPagination.alt.nextPage")}
                    />
                  </Link>
                )}
              </PaginationWrapper>
            </>
          )}
        </LoadableData>
      </div>
    </>
  );
}

export default AdminAllCourses;
