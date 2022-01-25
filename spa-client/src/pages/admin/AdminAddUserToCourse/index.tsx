import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  FormButton,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { Link } from "react-router-dom";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import { FormText } from "../AdminAllCourses/styles";
import { usePagination } from "../../../hooks/usePagination";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../components/generalStyles/pagination";
import { UserColumn, UserContainer, BackImg } from "./styles";
import { useForm } from "react-hook-form";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

type FormData = {
  userId: number;
  roleId: number;
};

function AdminAddUserToCourse() {
  const { t } = useTranslation();
  const maxPage = 3;
  const [currentPage, pageSize] = usePagination(10);
  const course = {
    courseId: 1,
    subject: { name: "PAW" },
    board: "F",
    year: 2020,
    quarter: 2,
  };
  const users = [{ userId: 1, fileNumber: 1, name: "xd", surname: "xd" }];
  const roles = [{ roleId: 1, roleName: "xd" }];
  const courseTeachers = [
    { userId: 1, fileNumber: 1, name: "xd", surname: "xd" },
  ];
  const courseHelpers = [
    { userId: 1, fileNumber: 1, name: "xd", surname: "xd" },
  ];
  const courseStudents = [
    { userId: 1, fileNumber: 1, name: "xd", surname: "xd" },
  ];

  const { register, handleSubmit, reset, setError } = useForm<FormData>({
    criteriaMode: "all",
  });

  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });
  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        method="post"
        acceptCharset="utf-8"
        style={{ margin: "0px 40px 40px 40px" }}
        onSubmit={onSubmit}
      >
        <Link
          to="/admin/course/select"
          style={{ display: "flex", alignItems: "center" }}
        >
          <BackImg src="/images/page-arrow.png" alt={t('AdminAddUserToCourse.alt.backButton')} />
          <p style={{ fontSize: "22px", fontWeight: "700" }}>{t('AdminAddUserToCourse.backButton')}</p>
        </Link>
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t('AdminAddUserToCourse.addUserToCourse', {subjectName: course.subject.name, courseBoard: course.board})}
        </GeneralTitle>
        <FormLabel htmlFor="userId">{t('AdminAddUserToCourse.form.user')}</FormLabel>
        <FormSelect style={{ fontSize: "26px" }} {...register("userId", {})}>
          {users.map((user) => (
            <option value={user.userId} key={user.userId}>
              {user.fileNumber} - {user.name} {user.surname}
            </option>
          ))}
        </FormSelect>
        <FormLabel htmlFor="roleId">{t('AdminAddUserToCourse.form.role.title')}</FormLabel>
        <FormSelect style={{ fontSize: "26px" }} {...register("roleId", {})}>
          {roles.map((role) => (
            <option value={role.roleId} key={role.roleId}>
              {t('AdminAddUserToCourse.form.role' + role.roleName)}
            </option>
          ))}
        </FormSelect>
        <FormButton>{t('AdminAddUserToCourse.form.addButton')}</FormButton>
        {(courseTeachers.length > 0 ||
          courseStudents.length > 0 ||
          courseHelpers.length > 0) && (
          <UserContainer>
            <UserColumn>
              <FormText style={{ margin: 0 }}>{t('AdminAddUserToCourse.form.students')}</FormText>
              <ul>
                {courseStudents.map((student) => (
                  <li key={student.userId}>{student.name}</li>
                ))}
              </ul>
              {maxPage > 1 && (
                <PaginationWrapper
                  style={{
                    alignSelf: "center",
                    fontSize: "14px",
                    marginRight: "25%",
                  }}
                >
                  {currentPage > 1 && (
                    <Link
                      to={`/admin/course/enroll?courseId=${
                        course.courseId
                      }&page=${currentPage - 1}&pageSize=${pageSize}`}
                    >
                      {/*ACHICAR!*/}
                      <PaginationArrow
                        xRotated={true}
                        src="/images/page-arrow.png"
                        alt={t('BasicPagination.alt.beforePage')}
                      />
                    </Link>
                  )}
                  {t('BasicPagination.message', {currentPage: currentPage,maxPage: maxPage})}
                  {currentPage < maxPage && (
                    <Link
                      to={`/admin/course/enroll?courseId=${
                        course.courseId
                      }&page=${currentPage + 1}&pageSize=${pageSize}`}
                    >
                      <PaginationArrow
                        src="/images/page-arrow.png"
                        alt={t('BasicPagination.alt.nextPage')}
                      />
                    </Link>
                  )}
                </PaginationWrapper>
              )}
            </UserColumn>
            <UserColumn>
              {courseTeachers.length > 0 && (
                <>
                  <FormText style={{ margin: 0 }}>{t('AdminAddUserToCourse.form.teachers')}</FormText>
                  <ul>
                    {courseTeachers.map((teacher) => (
                      <li key={teacher.userId}>{teacher.name}</li>
                    ))}
                  </ul>
                </>
              )}
              {courseHelpers.length > 0 && (
                <>
                  <FormText style={{ margin: 0 }}>{t('AdminAddUserToCourse.form.assistants')}</FormText>
                  <ul>
                    {courseHelpers.map((helper) => (
                      <li key={helper.userId}>{helper.name}</li>
                    ))}
                  </ul>
                </>
              )}
            </UserColumn>
          </UserContainer>
        )}
      </FormWrapper>
    </>
  );
}

export default AdminAddUserToCourse;
