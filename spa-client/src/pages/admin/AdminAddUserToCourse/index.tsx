import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  FormButton,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { Link, useNavigate, useParams } from "react-router-dom";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import { FormText } from "../AdminAllCourses/styles";
import { usePagination } from "../../../hooks/usePagination";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../components/generalStyles/pagination";
import { UserColumn, UserContainer, BackImg } from "./styles";
import { useForm } from "react-hook-form";
import React, { useEffect, useState } from "react";
import { userRoles } from "../../../common/constants";
import { handleService } from "../../../scripts/handleService";
import { courseService, userService } from "../../../services";
import { CourseModel, RoleModel, UserModel } from "../../../types";
import { renderToast } from "../../../scripts/renderToast";

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
  const { courseId } = useParams();
  const navigate = useNavigate();
  const [currentPage, pageSize] = usePagination(10);
  const [maxPage, setMaxPage] = useState(0);
  const [course, setCourse] = useState<CourseModel | undefined>();
  const [users, setUsers] = useState<UserModel[]>(new Array(1));
  const [courseTeachers, setCourseTeachers] = useState(new Array(1));
  const [courseStudents, setCourseStudents] = useState(new Array(1));
  const [courseHelpers, setCourseHelpers] = useState(new Array(1));
  const [roles, setRoles] = useState<RoleModel[]>(new Array(1));
  const [isCourseLoading, setIsCourseLoading] = useState(false);

  useEffect(() => {
    setIsCourseLoading(true);
    if (courseId) {
      const courseIdAsInt = parseInt(courseId);
      handleService(
        courseService.getCourseById(courseIdAsInt),
        navigate,
        (courseData) => {
          setCourse(courseData);
        },
        () => {
          setIsCourseLoading(false);
        }
      );
      handleService(
        userService.getRoles(),
        navigate,
        (roleData) => {
          setRoles(roleData);
        },
        () => {}
      );
      handleService(
        userService.getUsers(courseIdAsInt),
        navigate,
        (userData) => {
          setUsers(userData ? userData.getContent() : []);
        },
        () => {}
      );
      handleService(
        courseService.getTeachers(courseIdAsInt),
        navigate,
        (userData) => {
          setCourseTeachers(userData ? userData.getContent() : []);
        },
        () => {}
      );
      handleService(
        courseService.getStudents(courseIdAsInt),
        navigate,
        (userData) => {
          setCourseStudents(userData ? userData.getContent() : []);
          setMaxPage(userData ? userData.getMaxPage() : 1);
        },
        () => {}
      );
      handleService(
        courseService.getHelpers(courseIdAsInt),
        navigate,
        (userData) => {
          setCourseHelpers(userData ? userData.getContent() : []);
        },
        () => {}
      );
    }
  }, [courseId]);

  const { register, handleSubmit, reset } = useForm<FormData>({
    criteriaMode: "all",
  });

  const onSubmit = handleSubmit(({ userId, roleId }: FormData) => {
    const userIdFinal: number = userId
      ? parseInt(userId.toString())
      : users[0].userId;
    const roleIdFinal: number = roleId
      ? parseInt(roleId.toString())
      : roles[0].roleId;
    const courseIdFinal: number = parseInt(courseId ? courseId : "-1");
    const user = users.find((findUser) => {
      return findUser.userId === userIdFinal;
    });
    const promise = courseService.enrollUserToCourse(
      courseIdFinal,
      userIdFinal,
      roleIdFinal
    );

    promise
      .then((result) => {
        if (!result.hasFailed() || result.getError().getCode() === 204) {
          setUsers((oldUsers) =>
            oldUsers.filter((userIter) => userIter.userId !== user?.userId)
          );
          if (roleIdFinal === userRoles.STUDENT) {
            courseStudents.push(user);
          } else if (roleIdFinal === userRoles.TEACHER) {
            courseTeachers.push(user);
          } else {
            courseHelpers.push(user);
          }
          reset();
        } else {
          renderToast(
            "No se pudo agregar el usuario, intente de nuevo",
            "error"
          );
        }
      })
      .catch(() =>
        renderToast("No se pudo agregar el usuario, intente de nuevo", "error")
      );
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
          <BackImg
            src="/images/page-arrow.png"
            alt={t("AdminAddUserToCourse.alt.backButton")}
          />
          <p style={{ fontSize: "22px", fontWeight: "700" }}>
            {t("AdminAddUserToCourse.backButton")}
          </p>
        </Link>
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          {t("AdminAddUserToCourse.addUserToCourse", {
            subjectName: course?.subject.name,
            courseBoard: course?.board,
          })}
        </GeneralTitle>
        <FormLabel htmlFor="userId">
          {t("AdminAddUserToCourse.form.user")}
        </FormLabel>
        <FormSelect style={{ fontSize: "26px" }} {...register("userId", {})}>
          {users.map((user) => (
            <option value={user.userId} key={user.userId}>
              {user.fileNumber} - {user.name} {user.surname}
            </option>
          ))}
        </FormSelect>
        <FormLabel htmlFor="roleId">
          {t("AdminAddUserToCourse.form.role.title")}
        </FormLabel>
        <FormSelect style={{ fontSize: "26px" }} {...register("roleId", {})}>
          {roles.map((role) => (
            <option value={role.roleId} key={role.roleId}>
              {t("AdminAddUserToCourse.form.role" + role.roleName)}
            </option>
          ))}
        </FormSelect>
        <FormButton>{t("AdminAddUserToCourse.form.addButton")}</FormButton>
        {(courseTeachers.length > 0 ||
          courseStudents.length > 0 ||
          courseHelpers.length > 0) && (
          <UserContainer>
            <UserColumn>
              <FormText style={{ margin: 0 }}>
                {t("AdminAddUserToCourse.form.students")}
              </FormText>
              <ul>
                {courseStudents.map((student) => (
                  <li key={student.userId}>
                    {student.name} {student.surname}
                  </li>
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
                      to={`/admin/course/${courseId}/enroll?page=${
                        currentPage - 1
                      }&pageSize=${pageSize}`}
                    >
                      {/*ACHICAR!*/}
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
                      to={`/admin/course/${courseId}/enroll?page=${
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
              )}
            </UserColumn>
            <UserColumn>
              {courseTeachers.length > 0 && (
                <>
                  <FormText style={{ margin: 0 }}>
                    {t("AdminAddUserToCourse.form.teachers")}
                  </FormText>
                  <ul>
                    {courseTeachers.map((teacher) => (
                      <li key={teacher.userId}>
                        {teacher.name} {teacher.surname}
                      </li>
                    ))}
                  </ul>
                </>
              )}
              {courseHelpers.length > 0 && (
                <>
                  <FormText style={{ margin: 0 }}>
                    {t("AdminAddUserToCourse.form.assistants")}
                  </FormText>
                  <ul>
                    {courseHelpers.map((helper) => (
                      <li key={helper.userId}>
                        {helper.name} {helper.surname}
                      </li>
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
